package com.serg.run;

import com.serg.builder.Bouquet;
import com.serg.config.Config;
import com.serg.dao.BouquetDao;
import com.serg.dao.BouquetDaoImpl;
import com.serg.model.flower.Dandelion;
import com.serg.model.flower.Flower;
import com.serg.model.flower.Rose;
import com.serg.model.settings.Color;
import com.serg.model.settings.Fresh;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        Properties p = (Properties) ctx.getBean("test");
        BouquetDao dao = ctx.getBean(BouquetDaoImpl.class);
        dao.addBouquet(null);
        Flower rose = new Rose("RoseTest", 70, Fresh.HIGH, 200.00, 20, true, Color.Red);
        Flower dandelion = new Dandelion("Dandelion", 80, Fresh.LOW, 100.00, 10, false, Color.Blue);


        Bouquet bouquet = new Bouquet.BouquetBuilder()
                .addFlower(dandelion)
                .addFlower(rose)
                .addFlower(dandelion)
                .setPaper(5)
                .setRibbon(8)
                .setName("Rose and dandelion")
                .build();


        System.out.println(bouquet.toString());
        System.out.println(bouquet.getPrice());
        Collections.sort(bouquet.getBouquet());
        System.out.println(bouquet);
        System.out.println(bouquet.getByLength(50, 60));

    }
}
