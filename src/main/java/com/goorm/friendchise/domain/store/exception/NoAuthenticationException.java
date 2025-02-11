package com.goorm.friendchise.domain.store.exception;

import com.goorm.friendchise.global.exception.CustomException;
import com.goorm.friendchise.global.exception.ErrorCode;

public class NoAuthenticationException extends CustomException {
    public NoAuthenticationException() {
        super(ErrorCode.NOT_VALID_AUTHENTICATION);
    }
}
