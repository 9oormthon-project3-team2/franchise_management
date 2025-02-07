package com.goorm.friendchise.domain.manager.application;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.domain.Role;
import com.goorm.friendchise.domain.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.domain.manager.dto.response.ManagerDetailResponse;
import com.goorm.friendchise.domain.manager.dto.response.ManagerPersistResponse;
import com.goorm.friendchise.domain.manager.infrastructure.FakeManagerRepository;
import com.goorm.friendchise.global.auth.application.AuthService;
import com.goorm.friendchise.global.auth.jwt.JwtProperties;
import com.goorm.friendchise.global.auth.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManagerServiceTest {
	private ManagerService managerService;

	@BeforeEach
	void setUp() {
		ManagerRepository managerRepository = new FakeManagerRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		TokenProvider tokenProvider = new TokenProvider(new JwtProperties());
		AuthService authService = new AuthService(managerRepository);
		managerService = new ManagerService(managerRepository, bCryptPasswordEncoder, tokenProvider, authService);

		managerRepository.save(
			Manager.create("test", "test1234", Role.HEADQUARTER)
		);

		UserDetails manger = managerService.findManagerByUsername("test");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(manger, manger.getUsername(), manger.getAuthorities())
		);
	}

	@Test
	void create_success() {
		// given
		ManageCreateRequest request = ManageCreateRequest.builder()
			.username("request")
			.password("test1234")
			.role(Role.HEADQUARTER)
			.build();

		// when
		ManagerPersistResponse response = managerService.create(request);

		// then
		assertNotNull(response);
		assertEquals(2L, response.id());
	}

	@Test
	void detail_success() {
		// given
		String inputName = "test";

		// when
		ManagerDetailResponse detail = managerService.detail(inputName);

		// then
		assertNotNull(detail);
		assertEquals(1L, detail.id());
		assertEquals(inputName, detail.username());
		assertEquals(Role.HEADQUARTER, detail.role());
		assertNull(detail.manageId());
	}

	@Test
	void mypage_success() {
		// given
		// when
		ManagerDetailResponse mypage = managerService.mypage();

		// then
		assertNotNull(mypage);
		assertEquals(1L, mypage.id());
		assertEquals("test", mypage.username());
		assertEquals(Role.HEADQUARTER, mypage.role());
	}

	@Test
	void findManagerByUsername_success() {
		// given
		String inputName = "test";

		// when
		Manager manager = managerService.findManagerByUsername(inputName);

		// then
		assertNotNull(managerService.findManagerByUsername(inputName));
		assertEquals(1L, manager.getId());
		assertEquals(inputName, manager.getUsername());
		assertEquals("test1234", manager.getPassword());
		assertEquals(Role.HEADQUARTER, manager.getRole());
		assertNull(manager.getManageId());
	}
}