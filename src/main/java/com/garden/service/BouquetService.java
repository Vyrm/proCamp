package com.garden.service;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.dao.impl.JsonDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BouquetService {
    private final Logger logger = LoggerFactory.getLogger("BouquetService");
    @Autowired
    private BouquetDaoImpl bouquetDao;
    @Autowired
    private JsonDaoImpl jsonDao;
    @Resource
    private Environment env;

    public List<Flower> getByLength(Long bouquetId, int low, int high) {
        Bouquet bouquet = null;
        try {
            bouquet = bouquetDao.getBouquetById(bouquetId);
        } catch (SQLException e) {
            logger.error("Failed to get bouquet by id");
        }
        return bouquet.getFlowers().stream()
                .filter(p -> (p.getLength() >= low && p.getLength() <= high))
                .collect(Collectors.toList());
    }

    public List<Flower> sortBouquet(Collection<Flower> flowers) {
        return flowers.stream()
                .sorted(Comparator.comparing(Flower::getFresh).reversed())
                .collect(Collectors.toList());
    }

    public boolean saveBouquetToFileFromDbById(Long id) {
        Bouquet bouquet = null;
        try {
            bouquet = bouquetDao.getBouquetById(id);
        } catch (SQLException e) {
            logger.error("Failed to get bouquet");
        }
        return jsonDao.exportJsonToFile(bouquet);
    }

    public Collection<Bouquet> loadBouquetFromFileToDb() {
        return jsonDao.importFromJson();
    }

    public String getJsonString(Long bouquetId) {
        Bouquet bouquet = null;
        String json = null;
        try {
            bouquet = bouquetDao.getBouquetById(bouquetId);
        } catch (SQLException e) {
            logger.error("Failed to get bouquet by id");
        }
        try {
            json = jsonDao.getJson(bouquet);
        } catch (Exception e) {
            logger.error("failed to get String");
        }
        return json;
    }
}
