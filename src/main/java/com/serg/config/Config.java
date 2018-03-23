package com.serg.config;

import com.serg.dao.BouquetDao;
import com.serg.dao.BouquetDaoImpl;
import com.serg.jdbc.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.serg")
public class Config {

    @Bean(name = "test")
    public Properties properties() throws IOException {
        Properties properties = new Properties();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("properties.properties").getFile());
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        return properties;
    }

    @Bean
    public DataSource dataSource(){
        return new DataSource();
    }

}
