package com.garden.dao.impl;

import com.garden.dao.BouquetDao;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public long addBouquet(Bouquet bouquet) {
        Long bouquetId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psInsertBouquet = connection.prepareStatement(
                     environment.getRequiredProperty("INSERT_BOUQUET"), Statement.RETURN_GENERATED_KEYS)) {
            psInsertBouquet.setString(1, bouquet.getName());
            psInsertBouquet.setDouble(2, bouquet.getPrice());
            int result = psInsertBouquet.executeUpdate();
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
            } catch (SQLException e) {
                logger.error("Failed to insert flower into bouquet");
            }
        } catch (SQLException e) {
            logger.error("Failed to insert bouquet");
        }
        bouquet.setId(bouquetId);
        return bouquetId;
    }

    @Override
    public Bouquet getBouquetById(long id) {
        Bouquet bouquet = null;
        List<Flower> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psSelectBouquet = connection.prepareStatement(
                     environment.getRequiredProperty("SELECT_BOUQUET_BY_ID"))) {
            psSelectBouquet.setLong(1, id);
            try (ResultSet resultSet = psSelectBouquet.executeQuery()) {
                if (resultSet.next()) {
                    bouquet = new Bouquet();
                    bouquet = bouquet.mapRow(resultSet);
                }
            }
            try (PreparedStatement psGetFlowers = connection.prepareStatement(
                    environment.getRequiredProperty("SELECT_FLOWER_BY_BOUQUET_ID"))) {
                psGetFlowers.setLong(1, id);
                try (ResultSet resultSet = psGetFlowers.executeQuery()) {
                    while (resultSet.next()) {
                        Flower flower = new Flower();
                        flower.mapRow(resultSet);
                        bouquet.getBouquet().add(flower);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Failed to get bouquet");
        }
        return bouquet;
    }
}
