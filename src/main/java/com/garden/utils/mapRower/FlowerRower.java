package com.garden.utils.mapRower;

import com.garden.model.flower.Flower;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FlowerRower {
    private Long id;
    private String name;
    private int length;
    private Enum fresh;
    private double price;
    private int petals;
    private boolean spike;
    private Enum color;


    public Flower mapRow(ResultSet resultSet) throws SQLException {
        Flower flower = new Flower();
        this.id = (resultSet.getLong("id"));
        this.name = (resultSet.getString("name"));
        this.length = (resultSet.getInt("length"));
        this.setFresh(resultSet.getString("freshness"));
        this.price = (resultSet.getDouble("price"));
        this.petals = (resultSet.getInt("petals"));
        this.spike = (resultSet.getBoolean("spike"));
        this.setColor(resultSet.getString("color"));
        return flower;
    }


    public void setFresh(String fresh) {
        this.fresh = Fresh.valueOf(fresh);
    }

    public void setColor(String color) {
        this.color = Color.valueOf(color);
    }
}
