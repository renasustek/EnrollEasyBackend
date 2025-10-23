package com.enrollEasy.service;

import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService{

    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo){
        this.memberRepo = memberRepo;
    }

    public List<MemberDao> getMembers(){
        return memberRepo.findAll();
    }
}
