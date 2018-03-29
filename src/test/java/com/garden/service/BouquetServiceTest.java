package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BouquetServiceTest {
    private BouquetService bouquetService;
    private BouquetDaoImpl bouquetDao;
    private Bouquet bouquet;
    private Flower rose;
    private Flower cornflower;
    private Flower dandelion;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        bouquetService = (BouquetService) context.getBean("bouquetService");
        bouquetDao = (BouquetDaoImpl) context.getBean("bouquetDao");


        rose = new Rose("rose", 60, Fresh.HIGH, 200.00, 20, true, Color.RED);
        cornflower = new Cornflower("cornflower", 70, Fresh.LOW, 200.00, 20, true, Color.RED);
        dandelion = new Dandelion("dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.BLUE);
        rose.setId(1L);
        cornflower.setId(2);
        dandelion.setId(3);

        bouquet = new Bouquet("Test Bouquet");
        bouquet.setId(95L);
        bouquet.addFlower(rose);
        bouquet.addFlower(cornflower);
        bouquet.addFlower(dandelion);
        //when(bouquetDao.getBouquetById(any(Long.class))).thenReturn(bouquet);
    }

    @Test
    public void getByLengthTest() {

        int size = bouquetService.getByLength(95L, 70, 80).size();
        Assert.assertEquals(size , 2);
    }

    @Test
    public void getPriceTest() {
        Assert.assertEquals(bouquet.getPrice(), 500.0, 0.0);
    }
}
