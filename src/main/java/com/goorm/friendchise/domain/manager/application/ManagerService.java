package com.goorm.friendchise.domain.manager.application;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.domain.manager.dto.request.ManageLoginRequest;
import com.goorm.friendchise.domain.manager.dto.response.ManagerDetailResponse;
import com.goorm.friendchise.domain.manager.dto.response.ManagerPersistResponse;
import com.goorm.friendchise.domain.manager.dto.response.ManagerTokenResponse;
import com.goorm.friendchise.domain.manager.exception.ManagerNotFoundException;
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

	// TODO 로그인
	public ManagerTokenResponse login(ManageLoginRequest request) {
		return ManagerTokenResponse.of("", "");
	}

	@Transactional(readOnly = true)
	public ManagerDetailResponse detail(String username) {
		Manager manager = findManagerByUsername(username);
		return ManagerDetailResponse.from(manager);
	}

	@Transactional
	public void updateManager(String username, Long newStoreId) {
		Manager manager = findManagerByUsername(username);
		manager.updateManageId(newStoreId);
	}

	@Transactional
	public void updatePassword(String username, String newPassword) {
		String encode = bCryptPasswordEncoder.encode(newPassword);
		Manager manager = findManagerByUsername(username);
		manager.updatePassword(encode);
	}

	@Transactional
	public void delete(String username) {
		Manager manager = findManagerByUsername(username);
		managerRepository.delete(manager);
	}

	@Transactional
	public Manager findManagerByUsername(String username) {
		return managerRepository.findByUsername(username)
			.orElseThrow(ManagerNotFoundException::new);
	}
}
