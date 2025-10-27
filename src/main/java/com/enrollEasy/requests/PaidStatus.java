package com.enrollEasy.requests;

import java.util.UUID;

public record PaidStatus(UUID memberId, int numberOfMembershipDaysPaidFor) {}
