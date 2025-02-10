package com.goorm.friendchise.domain.customer.exception;

import com.goorm.friendchise.global.exception.CustomException;
import com.goorm.friendchise.global.exception.ErrorCode;

import static com.goorm.friendchise.global.exception.ErrorCode.USER_NOT_FOUND;

public class CustomerException extends CustomException {

    public CustomerException() {
        super(USER_NOT_FOUND); // 기본 오류 코드
    }

    public CustomerException(ErrorCode errorCode) {
        super(errorCode); // 특정 오류 코드 사용 가능
    }
}
