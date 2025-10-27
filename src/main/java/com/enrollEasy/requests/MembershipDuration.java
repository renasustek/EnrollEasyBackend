package com.enrollEasy.requests;

import java.util.UUID;

public record MembershipDuration(UUID memberId, int membershipDuration) {}
