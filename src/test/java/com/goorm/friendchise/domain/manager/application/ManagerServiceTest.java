package com.goorm.friendchise.domain.manager.application;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.domain.Role;
import com.goorm.friendchise.domain.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.domain.manager.dto.response.ManagerDetailResponse;
import com.goorm.friendchise.domain.manager.dto.response.ManagerPersistResponse;
import com.goorm.friendchise.domain.manager.exception.ManagerNotFoundException;
import com.goorm.friendchise.domain.manager.infrastructure.FakeManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManagerServiceTest {
	private ManagerService managerService;

	@BeforeEach
	void setUp() {
		ManagerRepository managerRepository = new FakeManagerRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		managerService = new ManagerService(managerRepository, bCryptPasswordEncoder);

		managerRepository.save(
			Manager.create("test", "test1234", Role.HEADQUARTER)
		);
	}

	@Test
	void create_success() {
		ManageCreateRequest request = ManageCreateRequest.builder()
			.username("request")
			.password("test1234")
			.role(Role.HEADQUARTER)
			.build();
		ManagerPersistResponse response = managerService.create(request);
		assertNotNull(response);
		assertEquals(2L, response.id());
	}

	@Test
	void detail_success() {
		String inputName = "test";
		ManagerDetailResponse detail = managerService.detail(inputName);
		assertNotNull(detail);
		assertEquals(1L, detail.id());
		assertEquals(inputName, detail.username());
		assertEquals(Role.HEADQUARTER, detail.role());
		assertNull(detail.manageId());
	}

	@Test
	void updateManager_success() {
		String inputName = "test";
		Long storeId = 1L;
		managerService.updateManager(inputName, storeId);
		assertEquals(storeId, managerService.findManagerByUsername(inputName).getManageId());
	}

	@Test
	void updatePassword_success() {
		String inputName = "test";
		String oldPassword = managerService.findManagerByUsername(inputName).getPassword();
		managerService.updatePassword(inputName, "newPassword");
		String newPassword = managerService.findManagerByUsername(inputName).getPassword();
		assertNotEquals(oldPassword, newPassword);
	}

	@Test
	void delete_success() {
		String inputName = "test";
		managerService.delete(inputName);
		assertThatThrownBy(() -> managerService.findManagerByUsername(inputName))
			.isInstanceOf(ManagerNotFoundException.class);
	}

	@Test
	void findManagerByUsername_success() {
		String inputName = "test";
		Manager manager = managerService.findManagerByUsername(inputName);
		assertNotNull(managerService.findManagerByUsername(inputName));
		assertEquals(1L, manager.getId());
		assertEquals(inputName, manager.getUsername());
		assertEquals("test1234", manager.getPassword());
		assertEquals(Role.HEADQUARTER, manager.getRole());
		assertNull(manager.getManageId());
	}
}