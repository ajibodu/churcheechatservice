package com.webspoons.churcheechatservice.pojo;

import com.webspoons.churcheechatservice.model.ChatMessageGroup;
import com.webspoons.churcheechatservice.model.ChatMessageOne;
import lombok.Data;

@Data
public class ChatNotification {

    public ChatNotification(){}
    public ChatNotification(ChatMessageOne chatMessageOne){
        receiverID = chatMessageOne.getReceiverID();
        senderID = chatMessageOne.getSenderID();
    }

    public ChatNotification(ChatMessageGroup chatMessageGroup){
        roomID = chatMessageGroup.getRoomID();
        senderID = chatMessageGroup.getSenderID();
    }

    private String receiverID;
    private String senderID;
    private String roomID;

    private MessageType messageType;
    private MessageStatus messageStatus;
    private String messageID;
}
