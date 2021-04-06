package com.webspoons.churcheechatservice.repositorycache;

import com.webspoons.churcheechatservice.Utility.RedisUtil;
import com.webspoons.churcheechatservice.model.Member;
import com.webspoons.churcheechatservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCache implements MemberRepository {

    private final MemberRepository memberRepository;
    private final RedisUtil<Member> memberRedisUtil;
    
    @Override
    public Optional<List<Member>> findByroomID(String roomID) {
        return memberRepository.findBymemberID(roomID);
    }

    @Override
    public Optional<List<Member>> findBymemberID(String memberID) {
        return memberRepository.findBymemberID(memberID);
    }

    @Override
    public <S extends Member> S save(S s) {
        return memberRepository.save(s);
    }

    @Override
    public <S extends Member> List<S> saveAll(Iterable<S> iterable) {
        return memberRepository.saveAll(iterable);
    }

    @Override
    public Optional<Member> findById(String s) {
        return memberRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return memberRepository.existsById(s);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Iterable<Member> findAllById(Iterable<String> iterable) {
        return memberRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return memberRepository.count();
    }

    @Override
    public void deleteById(String s) {
        memberRepository.deleteById(s);
    }

    @Override
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public void deleteAll(Iterable<? extends Member> iterable) {
        memberRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Override
    public List<Member> findAll(Sort sort) {
        return memberRepository.findAll(sort);
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Member> S insert(S s) {
        return memberRepository.insert(s);
    }

    @Override
    public <S extends Member> List<S> insert(Iterable<S> iterable) {
        return memberRepository.insert(iterable);
    }

    @Override
    public <S extends Member> Optional<S> findOne(Example<S> example) {
        return memberRepository.findOne(example);
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example) {
        return memberRepository.findAll(example);
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example, Sort sort) {
        return memberRepository.findAll(example, sort);
    }

    @Override
    public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) {
        return memberRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Member> long count(Example<S> example) {
        return memberRepository.count(example);
    }

    @Override
    public <S extends Member> boolean exists(Example<S> example) {
        return memberRepository.exists(example);
    }
}
