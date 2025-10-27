package com.enrollEasy.service;

import com.enrollEasy.controllers.responses.MemberResponse;
import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.MembershipDuration;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

  private final MemberRepo memberRepo;

  public MemberService(MemberRepo memberRepo) {
    this.memberRepo = memberRepo;
  }

  public List<MemberResponse> getAll() {
    return memberRepo.findAll().stream()
        .map(
            memberDao -> {
              return new MemberResponse(
                  memberDao.getUuid(),
                  memberDao.getMemberName(),
                  memberDao.getMembershipValidTill(),
                  memberDao.getMembershipValidTill() == null
                      ? false
                      : memberDao.getMembershipValidTill().after(Date.valueOf(LocalDate.now())));
            })
        .collect(Collectors.toList());
  }

  public MemberDao logMembershipPayment(MembershipDuration paidStatus) {
    Optional<MemberDao> member = memberRepo.findById(paidStatus.memberId());
    if (member.isPresent()) {
      Date currentMembershipDate = member.get().getMembershipValidTill();
      Date newDate;
      if (currentMembershipDate == null
          || currentMembershipDate.before(Date.valueOf(LocalDate.now()))) {
        newDate = Date.valueOf(LocalDate.now().plusDays(paidStatus.membershipDuration()));
      } else {
        newDate =
            Date.valueOf(
                currentMembershipDate.toLocalDate().plusDays(paidStatus.membershipDuration()));
      }
      member.get().setMembershipValidTill(newDate);
      return memberRepo.save(member.get());
    } else {
      throw new MemberNotFoundExpection();
    }
  }
}
