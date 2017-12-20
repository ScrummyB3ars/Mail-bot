package TestKleuterblog;

import JavaMail.Weather;
import JavaMail.WeatherCondition;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class WeatherUnitTest {
    private Weather weather;
    
    public WeatherUnitTest() {
        weather = new Weather();
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullArgumentWeatherConstructor() {
        System.out.println("testNullArgumentWeatherConstructor");
        
        Weather instance = new Weather (0, null);

        fail("testNullArgumentWeatherConstructor Failed"); 
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
