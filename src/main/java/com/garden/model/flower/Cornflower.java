package com.garden.model.flower;

import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;

public class Cornflower extends Flower {
    public Cornflower(String name, int length, Fresh fresh, double price, int petals, boolean spike, Color color) {
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
        return "Cornflower{" +
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
