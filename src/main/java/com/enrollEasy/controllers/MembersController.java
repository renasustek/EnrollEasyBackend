package com.enrollEasy.controllers;

import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;
import com.enrollEasy.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MembersController {

    private final MemberService memberService;

    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/getAll")
    public List<MemberDao> getMember(){
        return memberService.getMembers();
    }

    @PostMapping("/changePaidStatus")
    public ResponseEntity<MemberDao> changePaidStatus(@RequestBody PaidStatus paidStatus){
        return ResponseEntity.ok(memberService.changePaidStatus(paidStatus));
    }
}
