package com.goorm.friendchise.domain.manager.application;

import com.goorm.friendchise.domain.headquarter.domain.HeadquarterRepository;
import com.goorm.friendchise.domain.headquarter.insfrastructure.FakeHeadquarterRepository;
import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.dto.request.ManageCreateRequest;
import com.goorm.friendchise.domain.manager.dto.response.ManagerDetailResponse;
import com.goorm.friendchise.domain.manager.dto.response.ManagerPersistResponse;
import com.goorm.friendchise.domain.manager.exception.ManagerNotFoundException;
import com.goorm.friendchise.domain.manager.infrastructure.FakeManagerRepository;
import com.goorm.friendchise.global.auth.application.AuthService;
import com.goorm.friendchise.global.auth.jwt.JwtProperties;
import com.goorm.friendchise.global.auth.jwt.TokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.goorm.friendchise.domain.manager.domain.Role.HEADQUARTER;
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
		TokenProvider tokenProvider = new TokenProvider(new JwtProperties());
		AuthService authService = new AuthService(managerRepository);
		HeadquarterRepository headquarterRepository = new FakeHeadquarterRepository();
		managerService = new ManagerService(
			managerRepository, bCryptPasswordEncoder,
			tokenProvider, authService, headquarterRepository);

		managerRepository.save(
			Manager.create("test", "test1234", HEADQUARTER)
		);

		UserDetails manger = managerService.findManagerByUsername("test");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(manger, manger.getUsername(), manger.getAuthorities())
		);
	}

	@Test
	@DisplayName("create는 새로운 Manager를 생성")
	void create_success() {
		// given
		ManageCreateRequest request = ManageCreateRequest.builder()
			.username("request")
			.password("test1234")
			.role(HEADQUARTER)
			.build();

		// when
		ManagerPersistResponse response = managerService.create(request);

		// then
		assertNotNull(response);
		assertEquals(2L, response.id());
	}

	@Test
	@DisplayName("detail은 입력된 username으로 ManagerDetailResponse를 반환")
	void detail_success() {
		// given
		String inputName = "test";

		// when
		ManagerDetailResponse detail = managerService.detail(inputName);

		// then
		assertNotNull(detail);
		assertEquals(1L, detail.id());
		assertEquals(inputName, detail.username());
		assertEquals(HEADQUARTER, detail.role());
		assertNull(detail.manageId());
	}

	@Test
	@DisplayName("mypage는 SecurityContextHolder의 정보로 ManagerDetailResponse를 반환")
	void mypage_success() {
		// when
		ManagerDetailResponse mypage = managerService.mypage();

		// then
		assertNotNull(mypage);
		assertEquals(1L, mypage.id());
		assertEquals("test", mypage.username());
		assertEquals(HEADQUARTER, mypage.role());
	}

	@Test
	@DisplayName("updateManager는 Manager의 manageId를 변경")
	void updateManager_success() {
		// given
		Long newStoreId = 1L;

		// when
		managerService.updateManager(newStoreId);
		Manager manager = managerService.findManagerByUsername("test");

		// then
		assertEquals(1L, manager.getId());
	}

	@Test
	@DisplayName("updatePassword는 Manager의 password를 변경")
	void updatePassword_success() {
		// given
		Manager manager = managerService.findManagerByUsername("test");
		String oldPassword = manager.getPassword();
		String newPassword = "newPassword";

		// when
		managerService.updatePassword(newPassword);

		// then
		assertNotEquals(oldPassword, manager.getPassword());
	}

	@Test
	@DisplayName("delete는 Manager를 삭제")
	void delete_success() {
		// when
		managerService.delete();

		// then
		Assertions.assertThrows(
			ManagerNotFoundException.class,
			() -> managerService.findManagerByUsername("test")
		);
	}

	@Test
	@DisplayName("findManagerByUsername은 username으로 Manager를 반환")
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
		assertEquals(HEADQUARTER, manager.getRole());
		assertNull(manager.getManageId());
	}

	@Test
	@DisplayName("findManagerByUsername은 존재하지 않는 username으로 조회시 ManagerNotFoundException")
	void findManagerByUsername_ManagerNotFoundException() {
		// given
		String inputName = "notExist";

		// then
		Assertions.assertThrows(
			ManagerNotFoundException.class,
			() -> managerService.findManagerByUsername(inputName)
		);
	}
}