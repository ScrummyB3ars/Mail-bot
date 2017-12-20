/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.util.Random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author MilanT
 */
public class Tip {
    
    public Long idTip;
    public String tipContent;
    public String image;
    
    private static String URLTaalTips = "https://api-toddlr.herokuapp.com/theme_tips";
    private static String URLInteractieTips = "https://api-toddlr.herokuapp.com/interaction_tips";
    
    private static JSONArray interaction_tip_random;
    private static JSONArray taal_tip_random;
    private static JSONParser parser;
    
    
    public Tip(){
        parser = new JSONParser();
    }
    
    public Tip(Long idTip, String tipContent)throws NullPointerException{
        if (tipContent == null || idTip == null) throw new NullPointerException("Variable is null");
        
        this.idTip = idTip;
        this.tipContent = tipContent;
    }
    
    public Tip(Long idTip, String tipContent, String image)throws NullPointerException {        
        if (tipContent == null || idTip == null || image == null) throw new NullPointerException("Variable is null");
        
        this.idTip = idTip;
        this.tipContent = tipContent;
        this.image = image;        
    }
    
    public static JSONArray getInteractionTipsArray() {
        return interaction_tip_random;
    }
    
    public static JSONArray getTaalTipsArray() {
        return taal_tip_random;
    }    
    
    /**
     * This method will generate a random taal_tip based on the weather
     * @param weatherCondition Weathercondition from the subscriber
     * @return random taal tip
     */
    public static Tip getRandomTaal_Tip_Based_On_Weather(WeatherCondition weatherCondition) {
        try {
            getAllTipsFromAPI();
            parser = new JSONParser();
            parser.parse(taal_tip_random.toString());
            JSONArray allTips = (JSONArray) parser.parse(taal_tip_random.get(0).toString());
            ArrayList conditionTips = new ArrayList();
            for (int i = 0; i < allTips.size(); i++) {
                JSONObject jsonObject = (JSONObject) parser.parse(allTips.get(i).toString());
                if(null != weatherCondition)switch (weatherCondition) {
                    case REGEN:
                        if(((String) jsonObject.get("circumstances").toString()).contains("regen")){
                            conditionTips.add(jsonObject);
                        }   break;
                    case WOLKEN:
                        if(((String) jsonObject.get("circumstances").toString()).contains("wolk") || ((String) jsonObject.get("circumstances").toString()).contains("wind")){
                            conditionTips.add(jsonObject);
                        }   break;
                    case SNEEUW:
                        if(((String) jsonObject.get("circumstances").toString()).contains("winter") || ((String) jsonObject.get("circumstances").toString()).contains("sneeuw")){
                            conditionTips.add(jsonObject);
                        }   break;
                    case ZONNIG:
                        if(((String) jsonObject.get("circumstances").toString()).contains("zon")){
                            conditionTips.add(jsonObject);
                        }   break;
                    default:
                        if(((String) jsonObject.get("circumstances").toString()).contains("zon")){
                            conditionTips.add(jsonObject);
                        }   break;
                }                
            }
            Random random = new Random();
            
            int r = random.nextInt((conditionTips.size() - 0) + 0) + 0;
            JSONObject jsonObject = (JSONObject) parser.parse(conditionTips.get(r).toString());
            Tip tip = new Tip (
                (Long) (jsonObject.get("id")),
                jsonObject.get("tip_content").toString(),
                jsonObject.get("picture").toString()
            );        
            return (tip);
        } catch (Exception ex) {
            System.out.println("getRandomTaal_Tip: "  + ex);
        }
        return null;
    }
    
    /**
     * Convert JSON interaction_tip to string
     *
     * @return String with the interaction tip
     */
    public static Tip getRandomInteraction_Tip() {
        try {
            getAllTipsFromAPI();
            parser = new JSONParser();
            parser.parse(interaction_tip_random.toString());
            JSONArray allTips = (JSONArray) parser.parse(interaction_tip_random.get(0).toString());            
            Random random = new Random();
            int r = random.nextInt((allTips.size() - 0) + 0) + 0;
            JSONObject jsonObject = (JSONObject) parser.parse(allTips.get(r).toString());
            Tip tip = new Tip (
                (Long) (jsonObject.get("id")),
                jsonObject.get("tip_content").toString()                        
            );
            return (tip);
        } catch (Exception ex) {
            System.out.println("getRandomInteraction_Tip: " + ex);
        }
        return null;
    }
    
    /**
     * Makes API calls to get all info about the users and tips
     */
    public static void getAllTipsFromAPI() {
        try {
            interaction_tip_random = (JSONArray) getAPIRequest(URLInteractieTips);
            taal_tip_random = (JSONArray) getAPIRequest(URLTaalTips);
        } catch (Exception ex) {
            System.out.println("GetAllDataFromApi: " + ex);
        }
    }
    
    public static JSONArray getAPIRequest(String urlToRead) throws Exception {
      JSONArray test = new JSONArray();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         test.add(line);
      }
      rd.close();
      return test;
   }
}
