package com.garden.dto;

import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Cornflower;
import com.garden.model.flower.Dandelion;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import com.garden.service.BouquetService;
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
        rose = new Rose("rose", 60, Fresh.HIGH, 200.00, 20, true, Color.RED);
        cornflower = new Cornflower("cornflower", 70, Fresh.LOW, 200.00, 20, true, Color.RED);
        dandelion = new Dandelion("dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.BLUE);
        rose.setId(1);
        cornflower.setId(2);
        dandelion.setId(3);
        bouquet = new Bouquet("Test Bouquet");
        bouquet.addFlower(rose);
        bouquet.addFlower(cornflower);
        bouquet.addFlower(dandelion);
    }

    @Test
    public void getByLengthTest() {
        Assert.assertEquals(new BouquetService().getByLength(95L, 70, 80).size(), 2);
    }

    @Test
    public void getPriceTest() {
        Assert.assertEquals(bouquet.getPrice(), 500.0, 0.0);
    }
}
