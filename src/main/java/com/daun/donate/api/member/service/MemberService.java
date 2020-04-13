package com.daun.donate.api.member.service;

import com.daun.donate.api.member.doamin.Member;

public interface MemberService {
    int save(Member entity);
    Member detail(Member entity);
}
