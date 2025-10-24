package com.enrollEasy.controllers;

import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MembersController.class)
public class MembersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;
    @Test
    void getAllMembers() throws Exception {
        List<MemberDao> memberDaoList  = new ArrayList<>();
        memberDaoList.add(new MemberDao());
        given(memberService.getMembers()).willReturn(memberDaoList);

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk());
    }
}