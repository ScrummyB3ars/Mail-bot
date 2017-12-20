/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import JavaMail.Tip;
import JavaMail.Weather;
import JavaMail.WeatherCondition;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class WeatherTest {
    private Weather weather;
    
    public WeatherTest() {
        weather = new Weather();
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
    
    @Test(expected = NullPointerException.class)
    public void testNullArgumentWeatherConstructor() {
        System.out.println("testNullArgumentWeatherConstructor");
        
        Weather instance = new Weather (0, null);

        fail("testNullArgumentWeatherConstructor Failed"); 
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullArgumentWeatherSecondConstructor() {
        System.out.println("testNullArgumentWeatherSecondConstructor");
        
        Weather instance = new Weather (0, 0, null);

        fail("testNullArgumentWeatherSecondConstructor Failed"); 
    }
    
    @Test
    public void TestgetConditionNotNull() throws Exception{
        System.out.println("TestgetConditionNotNull");        
        WeatherCondition instance = weather.getCondition(9000);

        assertNotNull(instance);
    }
    
    @Test
    public void TestGetConditionObject (){
        System.out.println("TestGetConditionObject");
        WeatherCondition instance = weather.getCondition(9000);
        
        assertThat(instance, instanceOf(WeatherCondition.class));        
    }
}
