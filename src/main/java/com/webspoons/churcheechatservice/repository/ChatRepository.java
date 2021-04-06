package com.webspoons.churcheechatservice.repository;

import com.webspoons.churcheechatservice.model.ChatMessageOne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatMessageOne, String> {

    @Query("{ 'senderID' : ?0 , 'receiverID' : ?1, 'messageStatus': 'SENT' }")
    List<ChatMessageOne> findSentBySenderAndRecipient(String sender, String receiver);
}
