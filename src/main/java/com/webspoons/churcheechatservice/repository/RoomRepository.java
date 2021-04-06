package com.webspoons.churcheechatservice.repository;

import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    @Query("{ 'creatorID' : ?0 , 'roomName' : ?1}")
    Optional<Room> findByCreatorAndRoomName(String creatorID, String roomName);
}
