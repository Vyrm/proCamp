package com.serg.dao;

import com.serg.builder.Bouquet;
import com.serg.exceptions.BouquetException;
import com.serg.model.flower.Flower;
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
    private final Logger logger = LoggerFactory.getLogger("Dao");
    private DataSource dataSource;
    private Properties properties;
    private Connection connection;

    public void init() {
        properties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("properties.properties").getFile());
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties");
        }
    }

    @Override
    public long addFlower(Flower flower) {
        long flowerId = 0;
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_FLOWER"),
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, flower.getName());
            preparedStatement.setInt(2, flower.getLength());
            preparedStatement.setInt(3, flower.getFresh().ordinal());
            preparedStatement.setDouble(4, flower.getPrice());
            preparedStatement.setInt(5, flower.getPetals());
            preparedStatement.setBoolean(6, flower.isSpike());
            preparedStatement.setString(7, flower.getColor().toString());
            preparedStatement.setString(8, null);
            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            flowerId = resultSet.getLong(1);
            flower.setId(flowerId);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to add flower");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Failed to close ResultSet");
                }
            }
        }
        return flowerId;
    }

    @Override
    public long addBouquet(Bouquet bouquet) {
        long bouquetId = 0;
        ResultSet resultSet = null;
        if (!(bouquet.isEmpty())) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_BOUQUET"))) {
                preparedStatement.setString(1, bouquet.getName());
                preparedStatement.setDouble(2, bouquet.getPrice());
                preparedStatement.execute();
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                bouquetId = resultSet.getLong(1);

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        logger.error("Failed to close ResultSet");
                    }
                }
            }
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_FLOWER_TO_BOUQUET"))) {
                for (Flower flower : bouquet.getBouquet()) {
                    preparedStatement.setLong(1, bouquetId);
                    preparedStatement.setLong(2, flower.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                logger.error("Failed to add bouquet");
            }
        } else {
            try {
                throw new BouquetException("Bouquet is empty");
            } catch (BouquetException e) {
                logger.error("failed to add bouquet to DB");
            }
        }
        return bouquetId;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
