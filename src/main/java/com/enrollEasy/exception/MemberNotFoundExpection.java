package com.enrollEasy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MemberNotFoundExpection extends RuntimeException {
    public MemberNotFoundExpection() {
        super("404 error, member not found");
    }
}