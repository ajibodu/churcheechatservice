package com.webspoons.churcheechatservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.pojo.RoomType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter @Setter
@Document("tb_room")
public class Room {
    @Id
    @Indexed
    private String roomID;
    @JsonIgnore
    private Date createDate;
    @Indexed
    private String roomName;
    private String roomDesc;
    private RoomType roomType;
    private int totalMember;
}
