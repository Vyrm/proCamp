package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BouquetService {
    private final Logger logger = LoggerFactory.getLogger("BouquetService");
    @Autowired
    private BouquetDaoImpl bouquetDao;
    @Resource
    private Environment env;

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

    public Bouquet saveBouquetToFileFromDbById(Long id) {
        Bouquet bouquet = null;
        XMLStreamWriter xmlStreamWriter;
        Configuration config = new Configuration();
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);
        try (Writer writer = new FileWriter(env.getProperty("FILE_DIRECTORY"), true)) {
            try {
                bouquet = bouquetDao.getBouquetById(id);
            } catch (SQLException e) {
                logger.error("Failed to get bouquet by id");
            }
            JAXBContext jc = JAXBContext.newInstance(Bouquet.class);
            xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(bouquet, xmlStreamWriter);
        } catch (JAXBException e) {
            logger.error("Failed to save bouquet to File");
        } catch (IOException e) {
            logger.error("Failed to create or access to file");
        }
        return bouquet;
    }

    public Bouquet loadBouquetFromFileToDb() {
        Bouquet bouquet = null;
        JAXBContext jc;
        String json;
        JSONObject jsonObject;
        Configuration config = new Configuration();
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(env.getProperty("FILE_DIRECTORY"))))) {
            jc = JAXBContext.newInstance(Bouquet.class);

            while ((json = bufferedReader.readLine()) != null) {
                System.out.println(json);
                jsonObject = new JSONObject(json);
                XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jsonObject, con);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                bouquet = (Bouquet) unmarshaller.unmarshal(xmlStreamReader);
                logger.info(bouquet.toString());
            }
        } catch (JSONException | XMLStreamException | JAXBException | IOException e) {
            logger.error("Failed to load bouquet");
        }
        return bouquet;
    }
}
