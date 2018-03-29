package com.garden.dao;

import com.garden.dao.impl.BouquetDaoImpl;
import com.garden.dao.impl.FlowerDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BouquetDaoImplTest {
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private DataSource dataSource;
    @Mock
    private Properties properties;
    @Mock
    private FlowerDaoImpl flowerDao;
    @InjectMocks
    private BouquetDaoImpl bouquetDao;
    private Flower rose;
    private Bouquet bouquet;

    @Before
    public void init() throws SQLException {
        rose = new Rose("rose", 60, Fresh.HIGH, 200.00, 20, true, Color.RED);
        rose.setId(1);
        bouquet = new Bouquet("Test bouquet");
        bouquet.addFlower(rose);

        when(dataSource.getConnection()).thenReturn(connection);
        when(properties.getProperty(any(String.class))).thenReturn("Test SQL String");
        when(connection.prepareStatement(any(String.class), eq(1))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void addBouquetTest() {
        Assert.assertEquals(1, bouquetDao.addBouquet(bouquet));
    }

    @Test
    public void getBouquetByIdTest() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        Assert.assertEquals(null, bouquetDao.getBouquetById(95L));
    }
}
