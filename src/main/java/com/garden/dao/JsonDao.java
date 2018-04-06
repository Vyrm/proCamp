package com.garden.dao;

import com.garden.model.bouquet.Bouquet;

import java.util.Collection;

public interface JsonDao {
    boolean exportToJson(Bouquet bouquet);

    Collection<Bouquet> importFromJson();
}
