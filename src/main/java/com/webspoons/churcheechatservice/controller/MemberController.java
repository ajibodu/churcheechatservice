package com.webspoons.churcheechatservice.controller;

import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity test() throws Exception{
        return ResponseEntity.ok("Here we are");
    }

    @PostMapping("add/{roomID}")
    public ResponseEntity add(@RequestBody List<Member> members, @PathVariable("roomID") String roomID) throws Exception{
        return memberService.addMembersToRoom(members, roomID);
    }

    @GetMapping("get/{memberID}")
    public ResponseEntity get(@PathVariable("memberID") String memberID) throws Exception{
        return memberService.getRoomsMemberBelongTo(memberID);
    }

}
