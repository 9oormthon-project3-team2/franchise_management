package com.goorm.friendchise.domain.manager.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {
	@Test
	void create_success() {
		Manager manager = Manager.create("test", "test1234", Role.HEADQUARTER);
		assertNotNull(manager);
		assertEquals("test", manager.getUsername());
		assertEquals("test1234", manager.getPassword());
		assertEquals(Role.HEADQUARTER, manager.getRole());
	}

	@Test
	void updateManageId_success() {
		Manager manager = Manager.create("test", "test1234", Role.HEADQUARTER);
		manager.updateManageId(1L);
		assertEquals(1L, manager.getManageId());
	}

	@Test
	void updatePassword_success() {
		Manager manager = Manager.create("test", "test1234", Role.HEADQUARTER);
		manager.updatePassword("newPassword");
		assertEquals("newPassword", manager.getPassword());
	}

}