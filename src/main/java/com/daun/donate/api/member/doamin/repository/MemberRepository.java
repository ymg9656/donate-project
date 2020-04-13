package com.daun.donate.api.member.doamin.repository;

import com.daun.donate.api.member.doamin.Member;
import org.springframework.stereotype.Repository;


public interface MemberRepository {
    int save(Member entity);
    Member findOne(Member entity);
}
