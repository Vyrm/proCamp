package com.garden.utils.mapRower;

import com.garden.model.flower.Flower;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FlowerRower {
    private Flower flower;

    public Flower mapRow(ResultSet resultSet) throws SQLException {
        flower = new Flower();
        flower.setId(resultSet.getLong("id"));
        flower.setName(resultSet.getString("name"));
        flower.setLength(resultSet.getInt("length"));
        flower.setFresh(resultSet.getString("freshness"));
        flower.setPrice(resultSet.getDouble("price"));
        flower.setPetals(resultSet.getInt("petals"));
        flower.setSpike(resultSet.getBoolean("spike"));
        flower.setColor(resultSet.getString("color"));
        return flower;
    }
}
