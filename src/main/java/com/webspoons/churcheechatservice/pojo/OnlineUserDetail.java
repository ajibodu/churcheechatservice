package com.webspoons.churcheechatservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDetail {
    private String  userID;
    private Date connectTime;
    private Boolean online;
    private String currentSessionId;
}
