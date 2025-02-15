package com.goorm.friendchise.domain.headquarter.Item.application;

import com.goorm.friendchise.domain.customer.domain.CustomerRepository;
import com.goorm.friendchise.domain.customer.infrastructure.FakeCustomerRepository;
import com.goorm.friendchise.domain.headquarter.Item.dto.ItemReqDto;
import com.goorm.friendchise.domain.headquarter.Item.dto.ItemReqDtoList;
import com.goorm.friendchise.domain.headquarter.Item.dto.ItemResDto;
import com.goorm.friendchise.domain.headquarter.application.HeadquarterService;
import com.goorm.friendchise.domain.headquarter.domain.Category;
import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import com.goorm.friendchise.domain.headquarter.domain.HeadquarterRepository;
import com.goorm.friendchise.domain.headquarter.domain.SubCategory;
import com.goorm.friendchise.domain.headquarter.infrastructure.HeadquarterRepositoryImpl;
import com.goorm.friendchise.domain.headquarter.insfrastructure.FakeHeadquarterRepository;
import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.infrastructure.FakeManagerRepository;
import com.goorm.friendchise.global.auth.application.AuthService;
import com.goorm.friendchise.global.auth.domain.RefreshTokenRepository;
import com.goorm.friendchise.global.auth.infrastructure.FakeRefreshTokenRepository;
import com.goorm.friendchise.global.auth.jwt.JwtProperties;
import com.goorm.friendchise.global.auth.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.goorm.friendchise.domain.manager.domain.Role.HEADQUARTER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = {"com.goorm.friendchise.domain.headquarter", "com.goorm.friendchise.domain.store"})
@EnableJpaRepositories(basePackages = {"com.goorm.friendchise.domain.headquarter", "com.goorm.friendchise.domain.store"})
@Import({HeadquarterRepositoryImpl.class})
class ItemServiceTest {

    @Autowired
    private HeadquarterRepository headquarterRepository;

    private AuthService authService;
    private ManagerRepository managerRepository;

    private ItemService itemService;


    @BeforeEach
    void setup() {
        managerRepository = new FakeManagerRepository();
        CustomerRepository customerRepository = new FakeCustomerRepository();
        TokenProvider tokenProvider = new TokenProvider(new JwtProperties());
        RefreshTokenRepository refreshTokenRepository = new FakeRefreshTokenRepository();
        authService = new AuthService(managerRepository, tokenProvider, refreshTokenRepository, headquarterRepository, customerRepository);
        itemService = new ItemService(headquarterRepository, authService);
    }

    private void setContextHolder(Manager manager) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(manager, manager.getUsername(), manager.getAuthorities())
        );
    }

    private Long createManagerAndHeadquarter() {
        Manager manager = Manager.create("test", "test1234", HEADQUARTER);
        Manager savedManager = managerRepository.save(manager);
        setContextHolder(savedManager);

        Headquarter headquarter = Headquarter.builder()
                .franchiseName("test")
                .category(Category.FASTFOOD)
                .subCategory(SubCategory.NONE)
                .build();
        Headquarter savedHeadquarter = headquarterRepository.save(headquarter);
        savedManager.updateManageId(savedHeadquarter.getId());

        // 원래는 manager create 하고 바로 save 해야 하지만 그렇게 하면 managerId 업데이트가 repository에서 꺼내올 때 반영이 안되므로
        // 근데 왜 안되는거지
//		managerRepository.save(manager);
//		setContextHolder(manager);
        return savedHeadquarter.getId();
    }

    @Test
    @DisplayName("Cascade persist로 다대일 양방향 연관관계인 Headquarter외 Item들이 모두 잘 저장되는지 확인")
    void testCreateItems_CascadePersist() {
        // given: Headquarter 생성 및 저장
        Long savedHeadquarterId = createManagerAndHeadquarter();

        // given: ItemReqDtoList 생성 (예: 2개의 아이템 요청)
        List<ItemReqDto> itemReqDtos = List.of(
                ItemReqDto.of("item1", 1000),
                ItemReqDto.of("item2", 2000)
        );
        ItemReqDtoList itemReqDtoList = new ItemReqDtoList(itemReqDtos);

        // when: createItems 메서드 호출
        List<ItemResDto> itemResDtos = itemService.createItems(itemReqDtoList);

        // then: Headquarter와 연관된 Item이 DB에 저장되었는지 검증
        Headquarter foundHeadquarter = headquarterRepository.findById(savedHeadquarterId)
                .orElseThrow(() -> new RuntimeException("Headquarter not found"));

        // Headquarter의 items 컬렉션에 2개의 Item이 저장되어 있는지 확인
        assertThat(foundHeadquarter.getItems()).hasSize(2);

        // 각 Item의 headquarter 필드가 올바르게 설정되었는지 확인
        foundHeadquarter.getItems().forEach(item ->
                assertThat(item.getHeadquarter()).isEqualTo(foundHeadquarter)
        );

        // 결과 DTO 검증
        assertThat(itemResDtos).hasSize(2);
        List<String> itemNames = itemResDtos.stream()
                .map(ItemResDto::name)
                .collect(Collectors.toList());
        assertThat(itemNames).containsExactlyInAnyOrder("item1", "item2");
    }
}