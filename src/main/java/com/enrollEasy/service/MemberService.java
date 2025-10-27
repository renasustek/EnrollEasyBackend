package com.enrollEasy.service;

import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;

import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
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

  public MemberDao membershipValidDate(PaidStatus paidStatus) {
    Optional<MemberDao> member = memberRepo.findById(paidStatus.memberId());
    if (member.isPresent()) {
        Date newDate = member.get().getMembershipValidTill();
        if (newDate == null){
            newDate = Date.valueOf(LocalDate.now().plusDays(paidStatus.numberOfMembershipDaysPaidFor()));
        }else {
            newDate = Date.valueOf(newDate.toLocalDate().plusDays(paidStatus.numberOfMembershipDaysPaidFor()));
        }
      member.get().setMembershipValidTill(newDate);
      return memberRepo.save(member.get());
    } else {
      throw new MemberNotFoundExpection();
    }
  }
}
