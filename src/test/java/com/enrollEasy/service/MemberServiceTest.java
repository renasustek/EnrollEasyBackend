package com.enrollEasy.service;

import com.enrollEasy.persistance.MemberRepo;
import com.enrollEasy.persistance.entites.MemberDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}