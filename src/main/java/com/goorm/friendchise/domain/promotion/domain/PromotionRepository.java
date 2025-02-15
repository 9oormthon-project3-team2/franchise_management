package com.goorm.friendchise.domain.promotion.domain;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository {
	void save(Promotion promotion);

	List<Promotion> findAll();

	Optional<Promotion> findById(Long id);

	void delete(Long id);

	List<Promotion> findByHeadquarterId(Long headquarterId);
}
