package com.goorm.friendchise.domain.headquarter.Item.domain;

import com.goorm.friendchise.domain.headquarter.Item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
