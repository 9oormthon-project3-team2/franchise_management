package com.goorm.friendchise.domain.promotion.infrastructure;

import com.goorm.friendchise.domain.promotion.domain.Promotion;
import com.goorm.friendchise.domain.promotion.domain.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PromotionRepositoryImpl implements PromotionRepository {
	private final JpaPromotionRepository jpaPromotionRepository;

	@Override
	public void save(Promotion promotion) {
		jpaPromotionRepository.save(promotion);
	}

	@Override
	public List<Promotion> findAll() {
		return jpaPromotionRepository.findAll();
	}

	@Override
	public Optional<Promotion> findById(Long id) {
		return jpaPromotionRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		jpaPromotionRepository.deleteById(id);
	}
}
