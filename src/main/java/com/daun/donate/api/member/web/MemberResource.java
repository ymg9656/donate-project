package com.daun.donate.api.member.web;

import com.daun.donate.api.member.doamin.Member;
import com.daun.donate.api.member.service.MemberResult;
import com.daun.donate.api.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/member")
public class MemberResource {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody MemberResult register(@ModelAttribute Member entity){
        int isSuccess = memberService.save(entity);
        MemberResult r=new MemberResult();
        r.setCode(""+isSuccess);
        r.setCodeMessage(""+isSuccess);
        return r;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody MemberResult login(@ModelAttribute Member entity){
        Member m=memberService.detail(entity);
        if(m!=null){
            //정상 로그인
            String sessionId=m.getMemberSeq()+"|"+entity.getId()+"|"+entity.getPwd()+"|"+System.currentTimeMillis();
            



        }
        return new MemberResult();
    }
}
