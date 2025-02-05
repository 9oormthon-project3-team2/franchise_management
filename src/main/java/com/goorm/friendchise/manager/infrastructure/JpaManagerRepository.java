package com.goorm.friendchise.manager.infrastructure;

import com.goorm.friendchise.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManagerRepository extends JpaRepository<Manager, Long> {
}
