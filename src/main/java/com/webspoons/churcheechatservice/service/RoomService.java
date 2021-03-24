package com.webspoons.churcheechatservice.service;

import com.webspoons.churcheechatservice.Mics.CustomValidationException;
import com.webspoons.churcheechatservice.model.Room;
import com.webspoons.churcheechatservice.pojo.GenResponse;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import com.webspoons.churcheechatservice.repository.RoomRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public ResponseEntity<GenResponse> createRoom(Room room){
        var response = new GenResponse<Room>();
        try {
            Calendar calendar = Calendar.getInstance();
            room.setCreateDate(calendar.getTime());

            response.setData(roomRepository.insert(room));
            if(StringUtils.isEmpty(response.getData().getRoomID()))
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> editRoomDetail(Room room) throws Exception{
        var response = new GenResponse<Room>();
        try {
            var curRoom = roomRepository.findById(room.getRoomID())
                    .orElseThrow(
                            ()-> new CustomValidationException("Room Does Not Exist")
                    );
            room.setCreateDate(curRoom.getCreateDate());
            response.setData(roomRepository.save(room));
            if(StringUtils.isEmpty(response.getData().getRoomID()))
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> getRoomDetail(String roomID) throws Exception{
        var response = new GenResponse<Room>();
        try {
            var curRoom = roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("Room Does Not Exist")
                    );
            response.setData(curRoom);
            response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }
}
