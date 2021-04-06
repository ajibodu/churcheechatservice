package com.webspoons.churcheechatservice.service;

import com.webspoons.churcheechatservice.Utility.CustomValidationException;
import com.webspoons.churcheechatservice.model.ChatMessageGroup;
import com.webspoons.churcheechatservice.model.ChatMessageOne;
import com.webspoons.churcheechatservice.pojo.*;
import com.webspoons.churcheechatservice.repository.ChatRepository;
import com.webspoons.churcheechatservice.repository.GroupRepository;
import com.webspoons.churcheechatservice.repository.MemberRepository;
import com.webspoons.churcheechatservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final GroupRepository groupRepository;

    public void oneOnOneChat(ChatMessageOne chatMessageOne) throws Exception{

        var members = memberRepository.findBymemberID(chatMessageOne.getReceiverID());
        if(members.get().size() <= 0)
            //throw new CustomValidationException("Member Does Not Exist");

        chatMessageOne.setMessageStatus(MessageStatus.SENT);
        var saved = chatRepository.insert(chatMessageOne);

        messagingTemplate.convertAndSend("/oneOnOneChat/" + chatMessageOne.getReceiverID() + "/sub",
                new ChatNotification(chatMessageOne));

    }

    public void oneToGroup(ChatMessageGroup chatMessageGroup) throws Exception{

        var members = roomRepository.findById(chatMessageGroup.getRoomID());
        if(members.isEmpty())
            //throw new CustomValidationException("Room Does Not Exist");

        chatMessageGroup.setMessageStatus(MessageStatus.SENT);
        var saved = groupRepository.insert(chatMessageGroup);

        messagingTemplate.convertAndSend("/oneToGroupChat/" + chatMessageGroup.getRoomID() + "/sub",
                new ChatNotification(chatMessageGroup));

    }

    public List<ChatMessage> poolMessage(ChatNotification chatNotification){
        var messages = new ArrayList<ChatMessage>();
        if(chatNotification.getMessageType().equals(MessageType.ONEONONE)){
            var chatone = chatRepository.findSentBySenderAndRecipient(chatNotification.getSenderID(), chatNotification.getReceiverID());
            chatone.forEach(message -> messages.add(new ChatMessage(message)));
        }
        else {
            var group = groupRepository.findSentBySenderAndRoom(chatNotification.getSenderID(), chatNotification.getRoomID());
            group.forEach(message -> messages.add(new ChatMessage(message)));
        }
        return messages;
    }

    public ChatMessage messageStatus(ChatNotification chatNotification){
        var messages = new ChatMessage();
        if(chatNotification.getMessageType().equals(MessageType.ONEONONE)){
            var chatone = chatRepository.findById(chatNotification.getMessageID());
            chatone.get().setMessageStatus(chatNotification.getMessageStatus());
            chatRepository.save(chatone.get());
            messages = new ChatMessage(chatone.get());
        }
        else {
            var group = groupRepository.findById(chatNotification.getMessageID());
            group.get().setMessageStatus(chatNotification.getMessageStatus());
            if(chatNotification.getMessageStatus().equals(MessageStatus.READ)){
                var read = group.get().getMessageReadBy();
                if(read == null)
                    read = new ArrayList<String>();
                read.add(chatNotification.getReceiverID());
                group.get().setMessageReadBy(read);
            }else if(chatNotification.getMessageStatus().equals(MessageStatus.DELIVERED)){
                var delv = group.get().getMessageDeliveredTo();
                if(delv == null)
                    delv = new ArrayList<String>();
                delv.add(chatNotification.getReceiverID());
                group.get().setMessageDeliveredTo(delv);
            }
            groupRepository.save(group.get());
            messages = new ChatMessage(group.get());
        }
        return messages;
    }
    //Subscribe
    //oneOnOneChat/{ownID}/user
    //oneToGroupChat/{roomID}/group
}
