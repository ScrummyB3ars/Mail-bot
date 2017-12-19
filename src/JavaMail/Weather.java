/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.JSONObject;


/**
 *
 * @author MilanT
 */
public class Weather {
    
    private int zipCode;
    private int temperature;
    private JSONParser parser;
    private WeatherCondition weatherCondition;
    private String URLWeatherCondition = "http://api.openweathermap.org/data/2.5/weather?zip=9000,be&appid=c8f63cf0ac4245b55258a4609e1c532e";
    
    public Weather(){
        parser = new JSONParser();
    }
    
    public Weather(int zipCode, WeatherCondition weatherCondition){
        this.zipCode = zipCode;
        this.weatherCondition = weatherCondition;
    }
    
    public Weather(int zipCode, int temperature, WeatherCondition weatherCondition){
        this.zipCode = zipCode;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
    }
    
    /**
     * This method will determine the weathercondition based on the ZIP code
     * @param zipCode Zip code of the 
     * @return Enumeration of the weather condition
     */
    public WeatherCondition getCondition(int zipCode){
        try{            
            JSONArray weatherInfo = (JSONArray) getAPIRequest(URLWeatherCondition);
            parser.parse(weatherInfo.toString());
            JSONObject jsonObjectAllInfo = (JSONObject) parser.parse(weatherInfo.get(0).toString());
            JSONArray jsonArrayWeatherInfo = (JSONArray) jsonObjectAllInfo.get("weather");
            JSONObject weatherCircumstances = (JSONObject) parser.parse(jsonArrayWeatherInfo.get(0).toString());
            int idWeather = Integer.parseInt(weatherCircumstances.get("id").toString());

            if(idWeather >= 200 && idWeather < 300){
                return WeatherCondition.REGEN;
            }else if(idWeather >= 300 && idWeather < 400){
                return WeatherCondition.WOLKEN;
            }else if(idWeather >= 500 && idWeather < 600){
                return WeatherCondition.REGEN;
            }else if(idWeather >= 600 && idWeather < 625){
                return WeatherCondition.SNEEUW;
            }else if(idWeather >= 700 && idWeather < 800){
                return WeatherCondition.WOLKEN;
            }else if(idWeather == 800){
                return WeatherCondition.ZONNIG;
            }else{
                return WeatherCondition.ZONNIG;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    private static JSONArray getAPIRequest(String urlToRead) throws Exception {
      JSONArray jsonData = new JSONArray();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         jsonData.add(line);
      }
      rd.close();
      return jsonData;
   }
}
