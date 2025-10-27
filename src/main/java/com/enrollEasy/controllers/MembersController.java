package com.enrollEasy.controllers;

import com.enrollEasy.controllers.responses.MemberResponse;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;
import com.enrollEasy.service.MemberService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MembersController {

  private final MemberService memberService;

  public MembersController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/getAll")
  public List<MemberResponse> getMember() {
    return memberService.getMembers();
  }

  @PostMapping("/memberPaid")
  public ResponseEntity<MemberDao> memberPaid(@RequestBody PaidStatus paidStatus) {
    return ResponseEntity.ok(memberService.membershipValidDate(paidStatus));
  }
}
