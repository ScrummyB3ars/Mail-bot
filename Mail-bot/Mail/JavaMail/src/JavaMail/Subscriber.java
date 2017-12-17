/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static javafx.css.StyleOrigin.USER_AGENT;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONArray;

/**
 *
 * @author MilanT
 */
public class Subscriber {
    
    private int idSubrsciber;
    private String name;
    private String firstName;
    private String emailAddress;
    private String URLAddSubscriber = "https://api-toddlr.herokuapp.com/addSubscriber";
    
    public Subscriber(int idSubrsciber, String name, String firstName, String emailAddress){
        this.idSubrsciber = idSubrsciber;
        this.name = name;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }
    
    public void Unsubscribe(){
        try{
            JSONArray weatherInfo = (JSONArray) getAPIRequest(URLAddSubscriber);
        }catch(Exception e){
            
        }
        
    }
    
    public void Subscribe(){
        
    }
    
    private static JSONArray getAPIRequest(String urlToRead) throws Exception {
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
    
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "{\"facebook_id\":\"5879654\",\"age_group\":\"jongste\"}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
