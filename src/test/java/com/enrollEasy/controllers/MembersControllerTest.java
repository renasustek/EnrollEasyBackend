package com.enrollEasy.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.PaidStatus;
import com.enrollEasy.service.MemberService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MembersController.class)
public class MembersControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private MemberService memberService;

  @Test
  void getAllMembers() throws Exception {
    List<MemberDao> memberDaoList = new ArrayList<>();
    memberDaoList.add(new MemberDao());
    given(memberService.getMembers()).willReturn(memberDaoList);

    mockMvc.perform(get("/members/getAll")).andExpect(status().isOk());
  }

  @Test
  void whenGivenValidIdChangePaidStatusShouldReturnChangedDao() throws Exception {

    given(memberService.membershipValidDate(any(PaidStatus.class))).willReturn(any(MemberDao.class));

    mockMvc
        .perform(
            post("/members/changePaidStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "        \"memberId\": \"0d221942-44ff-4e29-bc7d-8e1a127f0c44\",\n"
                        + "        \"paid\": true\n"
                        + "}"))
        .andExpect(status().isOk());
  }

  @Test
  void whenGivenInvalidIdChangePaidStatusShould404Error() throws Exception {

    given(memberService.membershipValidDate(any(PaidStatus.class)))
        .willThrow(MemberNotFoundExpection.class);

    mockMvc
        .perform(
            post("/members/changePaidStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "        \"memberId\": \"0d221942-44ff-4e29-bc7d-8e1a127f0c44\",\n"
                        + "        \"paid\": true\n"
                        + "}"))
        .andExpect(status().isNotFound());
  }
}
