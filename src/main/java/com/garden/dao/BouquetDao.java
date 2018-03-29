package com.garden.dao;

import com.garden.model.bouquet.Bouquet;

public interface BouquetDao {
    long addBouquet(Bouquet bouquet);
    Bouquet getBouquetById(long id);
}
