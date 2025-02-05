package com.goorm.friendchise.domain.manager.domain;

import java.util.Optional;

public interface ManagerRepository {
	Manager save(Manager manager);

	Optional<Manager> findById(Long id);
}
