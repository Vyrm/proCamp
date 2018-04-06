package com.garden.dao;

import com.garden.config.AppConfig;
import com.garden.dao.impl.JsonDaoImpl;
import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import com.garden.model.flower.Rose;
import com.garden.model.settings.Color;
import com.garden.model.settings.Fresh;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class JsonDaoImplTest {
    private Flower rose;
    private Bouquet bouquet;
    private String json;
    @Mock
    private BufferedReader bufferedReader;
    @Mock
    private Environment env;
    @InjectMocks
    private JsonDaoImpl jsonDao;

    @BeforeClass
    public static void preDestroy() {
        File file = new File("test.txt");
        file.delete();
    }

    @Before
    public void init() throws IOException {
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
        when(env.getProperty(any(String.class))).thenReturn("test.txt");
        jsonDao.exportToJson(bouquet);
        Assert.assertEquals(true, true);
    }

    @Test
    public void assertStringsTest() throws IOException {
        when(env.getProperty(any(String.class))).thenReturn("test.txt");
        jsonDao.exportToJson(bouquet);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("test.txt")));
        String actual = bufferedReader.readLine();
        Assert.assertEquals(json, actual);
    }

    @Test
    public void importTest() throws IOException {
        when(env.getProperty(any(String.class))).thenReturn("test.txt");
        when(bufferedReader.readLine()).thenReturn(json).thenReturn(null);
        Collection<Bouquet> actual = jsonDao.importFromJson();
        Collection<Bouquet> expectedList = new ArrayList<>();
        expectedList.add(bouquet);
        Assert.assertEquals(expectedList, actual);
    }

    @After
    public void destroy(){
        File file = new File("test.txt");
        file.delete();
    }
}
