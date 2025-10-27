package com.enrollEasy.controllers.responses;

import java.sql.Date;
import java.util.UUID;

public record MemberResponse(
    UUID uuid, String memberName, Date membershipValidTill, boolean isMembershipValid) {}
