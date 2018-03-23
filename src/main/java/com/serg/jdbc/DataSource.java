package com.serg.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataSource {
    private final Logger logger = LoggerFactory.getLogger("DataSource");
    private Properties properties;
    private InputStream inputStream;
    private String URL;
    private String username;
    private String password;
    private Connection connection;


    public Connection getConnection() {
        File file = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            try {
                file = new File(classLoader.getResource("properties.properties").getFile());
            } catch (NullPointerException e) {
                logger.error("File not found");
            }
            properties = new Properties();
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("No properties detected");
        }
        URL = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}