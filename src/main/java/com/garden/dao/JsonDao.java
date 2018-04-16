package com.garden.dao;

import com.garden.model.bouquet.Bouquet;

import java.util.Collection;

public interface JsonDao {
    boolean exportJsonToFile(Bouquet bouquet);

    Collection<Bouquet> importFromJson();

    String getJson(Bouquet bouquet);
}
