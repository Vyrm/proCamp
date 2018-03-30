package com.garden.dao;

import com.garden.model.bouquet.Bouquet;

import java.sql.SQLException;

public interface BouquetDao {
    Long addBouquet(Bouquet bouquet) throws SQLException;

    Bouquet getBouquetById(long id) throws SQLException;
}
