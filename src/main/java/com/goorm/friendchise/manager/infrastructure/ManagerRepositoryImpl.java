package com.goorm.friendchise.manager.infrastructure;

import com.goorm.friendchise.manager.domain.Manager;
import com.goorm.friendchise.manager.domain.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepository {
	private final JpaManagerRepository jpaManagerRepository;

	@Override
	public Manager save(Manager manager) {
		return jpaManagerRepository.save(manager);
	}

	@Override
	public Optional<Manager> findById(Long id) {
		return jpaManagerRepository.findById(id);
	}
}
