package com.webspoons.churcheechatservice.service;

import com.webspoons.churcheechatservice.Mics.CustomValidationException;
import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.model.Room;
import com.webspoons.churcheechatservice.pojo.GenResponse;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import com.webspoons.churcheechatservice.repository.MemberRepository;
import com.webspoons.churcheechatservice.repository.RoomRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    public MemberService(MemberRepository memberRepository, RoomRepository roomRepository){
        this.memberRepository = memberRepository;
        this.roomRepository = roomRepository;
    }

    public ResponseEntity<GenResponse> addMembersToRoom(List<Member> members, String roomID) throws Exception{
        var response = new GenResponse<List<Member>>();
        try {
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
            else
                response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (DuplicateKeyException e){
            response.mapResponseCode(ResponseCodes.DUPLICATE_USER_REQUEST);
        }
        catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GenResponse> getRoomMembers(String roomID) throws Exception{
        var response = new GenResponse<List<Member>>();
        try {
            roomRepository.findById(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("room does not exist"));

            var memberUpdate = memberRepository.findByroomID(roomID)
                    .orElseThrow(
                            ()-> new CustomValidationException("No member found"));

            response.setData(memberUpdate);
            response.mapResponseCode(ResponseCodes.SUCCESSFUL);

        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok(response);
    }
}
