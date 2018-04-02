package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BouquetService {
    private final Logger logger = LoggerFactory.getLogger("BouquetService");
    @Autowired
    private BouquetDaoImpl bouquetDao;

    public List<Flower> getByLength(Long bouquetId, int low, int high) {
        Bouquet bouquet = null;
        try {
            bouquet = bouquetDao.getBouquetById(bouquetId);
        } catch (SQLException e) {
            logger.error("Failed to get bouquet by id");
        }
        return bouquet.getFlowers().stream().filter(p -> (p.getLength() >= low && p.getLength() <= high)).collect(Collectors.toList());
    }

    public Bouquet sortBouquet(Bouquet bouquet) {
        bouquet.getFlowers().sort(Comparator.comparing(Flower::getFresh).reversed());
        return bouquet;
    }

    public boolean saveBouquetToFileFromDbById(Bouquet bouquet) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Bouquet.class);

            Configuration config = new Configuration();
            MappedNamespaceConvention con = new MappedNamespaceConvention(config);
            Writer writer = new OutputStreamWriter(System.out);
            XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(bouquet, xmlStreamWriter);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return true;
    }
}
