package com.goorm.friendchise.domain.headquarter.Item.domain;

import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
    Slice<Item> findByHeadquarterId(Long headquarterId, Pageable pageable);
}
