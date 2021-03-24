package com.webspoons.churcheechatservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.pojo.MemberType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("tb_member")
public class Member {
    @JsonIgnore
    private String roomID;
    private String memberID;
    @JsonIgnore
    private Date joinDate;
    private MemberType memberType;
}
