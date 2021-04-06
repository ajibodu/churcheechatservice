package com.webspoons.churcheechatservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.webspoons.churcheechatservice.Utility.*;
import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.model.Room;
import com.webspoons.churcheechatservice.pojo.*;
import com.webspoons.churcheechatservice.repository.MemberRepository;
import com.webspoons.churcheechatservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    @Value("${aws.s3.bucket.name}")
    private String BUCKET_NAME;
    @Value("${aws.s3.bucket.roomprofile}")
    private String ROOM_PROFILE;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;
    private final AmazonS3 amazonS3;
    private final RedisUtil<RoomPJ> roomRedisUtil;
    private final RedisUtil<Member> memberRedisUtil;


    public ResponseEntity<GenResponse> checkIfRoomNameExistForCreator(Room room) throws Exception{
        var response = new GenResponse<Boolean>();
        try {
            var authTokenDetail = authenticationService.getAuthTokenDetail();
            var existing = roomRepository.findByCreatorAndRoomName(authTokenDetail.getData().get_id(), room.getRoomName());
            if(existing.isEmpty()){
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                response.setData(true);
            }else {
                response.mapResponseCode(ResponseCodes.DUPLICATE_REQUEST);
                response.setData(false);
            }
        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> createRoom(Room room) throws Exception{
        var response = new GenResponse<RoomPJ>();
        try {
            var authTokenDetail = authenticationService.getAuthTokenDetail();
            room.setCreateDate(Utility.currentTime());
            room.setCreatorID(authTokenDetail.getData().get_id());

            response.setData(new RoomPJ(roomRepository.insert(room)));
            if (StringUtils.isEmpty(response.getData().getRoomID()))
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else {
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                if(!StringUtils.isEmpty(room.getBase64Image())){
                    room.setProfileImage(uploadProfileImageToS3(room, response.getData().getRoomID()));
                    response.setData(new RoomPJ(roomRepository.save(room)));
                }
                roomRedisUtil.addValue(RedisKeys.ROOM, response.getData().getRoomID(), response.getData());
            }
        }catch (DuplicateKeyException e){
            response.mapResponseCode(ResponseCodes.DUPLICATE_REQUEST);
            log.warn(e.getMessage());
        }
        catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> editRoomDetail(Room room, String roomID) throws Exception{
        var response = new GenResponse<RoomPJ>();
        try {
            var curRoom = roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("Room Does Not Exist")
                    );
            room.setRoomID(roomID);
            room.setCreatorID(curRoom.getCreatorID());
            room.setCreateDate(curRoom.getCreateDate());
            room.setProfileImage(curRoom.getProfileImage());

            response.setData(new RoomPJ(roomRepository.save(room)));
            if(StringUtils.isEmpty(response.getData().getRoomID()))
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else{
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                if(!StringUtils.isEmpty(room.getBase64Image())){
                    room.setProfileImage(uploadProfileImageToS3(room, response.getData().getRoomID()));
                    response.setData(new RoomPJ(roomRepository.save(room)));
                }
                roomRedisUtil.addValue(RedisKeys.ROOM, response.getData().getRoomID(), response.getData());
            }
        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> getAllRoomDetail() throws Exception{
        var response = new GenResponse<List<RoomPJ>>();
        try {
            var allRoom = roomRepository.findAll();
            var roomPJs =  new ArrayList<RoomPJ>();
            allRoom.forEach((room)-> roomPJs.add(new RoomPJ(room)));
            response.setData(roomPJs);
            response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> getRoomDetail(String roomID) throws Exception{
        var response = new GenResponse<RoomPJ>();
        try {
            var cachedData = roomRedisUtil.getValue(RedisKeys.ROOM, roomID, RoomPJ.class);
            if(cachedData != null){
                response.setData(cachedData);
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                return ResponseEntity.ok(response);
            }

            var curRoom = roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("Room Does Not Exist")
                    );
            response.setData(new RoomPJ(curRoom));
            response.mapResponseCode(ResponseCodes.SUCCESSFUL);

            roomRedisUtil.addValue(RedisKeys.ROOM, response.getData().getRoomID(), response.getData());

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> getMembersInRoom(String roomID) throws Exception{
        var response = new GenResponse<List<Member>>();
        try {
            var cachedData = (List<Member>)memberRedisUtil.getListMembers(RedisKeys.MEMBER, roomID, Member.class);
            if(cachedData.size() > 0){
                response.setData(cachedData);
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                return ResponseEntity.ok(response);
            }

            roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("room does not exist"));

            var memberUpdate = memberRepository.findByroomID(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("No member found"));

            response.setData(memberUpdate);
            response.mapResponseCode(ResponseCodes.SUCCESSFUL);

            if(response.getData().size() > 0){
                memberRedisUtil.addList(RedisKeys.MEMBER, roomID, response.getData());
            }

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    private String uploadProfileImageToS3(Room room, String roomID){

        var base64Details = room.getBase64Image().split(",");
        var fileDetail = base64Details[0].split(":")[1].split(";")[0];

        byte[] data = Base64.getDecoder().decode(base64Details[1]);
        InputStream fis = new ByteArrayInputStream(data);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType(fileDetail); //"image/jpg"

        String objectKey = ROOM_PROFILE.concat("/" + roomID + "." + fileDetail.split("/")[1]);
        var put = amazonS3.putObject(
                BUCKET_NAME,
                objectKey,
                fis,
                metadata
        );
        amazonS3.setObjectAcl(BUCKET_NAME, objectKey, CannedAccessControlList.PublicRead);
        return  objectKey;


        //var object = amazonS3.getObject(BUCKET_NAME, objectKey);

    }
}
