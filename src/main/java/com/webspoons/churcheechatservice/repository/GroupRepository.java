package com.webspoons.churcheechatservice.repository;

import com.webspoons.churcheechatservice.model.ChatMessageGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroupRepository extends MongoRepository<ChatMessageGroup, String> {

    @Query("{ 'senderID' : ?0 , 'roomID' : ?1, 'messageStatus': 'SENT' }")
    List<ChatMessageGroup> findSentBySenderAndRoom(String senderID, String roomID);
}
