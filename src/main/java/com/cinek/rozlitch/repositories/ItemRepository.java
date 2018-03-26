package com.cinek.rozlitch.repositories;

import com.cinek.rozlitch.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cinek on 30.11.2017.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
