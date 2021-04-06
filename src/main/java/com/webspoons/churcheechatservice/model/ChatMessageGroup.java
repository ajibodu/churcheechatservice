package com.webspoons.churcheechatservice.model;

import com.webspoons.churcheechatservice.pojo.MessageContentType;
import com.webspoons.churcheechatservice.pojo.MessageStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("tb_group_chat")
public class ChatMessageGroup {

    @Id
    private String messageID;
    @Indexed
    private String senderID;
    @Indexed
    private String roomID;
    private String messageContent;
    private MessageContentType messageContentType;
    private Date timestamp;
    private MessageStatus messageStatus;
    private List<String> messageDeliveredTo;
    private List<String> messageReadBy;
}
