package com.goorm.friendchise.domain.manager.infrastructure;

import com.goorm.friendchise.domain.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManagerRepository extends JpaRepository<Manager, Long> {
}
