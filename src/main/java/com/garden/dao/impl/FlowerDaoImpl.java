package com.garden.dao.impl;

import com.garden.dao.FlowerDao;
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

public class FlowerDaoImpl implements FlowerDao {
    private final Logger logger = LoggerFactory.getLogger("FlowerDao");
    private DataSource dataSource;
    private Properties properties;

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
    public long addFlower(Flower flower) {
        Long flowerId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("INSERT_FLOWER"),
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, flower.getName());
            preparedStatement.setInt(2, flower.getLength());
            preparedStatement.setString(3, flower.getFresh().toString());
            preparedStatement.setDouble(4, flower.getPrice());
            preparedStatement.setInt(5, flower.getPetals());
            preparedStatement.setBoolean(6, flower.isSpike());
            preparedStatement.setString(7, flower.getColor().toString());
            preparedStatement.setString(8, null);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        flowerId = generatedKeys.getLong(1);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Failed to insert flower");
        }
        flower.setId(flowerId);
        return flowerId;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
