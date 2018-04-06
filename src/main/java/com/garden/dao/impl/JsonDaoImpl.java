package com.garden.dao.impl;

import com.garden.dao.JsonDao;
import com.garden.model.bouquet.Bouquet;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JsonDaoImpl implements JsonDao {
    private final Logger logger = LoggerFactory.getLogger("JsonDaoImpl");
    @Resource
    private Environment env;


    @Override
    public boolean exportToJson(Bouquet bouquet) {
        boolean result;
        XMLStreamWriter xmlStreamWriter;
        Configuration config = new Configuration();
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);
        try (Writer writer = new FileWriter(env.getProperty("FILE_DIRECTORY"), true)) {
            JAXBContext jc = JAXBContext.newInstance(Bouquet.class);
            xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(bouquet, xmlStreamWriter);
            result = true;
            logger.info("Success export object to file");
        } catch (IOException | JAXBException e) {
            result = false;
            logger.error("Failed to marshal to Json");
        }
        return result;
    }

    @Override
    public Collection<Bouquet> importFromJson() {
        Collection<Bouquet> collection = new ArrayList<>();
        Bouquet bouquet;
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
                collection.add(bouquet);
                logger.info(bouquet.toString());
            }
            logger.info("Success import from File");
        } catch (JSONException | XMLStreamException | JAXBException | IOException e) {
            logger.error("Failed to load bouquet");
        }
        return collection;
    }
}
