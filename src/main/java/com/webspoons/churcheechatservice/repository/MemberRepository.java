package com.webspoons.churcheechatservice.repository;

import com.webspoons.churcheechatservice.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {

    Optional<List<Member>> findByroomID(String roomID);
}
