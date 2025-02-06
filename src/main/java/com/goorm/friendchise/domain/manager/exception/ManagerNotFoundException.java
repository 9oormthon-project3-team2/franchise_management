package com.goorm.friendchise.domain.manager.exception;

import com.goorm.friendchise.global.exception.CustomException;

import static com.goorm.friendchise.global.exception.ErrorCode.USER_NOT_FOUND;

public class ManagerNotFoundException extends CustomException {
	public ManagerNotFoundException() {
		super(USER_NOT_FOUND);
	}
}
