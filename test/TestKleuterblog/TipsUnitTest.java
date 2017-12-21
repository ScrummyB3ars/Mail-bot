/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestKleuterblog;

import JavaMail.Tip;
import JavaMail.WeatherCondition;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;


/**
 *
 * @author MilanT
 */
public class TipsUnitTest {
    private Tip testTip;
    private static final Long idTip = Long.valueOf(12345);
    private static final String tipContent = "tester@gmail.cam";
    private static final String image = "tester46";
    
    public TipsUnitTest() {
        testTip = new Tip();
    }   
    
    @Test
    public void testSubscriberOject() {
        Tip instance = new Tip(idTip, tipContent, image);
        assertEquals(instance.image, image);
        assertEquals(instance.tipContent, tipContent);
        assertEquals(instance.idTip, idTip);
    }
    
    @Test
    public void testSubscriberOjectSndConstructor() {
        Tip instance = new Tip(idTip, tipContent);
        assertEquals(instance.tipContent, tipContent);
        assertEquals(instance.idTip, idTip);
    }
    
    @Test
    public void TestGetRandomInteraction_TipNotNull() throws Exception{
        System.out.println("RandomIntercationTip_NotNull");
        Tip instance = testTip.getRandomInteraction_Tip();

        assertNotNull(instance);
    }
    
    @Test
    public void TestGetRandomTaal_Tip_Based_On_WeatherNotNull() throws Exception{
        System.out.println("RandomTaalTip_NotNull");
        Tip instance = testTip.getRandomTaal_Tip_Based_On_Weather(WeatherCondition.SNEEUW);

        assertNotNull(instance);
    }
    
    @Test
    public void TestGetRandomTaal_Tip_Based_On_WeatherObject () throws Exception{
        System.out.println("TestGetRandomTaal_Tip_Based_On_WeatherObjectTipContent");        
        
        assertThat((testTip.getRandomTaal_Tip_Based_On_Weather(WeatherCondition.SNEEUW)), instanceOf(Tip.class));        
    }
    
    @Test
    public void TestgetRandomInteraction_TipObject () throws Exception{
        System.out.println("RandomInteractionTip_ObbjectInstanceOfString");        

        assertThat((testTip.getRandomInteraction_Tip()), instanceOf(Tip.class));        
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullArgumentTipConstructor() {
        System.out.println("testNullArgumentTip");
        
        Tip instance = new Tip (null, null, null);

        fail("testNullTipContent Failed"); 
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullArgumentTipSecondConstructor() {
        System.out.println("testNullArgumentTipSecondConstructor");
        
        Tip instance = new Tip (null, null);

        fail("testNullTipContent Failed"); 
    }
}
