package com.serg.builder;

import com.serg.model.flower.Cornflower;
import com.serg.model.flower.Dandelion;
import com.serg.model.flower.Rose;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BouquetTest {
    private Bouquet bouquet;
    private Rose rose = mock(Rose.class);
    private Dandelion dandelion = mock(Dandelion.class);
    private Cornflower cornflower = mock(Cornflower.class);

    @Before
    public void init(){
        bouquet = new Bouquet.BouquetBuilder().addFlower(rose).addFlower(dandelion).addFlower(cornflower).build();
    }

    @Test
    public void getByLengthTest(){
        when(rose.getLength()).thenReturn(40);
        when(dandelion.getLength()).thenReturn(50);
        when(cornflower.getLength()).thenReturn(70);
        Assert.assertEquals(bouquet.getByLength(40,50).size(),2);
    }

    @Test
    public void getPriceTest(){
        when(rose.getPrice()).thenReturn(40.0);
        when(dandelion.getPrice()).thenReturn(300.0);
        when(cornflower.getPrice()).thenReturn(20.0);
        Assert.assertEquals(bouquet.getPrice(), 360.0);
    }

}
