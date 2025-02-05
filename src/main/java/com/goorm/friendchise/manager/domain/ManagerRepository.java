package com.goorm.friendchise.manager.domain;

import java.util.Optional;

public interface ManagerRepository {
	public Manager save(Manager manager);

	public Optional<Manager> findById(Long id);
}
