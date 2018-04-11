package com.garden.model.flower;

import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;

public class Dandelion extends Flower {
    public Dandelion(String name, int length, Fresh fresh, double price, int petals, boolean spike, Color color) {
        super(name, length, fresh, price, petals, spike, color);
    }

    public Dandelion(final Flower flower) {
        this(flower.name, flower.length, flower.fresh, flower.price, flower.petals, flower.spike, flower.color);
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
