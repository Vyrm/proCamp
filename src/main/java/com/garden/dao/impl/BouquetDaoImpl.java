package com.garden.dao.impl;

import com.garden.dao.BouquetDao;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import com.garden.utils.mapRower.BouquetRower;
import com.garden.utils.mapRower.FlowerRower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

@Component
public class BouquetDaoImpl implements BouquetDao {
    private final Logger logger = LoggerFactory.getLogger("BouquetDao");
    @Autowired
    private DataSource dataSource;
    @Autowired
    private FlowerDaoImpl flowerDao;
    @Resource
    private Environment environment;

    @Override
    public Long addBouquet(Bouquet bouquet) throws SQLException {
        Long bouquetId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psInsertBouquet = connection.prepareStatement(
                     environment.getRequiredProperty("INSERT_BOUQUET"), Statement.RETURN_GENERATED_KEYS)) {
            logger.debug("Connection and preparedStatement created");
            psInsertBouquet.setString(1, bouquet.getName());
            psInsertBouquet.setDouble(2, bouquet.getPrice());
            int result = psInsertBouquet.executeUpdate();
            logger.debug("Executed bouquet");
            if (result == 1) {
                try (ResultSet generatedKeys = psInsertBouquet.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bouquetId = generatedKeys.getLong(1);
                    }
                }
            }
            try (PreparedStatement psInsertFlower = connection.prepareStatement(
                    environment.getRequiredProperty("INSERT_FLOWER_TO_BOUQUET"))) {

                for (Flower flower : bouquet.getBouquet()) {
                    long id = flowerDao.addFlower(flower);
                    psInsertFlower.setLong(1, bouquetId);
                    psInsertFlower.setLong(2, id);
                    psInsertFlower.executeUpdate();
                }
            }
        }
        bouquet.setId(bouquetId);
        logger.debug("Set Id");
        return bouquetId;
    }

    @Override
    public Bouquet getBouquetById(long id) throws SQLException {
        Bouquet bouquet = new Bouquet();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psSelectBouquet = connection.prepareStatement(
                     environment.getRequiredProperty("SELECT_BOUQUET_BY_ID"))) {
            logger.debug("Connection and preparedStatement created");
            psSelectBouquet.setLong(1, id);
            try (ResultSet resultSet = psSelectBouquet.executeQuery()) {
                if (resultSet.next()) {
                    BouquetRower bouquetRower = new BouquetRower();
                    bouquet = bouquetRower.mapRow(resultSet,bouquet);
                }
                logger.debug("Executed bouquet");
            }
            try (PreparedStatement psGetFlowers = connection.prepareStatement(
                    environment.getRequiredProperty("SELECT_FLOWER_BY_BOUQUET_ID"))) {
                psGetFlowers.setLong(1, id);
                try (ResultSet resultSet = psGetFlowers.executeQuery()) {
                    while (resultSet.next()) {
                        FlowerRower flowerRower = new FlowerRower();
                        Flower flower = flowerRower.mapRow(resultSet);
                        bouquet.getBouquet().add(flower);
                    }
                    logger.debug("Executed flowers");
                }
            }
        }
        return bouquet;
    }
}
