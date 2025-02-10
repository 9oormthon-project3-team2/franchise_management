package com.goorm.friendchise.domain.manager.exception;

import com.goorm.friendchise.global.exception.CustomException;

import static com.goorm.friendchise.global.exception.ErrorCode.TOKEN_NOT_FOUND;

public class TokenNotFoundException extends CustomException {
	public TokenNotFoundException() {
		super(TOKEN_NOT_FOUND);
	}
}
