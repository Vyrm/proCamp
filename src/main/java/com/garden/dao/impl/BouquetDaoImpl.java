package com.garden.dao.impl;

import com.garden.dao.BouquetDao;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BouquetDaoImpl implements BouquetDao {
    private final Logger logger = LoggerFactory.getLogger("BouquetDao");
    private DataSource dataSource;
    private Properties properties;
    private FlowerDaoImpl flowerDao;

    public void init() {
        properties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("properties.properties").getFile());
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties");
        }
    }


    @Override
    public long addBouquet(Bouquet bouquet) {
        Long bouquetId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psInsertBouquet = connection.prepareStatement(
                     properties.getProperty("INSERT_BOUQUET"), Statement.RETURN_GENERATED_KEYS)) {
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
                    properties.getProperty("INSERT_FLOWER_TO_BOUQUET"))) {

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement psSelectBouquet = connection.prepareStatement(
                     properties.getProperty("SELECT_BOUQUET_BY_ID"))) {
            psSelectBouquet.setLong(1, id);
            try (ResultSet resultSet = psSelectBouquet.executeQuery()) {
                if (resultSet.next()) {
                    bouquet = new Bouquet();
                    bouquet.setId(resultSet.getLong("id"));
                    bouquet.setName(resultSet.getString("name"));
                    bouquet.setPrice(resultSet.getDouble("assemble_price"));
                }
            }
            try (PreparedStatement psGetFlowers = connection.prepareStatement(
                    properties.getProperty("SELECT_FLOWER_BY_BOUQUET_ID"))) {
                psGetFlowers.setLong(1, id);
                try (ResultSet resultSet = psGetFlowers.executeQuery()) {
                    while (resultSet.next()) {
                        Flower flower = new Flower();
                        flower.setId(resultSet.getLong("id"));
                        flower.setName(resultSet.getString("name"));
                        flower.setLength(resultSet.getInt("length"));
                        flower.setFresh(resultSet.getString("freshness"));
                        flower.setPrice(resultSet.getDouble("price"));
                        flower.setPetals(resultSet.getInt("petals"));
                        flower.setSpike(resultSet.getBoolean("spike"));
                        flower.setColor(resultSet.getString("color"));
                        bouquet.getBouquet().add(flower);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Failed to get bouquet");
        }
        return bouquet;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setFlowerDao(FlowerDaoImpl flowerDao) {
        this.flowerDao = flowerDao;
    }
}
