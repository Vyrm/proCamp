package com.serg.builder;

import com.serg.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bouquet {
    private static final Logger logger = LoggerFactory.getLogger("Bouquet");
    private List<Flower> bouquet;
    private String name;
    private boolean isEmpty = false;

    private Bouquet(BouquetBuilder bouquetBuilder) {
        this.bouquet = bouquetBuilder.bouquet;
        this.name = bouquetBuilder.name;
        this.isEmpty = bouquetBuilder.isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
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


    @Override
    public String toString() {
        return "Bouquet{" +
                "bouquet=" + bouquet +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Flower> getBouquet() {
        return bouquet;
    }

    public List<Flower> getByLength(int low, int high) {
        List<Flower> list = new ArrayList<Flower>();
        for (Flower flower : bouquet) {
            if (flower.getLength() >= low && flower.getLength() <= high) list.add(flower);
        }
        return list;
    }

    public void sortByFresh() {
        List<Flower> list = this.bouquet;
        Collections.sort(list, new Flower());
    }

    public static class BouquetBuilder {
        private List<Flower> bouquet;
        private String name;
        private boolean isEmpty = false;

        public BouquetBuilder() {
            bouquet = new ArrayList<Flower>();
        }

        public BouquetBuilder addFlower(Flower flower) {
            if (!(flower.getId() == 0)) {
                bouquet.add(flower);
            }
            return this;
        }

        public BouquetBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Bouquet build(){
            if (!(this.bouquet.size() == 0)) {
                return new Bouquet(this);
            } else isEmpty = true;
            return new Bouquet(this);
        }
    }
}
