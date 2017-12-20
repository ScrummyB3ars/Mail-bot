/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import JavaMail.Tip;
import JavaMail.WeatherCondition;
import freemarker.core.Expression;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class TipTest {
    private Tip tip;
    
    public TipTest() {
        tip = new Tip();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void TestGetRandomInteraction_TipNotNull() throws Exception{
        System.out.println("RandomIntercationTip_NotNull");
        Tip instance = tip.getRandomInteraction_Tip();

        assertNotNull(instance);
    }
    
    @Test
    public void TestGetRandomTaal_Tip_Based_On_WeatherNotNull() throws Exception{
        System.out.println("RandomTaalTip_NotNull");
        Tip instance = tip.getRandomTaal_Tip_Based_On_Weather(WeatherCondition.SNEEUW);

        assertNotNull(instance);
    }
    
    @Test
    public void TestGetAPIRequestNotNull() throws Exception{
        System.out.println("ApiRequest_NotNull");
        JSONArray instance = tip.getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips"); 
        
        assertNotNull(instance);    
    }  
    
    @Test
    public void TestInteractionTipsArrayNotNull () throws Exception {
        System.out.println("AllTipsFromAPI_InteractionTipArrayNotNull");
        tip.getAllTipsFromAPI();
        
        assertNotNull(tip.getInteractionTipsArray());
    }
    
    @Test
    public void TestTaalTipsArrayNotNull () throws Exception {
        System.out.println("AllTipsFromAPI_TaalTipArrayNotNull");
        tip.getAllTipsFromAPI();
        
        assertNotNull(tip.getTaalTipsArray());
    }
    
    @Test
    public void TestGetRandomTaal_Tip_Based_On_WeatherObject () throws Exception{
        System.out.println("TestGetRandomTaal_Tip_Based_On_WeatherObjectTipContent");        
        
        assertThat((tip.getRandomTaal_Tip_Based_On_Weather(WeatherCondition.SNEEUW)), instanceOf(Tip.class));        
    }
    
    @Test
    public void TestgetRandomInteraction_TipObject () throws Exception{
        System.out.println("RandomInteractionTip_ObbjectInstanceOfString");        

        assertThat((tip.getRandomInteraction_Tip()), instanceOf(Tip.class));        
    }
    
    @Test
    public void TestGetAPIRequestObject() throws Exception{
        System.out.println("APIRequest_ObjectInstanceOfJSONArray");         
        
        assertThat(tip.getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips"), instanceOf(JSONArray.class));        
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
