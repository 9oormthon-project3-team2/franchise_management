package com.goorm.friendchise.manager.presentation;

import com.goorm.friendchise.manager.application.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ManagerController {
	private final ManagerService managerService;
}
