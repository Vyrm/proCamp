package com.garden.model.flower;

public class Dandelion extends Flower {
    public Dandelion(String name, int length, Enum fresh, double price, int petals, boolean spike, Enum color) {
        super(name, length, fresh, price, petals, spike, color);
    }

    public int getLength() {
        return length;
    }

    public Enum getFresh() {
        return fresh;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dandelion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", fresh=" + fresh +
                ", price=" + price +
                ", petals=" + petals +
                ", spike=" + spike +
                ", color=" + color +
                '}';
    }
}
