package com.garden.model.flower;

import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flower implements Comparable<Flower>, Comparator<Flower> {
    Long id;
    String name;
    int length;
    Fresh fresh;
    double price;
    int petals;
    boolean spike;
    Color color;

    public Flower(String name, int length, Fresh fresh, double price, int petals, boolean spike, Color color) {
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

    public void setLength(int length) {
        this.length = length;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Enum getFresh() {
        return fresh;
    }

    public void setFresh(String fresh) {
        this.fresh = Fresh.valueOf(fresh);
    }

    public Enum getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = Color.valueOf(color);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPetals() {
        return petals;
    }

    public void setPetals(int petals) {
        this.petals = petals;
    }

    public boolean isSpike() {
        return spike;
    }

    public void setSpike(boolean spike) {
        this.spike = spike;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int compareTo(Flower o) {
        return o.getFresh().ordinal() - this.getFresh().ordinal();
    }

    @Override
    public int compare(Flower o1, Flower o2) {
        return o1.getFresh().ordinal() - o2.getFresh().ordinal();
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
