package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BouquetService {
    private BouquetDaoImpl bouquetDao;

    public List<Flower> getByLength(Long bouquetId, int low, int high) {
        Bouquet bouquet = bouquetDao.getBouquetById(bouquetId);
        List<Flower> list = new ArrayList<>();
        for (Flower flower : bouquet.getBouquet()) {
            if (flower.getLength() >= low && flower.getLength() <= high) list.add(flower);
        }
        return list;
    }

    public Bouquet sortBouquete(Bouquet bouquet){
        Collections.sort(bouquet.getBouquet());
        return bouquet;
    }

    public void setBouquetDao(BouquetDaoImpl bouquetDao) {
        this.bouquetDao = bouquetDao;
    }
}
