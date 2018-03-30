package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BouquetService {
    @Autowired
    private BouquetDaoImpl bouquetDao;

    public List<Flower> getByLength(Long bouquetId, int low, int high) {
        Bouquet bouquet = bouquetDao.getBouquetById(bouquetId);
        return bouquet.getBouquet().stream().filter(p -> p.getLength() >= low && p.getLength() <= high)
                .collect(Collectors.toList());
    }

    public Bouquet sortBouquet(Bouquet bouquet) {
        bouquet.getBouquet().sort(Comparator.comparing(Flower::getFresh).reversed());
        return bouquet;
    }

    public void setBouquetDao(BouquetDaoImpl bouquetDao) {
        this.bouquetDao = bouquetDao;
    }
}
