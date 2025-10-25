package com.enrollEasy.service;

import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberRepo memberRepo;

  public MemberService(MemberRepo memberRepo) {
    this.memberRepo = memberRepo;
  }

  public List<MemberDao> getMembers() {
    return memberRepo.findAll();
  }

  public MemberDao changePaidStatus(PaidStatus paidStatus) {
    Optional<MemberDao> member = memberRepo.findById(paidStatus.memberId());
    if (member.isPresent()) {
      member.get().setPaidStatus(paidStatus.paid());
      return memberRepo.save(member.get());
    } else {
      throw new MemberNotFoundExpection();
    }
  }
}
