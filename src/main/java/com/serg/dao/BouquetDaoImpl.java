package com.serg.dao;

import com.serg.builder.Bouquet;
import com.serg.jdbc.DataSource;
import com.serg.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Component
public class BouquetDaoImpl implements BouquetDao {
    private final Logger logger = LoggerFactory.getLogger("Dao");
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("test")
    private Properties properties;
    private Connection connection;

    @Override
    public long addFlower(Flower flower) {
        connection = dataSource.getConnection();
        long flowerId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_FLOWER"),
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
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            flowerId = resultSet.getLong(1);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flowerId;
    }

    @Override
    public void addBouquet(Bouquet bouquet) {
        connection = dataSource.getConnection();
        long bouquetId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_BOUQUET"))) {
            preparedStatement.setString(1, bouquet.getName());
            preparedStatement.setDouble(2, bouquet.getPrice());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            bouquetId = resultSet.getLong(1);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_FLOWER_TO_BOUQUET"))) {
            for (Flower flower : bouquet.getBouquet()) {
                preparedStatement.setLong(1, bouquetId);
                preparedStatement.setLong(2, flower.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
