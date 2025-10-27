package com.enrollEasy.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.enrollEasy.controllers.responses.MemberResponse;
import com.enrollEasy.exception.MemberNotFoundExpection;
import com.enrollEasy.persistance.entites.MemberDao;
import com.enrollEasy.requests.MembershipDuration;
import com.enrollEasy.service.MemberService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

  private UUID uuid = UUID.randomUUID();
  private MemberResponse memberResponse =
      new MemberResponse(uuid, "test", Date.valueOf(LocalDate.now()), true);

  @Test
  void getAllMembers() throws Exception {
    List<MemberResponse> mockList = new ArrayList<>();
    mockList.add(memberResponse);

    given(memberService.getAll()).willReturn(mockList);

    mockMvc
        .perform(get("/members/getAll"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].uuid").value(memberResponse.uuid().toString()))
        .andExpect(jsonPath("$[0].memberName").value(memberResponse.memberName().toString()))
        .andExpect(
            jsonPath("$[0].membershipValidTill")
                .value(memberResponse.membershipValidTill().toString()));
  }

  @Test
  void whenGivenValidIdChangePaidStatusShouldReturnChangedDao() throws Exception {

    given(memberService.logMembershipPayment(any(MembershipDuration.class)))
        .willReturn(any(MemberDao.class));

    mockMvc
        .perform(
            post("/members/memberPaid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "        \"memberId\": \"0d221942-44ff-4e29-bc7d-8e1a127f0c44\",\n"
                        + "        \"membershipDuration\": 3\n"
                        + "}"))
        .andExpect(status().isOk());
  }

  @Test
  void whenGivenInvalidIdChangePaidStatusShould404Error() throws Exception {

    given(memberService.logMembershipPayment(any(MembershipDuration.class)))
        .willThrow(MemberNotFoundExpection.class);

    mockMvc
        .perform(
            post("/members/changePaidStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "        \"memberId\": \"0d221942-44ff-4e29-bc7d-8e1a127f0c44\",\n"
                        + "        \"membershipDuration\": 3\n"
                        + "}"))
        .andExpect(status().isNotFound());
  }
}
