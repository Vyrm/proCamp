package com.garden.dao;

import com.garden.config.AppConfig;
import com.garden.dao.impl.JsonDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.*;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class JsonDaoImplTest {
    private final Logger logger = LoggerFactory.getLogger("JsonDaoImplTest");
    private Rose rose;
    private Bouquet bouquet;
    private String json;
    @Mock
    private BufferedReader bufferedReader;
    @Mock
    private Environment env;
    @InjectMocks
    private JsonDaoImpl jsonDao;
    private File file;

    @Before
    public void init() {
        //
        // Given
        //
        file = new File("test.txt");
        json = "{\"bouquet\":{\"id\":100,\"flowers\":{\"id\":200,\"name\":\"Rose\",\"length\":70,\"fresh\":\"high\"," +
                "\"price\":300,\"petals\":20,\"spike\":true,\"color\":\"red\"},\"name\":\"TestBouquet\",\"price\":300}}";
        rose = new Rose("Rose", 70, Fresh.HIGH, 300.00, 20, true, Color.RED);
        rose.setId(200L);
        bouquet = new Bouquet();
        bouquet.setName("TestBouquet");
        bouquet.setId(100L);
        bouquet.addFlower(rose);
    }

    @Test
    public void exportTest() {
        //
        // Given
        //
        when(env.getProperty(any(String.class))).thenReturn(file.getPath());

        //
        // Then
        //
        Assert.assertEquals(jsonDao.exportToJson(bouquet), true);
    }

    @Test
    public void assertStringsTest() {
        String actual;

        //
        // Given
        //
        when(env.getProperty(any(String.class))).thenReturn(file.getPath());

        //
        // When
        //
        jsonDao.exportToJson(bouquet);
        actual = readJson();

        //
        // Then
        //
        Assert.assertEquals(json, actual);
    }

    @Test
    public void importTest() throws IOException {
        List<Flower> flowers = null;
        String name = null;
        double price = 0;
        Long id = 0L;
        Collection<Bouquet> actual;
        Rose expectedRose;

        //
        // Given
        //
        when(env.getProperty(any(String.class))).thenReturn(file.getPath());
        when(bufferedReader.readLine()).thenReturn(json).thenReturn(null);

        //
        // When
        //
        jsonDao.exportToJson(bouquet);
        actual = jsonDao.importFromJson();

        for (Bouquet bouquet1 : actual) {
            id = bouquet1.getId();
            flowers = bouquet1.getFlowers();
            name = bouquet1.getName();
            price = bouquet1.getPrice();
        }
        expectedRose = new Rose(flowers.get(0));
        expectedRose.setId(200L);

        //
        // Then
        //
        Assert.assertEquals(expectedRose, rose);
        Assert.assertEquals(name, bouquet.getName());
        Assert.assertEquals(price, bouquet.getPrice(), 0.0);
        Assert.assertEquals(id, bouquet.getId());
    }

    @After
    public void destroy() {
        System.out.println(file.delete());
    }

    private String readJson() {
        String json = null;
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)))){
            json = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("Failed to read Json");
        }
        return json;
    }
}
