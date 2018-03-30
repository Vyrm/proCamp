package com.garden.dao.impl;

import com.garden.dao.FlowerDao;
import com.garden.model.flower.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

@Component
public class FlowerDaoImpl implements FlowerDao {
    private final Logger logger = LoggerFactory.getLogger("FlowerDao");
    @Autowired
    private DataSource dataSource;
    @Resource
    private Environment environment;

    @Override
    public long addFlower(Flower flower) throws SQLException {
        Long flowerId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(environment.getRequiredProperty("INSERT_FLOWER"),
                     Statement.RETURN_GENERATED_KEYS)) {
            logger.debug("Connection and preparedStatement created");
            preparedStatement.setString(1, flower.getName());
            preparedStatement.setInt(2, flower.getLength());
            preparedStatement.setString(3, flower.getFresh().toString());
            preparedStatement.setDouble(4, flower.getPrice());
            preparedStatement.setInt(5, flower.getPetals());
            preparedStatement.setBoolean(6, flower.isSpike());
            preparedStatement.setString(7, flower.getColor().toString());
            preparedStatement.setString(8, null);
            int result = preparedStatement.executeUpdate();
            logger.debug("Executed flower");
            if (result == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        flowerId = generatedKeys.getLong(1);
                    }
                }
            }
        }
        flower.setId(flowerId);
        logger.debug("Set Id");
        return flowerId;
    }
}
