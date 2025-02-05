package com.goorm.friendchise.domain.manager.infrastructure;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeManagerRepository implements ManagerRepository {

	private final List<Manager> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(1);


	@Override
	public Manager save(Manager manager) {
		Manager built = Manager.builder()
			.id(sequence.getAndIncrement())
			.username(manager.getUsername())
			.password(manager.getPassword())
			.role(manager.getRole())
			.manageId(manager.getManageId())
			.build();
		data.add(built);
		return built;
	}

	@Override
	public Optional<Manager> findById(Long id) {
		return data.stream().filter(
				manager -> manager.getId().equals(id)
			)
			.findFirst();
	}

	@Override
	public Optional<Manager> findByUsername(String username) {
		return data.stream().filter(
				manager -> manager.getUsername().equals(username)
			)
			.findFirst();
	}

	@Override
	public void delete(Manager manager) {
		data.remove(manager);
	}
}
