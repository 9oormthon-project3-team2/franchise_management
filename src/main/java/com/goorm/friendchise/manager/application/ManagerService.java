package com.goorm.friendchise.manager.application;

import com.goorm.friendchise.manager.domain.Manager;
import com.goorm.friendchise.manager.domain.ManagerRepository;
import com.goorm.friendchise.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.manager.dto.response.ManagerPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;

	public ManagerPersistResponse create(ManageCreateRequest request) {
		Manager manager = Manager.create(request.username(), request.password(), request.role());
		Long id = managerRepository.save(manager).getId();
		return ManagerPersistResponse.of(id);
	}
}
