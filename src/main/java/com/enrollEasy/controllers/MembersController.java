package com.enrollEasy.controllers;

import com.enrollEasy.controllers.responses.MemberResponse;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.MembershipDuration;
import com.enrollEasy.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/getAll")
  public List<MemberResponse> getAll() {
    return memberService.getAll();
  }

  @PostMapping("/memberPaid")
  public ResponseEntity<MemberDao> logPayment(@RequestBody MembershipDuration membershipDuration) {
    return ResponseEntity.ok(memberService.logMembershipPayment(membershipDuration));
  }
}
