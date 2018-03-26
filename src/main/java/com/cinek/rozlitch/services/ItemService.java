package com.cinek.rozlitch.services;

import com.cinek.rozlitch.models.Item;
import com.cinek.rozlitch.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cinek on 26.03.2018.
 */
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public void save(Item item) {
       itemRepository.save(item);
    }

}
