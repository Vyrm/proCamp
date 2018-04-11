package com.garden.model.bouquet;

import com.garden.model.flower.Flower;
import org.springframework.lang.NonNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bouquet {
    private Long id;
    private List<Flower> flowers = new ArrayList<>();
    private String name;
    private double price;

    public Bouquet(String name) {
        this.name = name;
        flowers = new ArrayList<>();
    }

    public Bouquet() {

    }

    public Bouquet(@NonNull Collection<Flower> flowers) {
        if (Objects.nonNull(flowers))
            this.flowers.addAll(flowers);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addFlower(Flower flower) {
        flowers.add(flower);
        updatePrice();
    }

    public double getPrice() {
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
        return Collections.unmodifiableList(flowers);
    }

    private void updatePrice() {
        price = flowers.stream().mapToDouble(Flower::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Bouquet{" +
                "flowers=" + flowers +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
}
