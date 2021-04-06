package com.webspoons.churcheechatservice.pojo;

import com.webspoons.churcheechatservice.model.ChatMessageGroup;
import com.webspoons.churcheechatservice.model.ChatMessageOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

@Data
public class ChatMessage {

    public ChatMessage(ChatMessageOne chatMessageOne){
        senderID = chatMessageOne.getSenderID();
        receiverID = chatMessageOne.getReceiverID();
        messageContent  = chatMessageOne.getMessageContent();
        messageContentType = chatMessageOne.getMessageContentType();
        timestamp = chatMessageOne.getTimestamp();
        messageStatus = chatMessageOne.getMessageStatus();
        messageID = chatMessageOne.getMessageID();
    }

    public ChatMessage(ChatMessageGroup chatMessageGroup){
        senderID = chatMessageGroup.getSenderID();
        receiverID = chatMessageGroup.getRoomID();
        messageContent  = chatMessageGroup.getMessageContent();
        messageContentType = chatMessageGroup.getMessageContentType();
        timestamp = chatMessageGroup.getTimestamp();
        messageStatus = chatMessageGroup.getMessageStatus();
        messageID = chatMessageGroup.getMessageID();
    }
    public ChatMessage(){};

    @Indexed
    private String senderID;
    @Indexed
    private String receiverID;
    @Indexed
    @Id
    private String messageID;
    private String messageContent;
    private MessageContentType messageContentType;
    private Date timestamp;
    private MessageStatus messageStatus;
    private List<String> messageDeliveredTo;
    private List<String> messageReadBy;
}
