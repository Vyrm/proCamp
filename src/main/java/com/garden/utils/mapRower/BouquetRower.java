package com.garden.utils.mapRower;

import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BouquetRower {
    private Long id;
    private List<Flower> bouquet;
    private String name;
    private double price;

    public Bouquet mapRow(ResultSet resultSet, Bouquet bouquet) throws SQLException {
        bouquet = new Bouquet();
        this.id = (resultSet.getLong("id"));
        this.name = (resultSet.getString("name"));
        this.price = (resultSet.getDouble("assemble_price"));
        return bouquet;
    }
}
