package com.goorm.friendchise.domain.promotion.infrastructure;

import com.goorm.friendchise.domain.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPromotionRepository extends JpaRepository<Promotion, Long> {
	List<Promotion> findByHeadquarterId(Long headquarterId);
}
