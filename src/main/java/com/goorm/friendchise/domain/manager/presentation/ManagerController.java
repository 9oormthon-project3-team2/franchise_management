package com.goorm.friendchise.domain.manager.presentation;

import com.goorm.friendchise.domain.manager.application.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerController {
	private final ManagerService managerService;
}
