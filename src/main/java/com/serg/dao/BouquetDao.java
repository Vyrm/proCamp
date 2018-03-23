package com.serg.dao;

import com.serg.builder.Bouquet;
import com.serg.model.flower.Flower;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetDao {
    long addFlower(Flower flower);
    void addBouquet(Bouquet bouquet);
}
