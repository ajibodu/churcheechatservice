package com.webspoons.churcheechatservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webspoons.churcheechatservice.pojo.MessageContentType;
import com.webspoons.churcheechatservice.pojo.MessageStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("tb_person_chat")
public class ChatMessageOne {

    @Id
    private String messageID;
    @Indexed
    private String senderID;
    @Indexed
    private String receiverID;
    private String messageContent;
    private MessageContentType messageContentType;
    private Date timestamp;
    private MessageStatus messageStatus;
}
