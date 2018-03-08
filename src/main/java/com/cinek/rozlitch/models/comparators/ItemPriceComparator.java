package com.cinek.rozlitch.models.comparators;

import com.cinek.rozlitch.models.Item;

import java.util.Comparator;

/**
 * Created by Cinek on 20.01.2018.
 */
public class ItemPriceComparator implements Comparator<Item>{
    @Override
    public int compare(Item o1, Item o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
