package com.enrollEasy.service;

import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class MemberServiceTest {

    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getMembers(){
        List<MemberDao> memberDaoList  = new ArrayList<>();
        memberDaoList.add(new MemberDao());

        given(memberRepo.findAll()).willReturn(memberDaoList);

        List<MemberDao> response = memberService.getMembers();

        assertThat(response).isEqualTo(memberDaoList);

    }

    @Test
    void whenFindMemberChangeStatusAndReturnDao(){
        MemberDao memberDao = new MemberDao();
        given(memberRepo.findById(any(UUID.class))).willReturn(Optional.of(memberDao));
        given(memberRepo.save(memberDao)).willReturn(memberDao);
        MemberDao response = memberService.changePaidStatus(new PaidStatus(UUID.randomUUID(), true));

        assertThat(response.getPaidStatus()).isEqualTo(true);
    }

    @Test
    void whenChangePaidStatusIdInvalidShouldThrowExepction(){
        Optional<MemberDao> memberDao = Optional.empty();
        given(memberRepo.findById(any(UUID.class))).willReturn(memberDao);
        assertThatThrownBy(() ->{
            memberService.changePaidStatus(new PaidStatus(UUID.randomUUID(), true));
        })
                .isInstanceOf(MemberNotFoundExpection.class);
    }
}