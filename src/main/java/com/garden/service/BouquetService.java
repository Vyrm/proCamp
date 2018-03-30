package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BouquetService {
    private BouquetDaoImpl bouquetDao;

    public List<Flower> getByLength(Long bouquetId, int low, int high) {
        Bouquet bouquet = bouquetDao.getBouquetById(bouquetId);
        return bouquet.getBouquet().stream().filter(p -> p.getLength() >= low && p.getLength() <= high)
                .collect(Collectors.toList());
    }

    public Bouquet sortBouquet(Bouquet bouquet){
        bouquet.getBouquet().sort(Comparator.comparing(Flower::getFresh).reversed());
        return bouquet;
    }

    public void setBouquetDao(BouquetDaoImpl bouquetDao) {
        this.bouquetDao = bouquetDao;
    }
}
