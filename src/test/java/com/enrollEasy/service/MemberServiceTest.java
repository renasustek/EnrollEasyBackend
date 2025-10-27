package com.enrollEasy.service;

import com.enrollEasy.controllers.responses.MemberResponse;
import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.MembershipDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class MemberServiceTest {

  @Mock private MemberRepo memberRepo;

  @InjectMocks private MemberService memberService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private static UUID uuid = UUID.randomUUID();

  private static MemberDao memberDaoCreator(Date date) {
    MemberDao memberDao = new MemberDao();
    memberDao.setUuid(uuid);
    memberDao.setMemberName("memberName");
    memberDao.setMembershipValidTill(date);
    return memberDao;
  }

  @Test
  void getAllWhenDateIsNowShouldReturnReturnFalseInIsPaymentTrue() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now())));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.get(0).isMembershipValid()).isEqualTo(false);
  }

  @Test
  void getAllWhenDateIsAfterShouldReturnReturnTrueInIsPaymentTrue() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now().plusDays(1))));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.get(0).isMembershipValid()).isEqualTo(true);
  }

  @Test
  void getAllWhenDateIsBeforeShouldReturnReturnTrueInIsPaymentTrue() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now().minusDays(1))));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.get(0).isMembershipValid()).isEqualTo(false);
  }

  @Test
  void getAllWhenDateIsNullShouldReturnReturnFalseInIsPaymentTrue() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(null));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.get(0).isMembershipValid()).isEqualTo(false);
  }

  @Test
  void whenMultipleValuesInListFromTestsAboveStillShouldPass() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(null));
    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now())));
    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now().plusDays(1))));
    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now().minusDays(1))));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.get(0).isMembershipValid()).isEqualTo(false);
    assertThat(response.get(1).isMembershipValid()).isEqualTo(false);
    assertThat(response.get(2).isMembershipValid()).isEqualTo(true);
    assertThat(response.get(3).isMembershipValid()).isEqualTo(false);
  }

  @Test
  void whenValidMemberDaoShouldCorrectlyTranslateToMemberResponseList() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    memberDaoList.add(memberDaoCreator(Date.valueOf(LocalDate.now())));

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response.getFirst().uuid()).isEqualTo(uuid);
    assertThat(response.getFirst().memberName()).isEqualTo("memberName");
    assertThat(response.getFirst().membershipValidTill()).isEqualTo(Date.valueOf(LocalDate.now()));
    assertThat(response.getFirst().isMembershipValid()).isEqualTo(false);
  }

  @Test
  void whenEmptyListReturnedFromRepoShouldReturnEmptyList() {
    List<MemberDao> memberDaoList = new ArrayList<>();

    given(memberRepo.findAll()).willReturn(memberDaoList);

    List<MemberResponse> response = memberService.getAll();

    assertThat(response).isEmpty();
  }

  @Test
  void whenMembershipDateIsNullShouldReturnMemberDatWithCurrentDatePlusDays() {
    int memDuration = 3;

    MemberDao memberDao = memberDaoCreator(null);
    given(memberRepo.findById(any(UUID.class))).willReturn(Optional.of(memberDao));
    given(memberRepo.save(memberDao)).willReturn(memberDao);
    MemberDao response =
        memberService.logMembershipPayment(new MembershipDuration(UUID.randomUUID(), memDuration));

    assertThat(response.getMembershipValidTill())
        .isEqualTo(Date.valueOf(LocalDate.now().plusDays(memDuration)));
  }

  @Test
  void whenMembershipDateIsBeforeShouldReturnMemberDatWithCurrentDatePlusDays() {
    int memDuration = 3;

    MemberDao memberDao = memberDaoCreator(Date.valueOf(LocalDate.now().minusDays(1)));
    given(memberRepo.findById(any(UUID.class))).willReturn(Optional.of(memberDao));
    given(memberRepo.save(memberDao)).willReturn(memberDao);
    MemberDao response =
        memberService.logMembershipPayment(new MembershipDuration(UUID.randomUUID(), memDuration));

    assertThat(response.getMembershipValidTill())
        .isEqualTo(Date.valueOf(LocalDate.now().plusDays(memDuration)));
  }

  @Test
  void whenMembershipDateIsAfterShouldAddToThatDate() {
    int memDuration = 3;

    MemberDao memberDao = memberDaoCreator(Date.valueOf(LocalDate.now().plusDays(memDuration)));
    given(memberRepo.findById(any(UUID.class))).willReturn(Optional.of(memberDao));
    given(memberRepo.save(memberDao)).willReturn(memberDao);
    MemberDao response =
        memberService.logMembershipPayment(new MembershipDuration(UUID.randomUUID(), memDuration));

    assertThat(response.getMembershipValidTill())
        .isEqualTo(Date.valueOf(LocalDate.now().plusDays(memDuration + memDuration)));
  }

  @Test
  void whenMembershipDateIsNowShouldAddToThatDate() {
    int memDuration = 3;

    MemberDao memberDao = memberDaoCreator(Date.valueOf(LocalDate.now()));
    given(memberRepo.findById(any(UUID.class))).willReturn(Optional.of(memberDao));
    given(memberRepo.save(memberDao)).willReturn(memberDao);
    MemberDao response =
        memberService.logMembershipPayment(new MembershipDuration(UUID.randomUUID(), memDuration));

    assertThat(response.getMembershipValidTill())
        .isEqualTo(Date.valueOf(LocalDate.now().plusDays(memDuration)));
  }

  @Test
  void whenLogMembershipPaymentIdInvalidShouldThrowExepction() {
    Optional<MemberDao> memberDao = Optional.empty();
    given(memberRepo.findById(any(UUID.class))).willReturn(memberDao);
    assertThatThrownBy(
            () -> {
              memberService.logMembershipPayment(new MembershipDuration(UUID.randomUUID(), 3));
            })
        .isInstanceOf(MemberNotFoundExpection.class);
  }
}
