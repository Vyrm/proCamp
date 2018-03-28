package com.serg.dao;

import com.serg.builder.Bouquet;
import com.serg.model.flower.Flower;

public interface BouquetDao {
    long addFlower(Flower flower);

    long addBouquet(Bouquet bouquet);
}
