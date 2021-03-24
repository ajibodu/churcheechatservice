package com.webspoons.churcheechatservice.controller;

import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("add/{roomID}")
    public ResponseEntity add(@RequestBody List<Member> members, @PathVariable("roomID") String roomID) throws Exception{
        return memberService.addMembersToRoom(members, roomID);
    }

    @GetMapping("get/{roomID}")
    public ResponseEntity get(@PathVariable("roomID") String roomID) throws Exception{
        return memberService.getRoomMembers(roomID);
    }
}
