package com.garden.service;

import com.garden.config.AppConfig;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Cornflower;
import com.garden.model.flower.Dandelion;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader=AnnotationConfigContextLoader.class)
public class BouquetServiceTest {
    private BouquetService bouquetService;
    private Bouquet bouquet;
    private Flower rose;
    private Flower cornflower;
    private Flower dandelion;

    @Before
    public void init() {
        //
        // Given
        //
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        bouquetService = (BouquetService) context.getBean("bouquetService");

        rose = new Rose("rose", 60, Fresh.HIGH, 200.00, 20, true, Color.RED);
        cornflower = new Cornflower("cornflower", 70, Fresh.LOW, 200.00, 20, true, Color.RED);
        dandelion = new Dandelion("dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.BLUE);
        bouquet = new Bouquet("Test Bouquet");

        rose.setId(1L);
        cornflower.setId(2L);
        dandelion.setId(3L);

        bouquet.setId(95L);
        bouquet.addFlower(rose);
        bouquet.addFlower(cornflower);
        bouquet.addFlower(dandelion);
    }

    @Test
    public void getByLengthTest() {
        //
        // When
        //
        int size = bouquetService.getByLength(95L, 70, 80).size();

        //
        // Then
        //
        Assert.assertEquals(size , 2);
    }

    @Test
    public void getPriceTest() {
        //
        // When
        //
        double price = bouquet.getPrice();

        //
        // Then
        //
        Assert.assertEquals(price, 500.0, 0.0);
    }
}
