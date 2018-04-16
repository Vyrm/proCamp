package com.garden.dao;

import com.garden.config.AppConfig;
import com.garden.dao.impl.JsonDaoImpl;
import com.garden.model.bouquet.Bouquet;
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
import java.util.*;

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
        Assert.assertEquals(jsonDao.exportJsonToFile(bouquet), true);
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
        jsonDao.exportJsonToFile(bouquet);
        actual = readJson();

        //
        // Then
        //
        Assert.assertEquals(json, actual);
    }

    @Test
    public void importTest() throws IOException {
        Collection<Bouquet> actual;

        //
        // Given
        //
        when(env.getProperty(any(String.class))).thenReturn(file.getPath());
        when(bufferedReader.readLine()).thenReturn(json).thenReturn(null);

        //
        // When
        //
        jsonDao.exportJsonToFile(bouquet);
        actual = jsonDao.importFromJson();
        ArrayList<Bouquet> bouquets = new ArrayList<>(actual);

        //
        // Then
        //
        Assert.assertEquals(bouquet, bouquets.get(0));
    }

    @After
    public void destroy() {
        file.delete();
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
