package com.garden.run;

import com.garden.config.AppConfig;
import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.dao.impl.FlowerDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Cornflower;
import com.garden.model.flower.Dandelion;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import com.garden.service.BouquetService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import java.io.StringWriter;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, JAXBException, JSONException, XMLStreamException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BouquetDaoImpl bouquetDao = context.getBean(BouquetDaoImpl.class);
        FlowerDaoImpl flowerDao = context.getBean(FlowerDaoImpl.class);
        BouquetService bouquetService = context.getBean(BouquetService.class);


        Flower rose = new Rose("Rose", 70, Fresh.HIGH, 200.00, 20, true, Color.RED);
        Flower cornflower = new Cornflower("Cornflower", 80, Fresh.LOW, 200.00, 20, true, Color.RED);
        Flower dandelion = new Dandelion("Dandelion", 90, Fresh.HIGH, 100.00, 10, false, Color.BLUE);

        Bouquet bouquet = new Bouquet("Bouquet 1");
        bouquet.addFlower(rose);
        bouquet.addFlower(cornflower);
        bouquet.addFlower(dandelion);
        bouquetDao.addBouquet(bouquet);

/*        System.err.println(bouquet.getPrice());
        System.out.println(bouquet);
        List list = bouquetService.getByLength(95L, 80, 90);
        System.err.println("Get by length:" + list);

        bouquet.sortByFresh();
        System.err.println(bouquet);
        Bouquet bouquet1 = bouquetDao.getBouquetById(126L);
        System.err.println(bouquet1);
        bouquetService.sortBouquet(bouquet1);
        System.out.println("Sorted " + bouquet1);*/

        bouquetService.saveBouquetToFileFromDbById(bouquet);
    }
}
