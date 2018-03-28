package com.serg.builder;

import com.serg.model.flower.Cornflower;
import com.serg.model.flower.Dandelion;
import com.serg.model.flower.Flower;
import com.serg.model.flower.Rose;
import com.serg.model.settings.Color;
import com.serg.model.settings.Fresh;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BouquetTest {
    private Flower rose;
    private Flower cornflower;
    private Flower dandelion;
    private Bouquet bouquet;

    @Before
    public void init() {
        rose = new Rose("rose", 60, Fresh.HIGH, 200.00, 20, true, Color.Red);
        cornflower = new Cornflower("cornflower", 70, Fresh.LOW, 200.00, 20, true, Color.Red);
        dandelion = new Dandelion("dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.Blue);
        rose.setId(1);
        cornflower.setId(2);
        dandelion.setId(3);
        bouquet = new Bouquet.BouquetBuilder().addFlower(rose).addFlower(cornflower).addFlower(dandelion).build();
    }

    @Test
    public void getByLengthTest() {
        Assert.assertEquals(bouquet.getByLength(70, 80).size(), 2);
    }

    @Test
    public void getPriceTest() {
        Assert.assertEquals(bouquet.getPrice(), 500.0, 0.0);
    }
}
