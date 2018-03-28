package com.serg.run;

import com.serg.builder.Bouquet;
import com.serg.dao.BouquetDaoImpl;
import com.serg.exceptions.BouquetException;
import com.serg.model.flower.Dandelion;
import com.serg.model.flower.Flower;
import com.serg.model.flower.Rose;
import com.serg.model.settings.Color;
import com.serg.model.settings.Fresh;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        BouquetDaoImpl bouquetDao = (BouquetDaoImpl) context.getBean("dao");


        Flower rose = new Rose("RoseTest22", 70, Fresh.HIGH, 200.00, 20, true, Color.Red);
        Flower rose2 = new Rose("RoseTest22", 70, Fresh.LOW, 200.00, 20, true, Color.Red);
        Flower dandelion = new Dandelion("Dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.Blue);
/*        bouquetDao.addFlower(rose);
        bouquetDao.addFlower(dandelion);*/

        rose.setId(1);
        rose2.setId(2);

        Bouquet bouquet = new Bouquet.BouquetBuilder()
                    .addFlower(dandelion)
                    .addFlower(rose)
                    .addFlower(rose2)
                    .setName("Rose and dandelion")
                    .build();


        bouquetDao.addBouquet(bouquet);

        System.err.println(bouquet.toString());
        System.err.println(bouquet.getPrice());
        System.err.println(bouquet.getByLength(5, 6));

        bouquet.sortByFresh();
        System.err.println(bouquet);
    }
}
