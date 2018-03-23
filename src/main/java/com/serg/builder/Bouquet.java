package com.serg.builder;

import com.serg.dao.BouquetDao;
import com.serg.dao.BouquetDaoImpl;
import com.serg.model.accessory.Paper;
import com.serg.model.accessory.Ribbon;
import com.serg.model.flower.Flower;

import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private List<Flower> bouquet;
    private Paper paper;
    private Ribbon ribbon;
    private String name;

    public double getPrice() {
        double price = 0;
        for (Flower flower : bouquet) {
            price += flower.getPrice();
        }
        if (paper != null) price += paper.getPrice();
        if (ribbon != null) price += ribbon.getPrice();
        return price;
    }

    public String getName() {
        return name;
    }

    private Bouquet(BouquetBuilder bouquetBuilder) {
        this.bouquet = bouquetBuilder.bouquet;
        this.paper = bouquetBuilder.paper;
        this.ribbon = bouquetBuilder.ribbon;
        this.name = bouquetBuilder.name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bouquet{" + "bouquet=").append(bouquet);
        if (paper != null) stringBuilder.append(", Paper price: ").append(paper.getPrice());
        if (paper != null) stringBuilder.append(", Ribbon price: ").append(ribbon.getPrice());
        stringBuilder.append('}');
        return stringBuilder.toString();
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

    public static class BouquetBuilder {
        private List<Flower> bouquet = new ArrayList<Flower>();
        private Paper paper;
        private Ribbon ribbon;
        private String name;

        public BouquetBuilder() {

        }

        public BouquetBuilder addFlower(Flower flower) {
            bouquet.add(flower);
            return this;
        }

        public BouquetBuilder setPaper(int price) {
            paper = new Paper();
            paper.setPrice(price);
            return this;
        }

        public BouquetBuilder setRibbon(int price) {
            ribbon = new Ribbon();
            ribbon.setPrice(price);
            return this;
        }

        public BouquetBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Bouquet build() {
            BouquetDao bouquetDao = new BouquetDaoImpl();
            bouquetDao.addBouquet(new Bouquet(this));
            return new Bouquet(this);
        }
    }
}
