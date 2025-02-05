package com.goorm.friendchise.manager.presentation;

import com.goorm.friendchise.manager.application.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerController {
	private final ManagerService managerService;
}
