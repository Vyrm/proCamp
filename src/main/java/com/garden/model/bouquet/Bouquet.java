package com.garden.model.bouquet;

import com.garden.model.flower.Flower;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bouquet {
    private Long id;
    private List<Flower> bouquet;
    private String name;
    private double price;

    public Bouquet(String name) {
        this.name = name;
        bouquet = new ArrayList<>();
    }

    public Bouquet() {
        bouquet = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addFlower(Flower flower) {
        bouquet.add(flower);

    }

    public double getPrice() {
        double price = 0;
        for (Flower flower : bouquet) {
            price += flower.getPrice();
        }
        return price;
    }

    public String getName() {
        return name;
    }


    public List<Flower> getBouquet() {
        return bouquet;
    }



    public void sortByFresh() {
        List<Flower> list = this.bouquet;
        Collections.sort(list, new Flower());
    }

    @Override
    public String toString() {
        return "Bouquet{" +
                "bouquet=" + bouquet +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bouquet mapRow(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getLong("id"));
        this.setName(resultSet.getString("name"));
        this.setPrice(resultSet.getDouble("assemble_price"));
        return this;
    }
}
