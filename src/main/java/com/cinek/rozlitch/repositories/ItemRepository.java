package com.cinek.rozlitch.repositories;

import com.cinek.rozlitch.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Cinek on 30.11.2017.
 */
public interface ItemRepository extends JpaRepository<Item,Long> {
}
