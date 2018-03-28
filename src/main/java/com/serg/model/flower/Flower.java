package com.serg.model.flower;

import java.util.Comparator;

public class Flower implements Comparable<Flower>, Comparator<Flower> {
    long id;
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

    public int compareTo(Flower o) {
        return o.getFresh().ordinal() - this.getFresh().ordinal();
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compare(Flower o1, Flower o2) {
        return o1.getFresh().ordinal() - o2.getFresh().ordinal();
    }
}
