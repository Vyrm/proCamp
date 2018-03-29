package com.garden.model.flower;

import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;

import java.util.Comparator;

public class Flower implements Comparable<Flower>, Comparator<Flower> {
    Long id;
    String name;
    int length;
    Enum fresh;
    double price;
    int petals;
    boolean spike;
    Enum color;

    public Flower(String name, int length, Enum fresh, double price, int petals, boolean spike, Enum color) {
        this.name = name;
        this.length = length;
        this.fresh = fresh;
        this.price = price;
        this.petals = petals;
        this.spike = spike;
        this.color = color;
    }

    public Flower() {
    }

    public int getLength() {
        return length;
    }

    public double getPrice() {
        return price;
    }

    public Enum getFresh() {
        return fresh;
    }

    public Enum getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getPetals() {
        return petals;
    }

    public boolean isSpike() {
        return spike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int compareTo(Flower o) {
        return o.getFresh().ordinal() - this.getFresh().ordinal();
    }

    @Override
    public int compare(Flower o1, Flower o2) {
        return o1.getFresh().ordinal() - o2.getFresh().ordinal();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setFresh(String fresh) {
        this.fresh = Fresh.valueOf(fresh);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPetals(int petals) {
        this.petals = petals;
    }

    public void setSpike(boolean spike) {
        this.spike = spike;
    }

    public void setColor(String color) {
        this.color = Color.valueOf(color);
    }

    @Override
    public String toString() {
        return "Flower{" +
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
