package com.garden.model.bouquet;

import com.garden.model.flower.Flower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bouquet {
    private Long id;
    private List<Flower> flowers;
    private String name;
    private double price;

    public Bouquet(String name) {
        this.name = name;
        flowers = new ArrayList<>();
    }

    public Bouquet() {
        flowers = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addFlower(Flower flower) {
        flowers.add(flower);

    }

    public double getPrice() {
        double price = 0;
        for (Flower flower : flowers) {
            price += flower.getPrice();
        }
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void sortByFresh() {
        List<Flower> list = this.flowers;
        Collections.sort(list, new Flower());
    }

    @Override
    public String toString() {
        return "Bouquet{" +
                "flowers=" + flowers +
                ", name='" + name + '\'' +
                '}';
    }


}
