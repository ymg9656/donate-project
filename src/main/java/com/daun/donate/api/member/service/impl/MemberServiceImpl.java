package com.daun.donate.api.member.service.impl;

import com.daun.donate.api.member.doamin.Member;
import com.daun.donate.api.member.doamin.repository.MemberRepository;
import com.daun.donate.api.member.service.MemberResult;
import com.daun.donate.api.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public int save(Member entity) {
        return memberRepository.save(entity);
    }

    @Override
    public Member detail(Member entity) {
        return memberRepository.findOne(entity);
    }
}
