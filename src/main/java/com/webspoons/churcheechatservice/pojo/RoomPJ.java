package com.webspoons.churcheechatservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.model.Room;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class RoomPJ {

    public RoomPJ(Room room){
        roomID = room.getRoomID();
        creatorID = room.getCreatorID();
        createDate = room.getCreateDate();
        roomName = room.getRoomName();
        roomDesc = room.getRoomDesc();
        roomType = room.getRoomType();
        totalMember = room.getTotalMember();
        profileImage = room.getProfileImage();
    }
    public RoomPJ(){}

    private String roomID;
    private String creatorID;
    private Date createDate;
    private String roomName;
    private String roomDesc;
    private RoomType roomType;
    private int totalMember;
    private String profileImage;
}
