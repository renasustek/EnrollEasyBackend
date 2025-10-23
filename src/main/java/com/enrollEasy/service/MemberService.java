package com.enrollEasy.service;

import com.enrollEasy.persistance.MemberRepo;
import org.springframework.stereotype.Service;

@Service
public class MemberService{

    private final MemberRepo memberDao;

    public MemberService(MemberRepo memberDao){
        this.memberDao = memberDao;
    }

}
