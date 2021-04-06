package com.webspoons.churcheechatservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.pojo.RoomType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter @Setter
@Document("tb_room")
@CompoundIndex(def = "{'creatorID':1, 'roomName':1}", name = "room_index", unique = true)
public class Room {
    @Id
    @Indexed
    @JsonIgnore
    private String roomID;
    @Indexed
    @JsonIgnore
    private String creatorID;
    @JsonIgnore
    private Date createDate;
    @Indexed
    private String roomName;
    private String roomDesc;
    private RoomType roomType;
    @JsonIgnore
    private String profileImage;
    @JsonIgnore
    private int totalMember;
    @Transient
    private String base64Image;


}
