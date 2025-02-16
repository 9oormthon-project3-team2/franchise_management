package com.goorm.friendchise.domain.headquarter.Item.infrastructure;

import com.goorm.friendchise.domain.headquarter.Item.domain.Item;
import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<Item, Long> {
    Slice<Item> findByHeadquarterId(Long headquarterId, Pageable pageable);
}
