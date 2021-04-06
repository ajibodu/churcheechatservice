package com.webspoons.churcheechatservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.model.Member;
import lombok.Data;

import java.util.Date;

@Data
public class MemberPJ {

    public MemberPJ(Member member){
        roomID = member.getRoomID();
        memberID = member.getMemberID();
        joinDate = member.getJoinDate();
        memberType = member.getMemberType();
    }
    public MemberPJ(){}

    private String roomID;
    private String memberID;
    private Date joinDate;
    private MemberType memberType;

}
