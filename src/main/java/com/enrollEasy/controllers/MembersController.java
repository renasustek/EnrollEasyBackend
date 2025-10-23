package com.enrollEasy.controllers;

import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MembersController {

    private final MemberService memberService;

    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberDao> getMember(){
        return memberService.getMembers();
    }

}
