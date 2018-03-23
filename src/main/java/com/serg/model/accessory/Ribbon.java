package com.serg.model.accessory;

public class Ribbon implements Accessory {
    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ribbon{" +
                "price=" + price +
                '}';
    }
}
