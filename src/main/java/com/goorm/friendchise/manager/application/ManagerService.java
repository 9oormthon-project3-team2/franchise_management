package com.goorm.friendchise.manager.application;

import com.goorm.friendchise.manager.domain.Manager;
import com.goorm.friendchise.manager.domain.ManagerRepository;
import com.goorm.friendchise.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.manager.dto.response.ManagerPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public ManagerPersistResponse create(ManageCreateRequest request) {
		String encodedPassword = bCryptPasswordEncoder.encode(request.password());
		Manager manager = Manager.create(request.username(), encodedPassword, request.role());
		Long id = managerRepository.save(manager).getId();
		return ManagerPersistResponse.of(id);
	}
}
