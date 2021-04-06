package com.webspoons.churcheechatservice.service;

import com.webspoons.churcheechatservice.Utility.Contant;
import com.webspoons.churcheechatservice.Utility.CustomValidationException;
import com.webspoons.churcheechatservice.Utility.RedisKeys;
import com.webspoons.churcheechatservice.Utility.RedisUtil;
import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.pojo.GenResponse;
import com.webspoons.churcheechatservice.pojo.MemberPJ;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import com.webspoons.churcheechatservice.pojo.RoomPJ;
import com.webspoons.churcheechatservice.repository.MemberRepository;
import com.webspoons.churcheechatservice.repository.RoomRepository;
import com.webspoons.churcheechatservice.repositorycache.MemberRepositoryCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MemberService {

    private final MemberRepositoryCache memberRepository;
    private final RoomRepository roomRepository;
    private final HttpServletRequest httpServletRequest;
    private final RedisUtil<Member> memberRedisUtil;
    private final RedisUtil<MemberPJ> memberpjRedisUtil;

    public ResponseEntity<GenResponse> addMembersToRoom(List<Member> members, String roomID) throws Exception{
        var response = new GenResponse<List<Member>>();
        try {
            httpServletRequest.getHeader(Contant.USER_HEADER_AUTH);
            roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("Room Does Not Exist")
                    );
            var memberUpdate = new ArrayList<Member>();
            Calendar calendar = Calendar.getInstance();
            for(Member member : members){
                member.setJoinDate(calendar.getTime());
                member.setRoomID(roomID);
                memberUpdate.add(member);
            }

            var insertedMembers = memberRepository.insert(members);
            memberUpdate.clear();
            for(Member member : insertedMembers){
                memberUpdate.add(member);
            }
            response.setData(memberUpdate);

            if(response.getData().size() <= 0)
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else{
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
//                memberRedisUtil.addList(RedisKeys.MEMBER, roomID, response.getData());
//                for(Member member : insertedMembers){
//                    memberpjRedisUtil.addList(RedisKeys.ROOM, member.getMemberID(), new MemberPJ(member));
//                }
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

    public ResponseEntity<GenResponse> getRoomsMemberBelongTo(String memberID) throws Exception{
        var response = new GenResponse<List<MemberPJ>>();
        try {
            var cachedData = memberpjRedisUtil.getListMembers(RedisKeys.ROOM, memberID, MemberPJ.class);
            if(cachedData.size() > 0){
                response.setData(cachedData);
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                return ResponseEntity.ok(response);
            }

            String userAuth = httpServletRequest.getHeader(Contant.USER_HEADER_AUTH);
            var members = memberRepository.findBymemberID(memberID);
            if(members.get().size() <= 0)
                throw new CustomValidationException("Member Does Not Exist");

            var memberPJs =  new ArrayList<MemberPJ>();
            members.get().forEach((member)-> memberPJs.add(new MemberPJ(member)));
            response.setData(memberPJs);

            if(response.getData().size() <= 0)
                response.mapResponseCode(ResponseCodes.FAILED_CREATION);
            else{
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);
                memberpjRedisUtil.addList(RedisKeys.ROOM, memberID, response.getData());
            }


        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

}
