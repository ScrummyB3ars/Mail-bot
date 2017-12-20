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

/**
 *
 * @author MilanT
 */
public class Subscriber {

    public int idSubrsciber;
    public String userName;
    public String emailAddress;
    public int zipCode;

    private static String URLSubscribers = "https://api-toddlr.herokuapp.com/users";
    private static JSONArray subscriberInfo;
    private static JSONParser parser;

    public Subscriber(int idSubrsciber, String userName, String emailAddress, int zipCode) {
        this.idSubrsciber = idSubrsciber;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.zipCode = zipCode;
    }

    public Subscriber() {
        parser = new JSONParser();
    }

    /**
     * This method gets a subscriber based on his id
     *
     * @param id Id of the user
     * @return Subscriber that belongs to the id
     */
    public static Subscriber getSubscriberInfoById(int id) {
        Subscriber subscriber;
        try {
            getAllSubscribersFromAPI();
            parser = new JSONParser();
            parser.parse(subscriberInfo.toString());
            JSONArray jsonObject = (JSONArray) parser.parse(subscriberInfo.get(0).toString());

            for (int i = 0; i < jsonObject.size(); i++) {
                JSONObject json = (JSONObject) jsonObject.get(i);
                subscriber = new Subscriber();
                subscriber.emailAddress = json.get("email").toString();
                subscriber.userName = json.get("username").toString();
                subscriber.idSubrsciber = Integer.parseInt(json.get("id").toString());
                subscriber.zipCode = Integer.parseInt(json.get("zip_code").toString());
                if (subscriber.idSubrsciber == id) {
                    return subscriber;
                }
            }
        } catch (Exception ex) {
            System.out.println("getAllSubscribers: " + ex);
        }
        return null;
    }

    /**
     * This method gets all subscribers
     *
     * @return List of all subscribers
     */
    public static Subscriber[] getAllSubscribers() {
        try {
            getAllSubscribersFromAPI();
            parser = new JSONParser();
            parser.parse(subscriberInfo.toString());
            JSONArray jsonObject = (JSONArray) parser.parse(subscriberInfo.get(0).toString());
            Subscriber[] subscribers = new Subscriber[jsonObject.size()];
            for (int i = 0; i < jsonObject.size(); i++) {
                JSONObject json = (JSONObject) jsonObject.get(i);
                Subscriber subscriber = new Subscriber();
                subscriber.emailAddress = json.get("email").toString();
                subscriber.userName = json.get("username").toString();
                subscriber.idSubrsciber = Integer.parseInt(json.get("id").toString());
                subscriber.zipCode = Integer.parseInt(json.get("zip_code").toString());
                subscribers[i] = subscriber;
            }
            return subscribers;
        } catch (Exception ex) {
            System.out.println("getAllSubscribers: " + ex);
        }
        return null;
    }

    /**
     * Makes API call to get all info about the users
     */
    private static void getAllSubscribersFromAPI() {
        try {
            subscriberInfo = (JSONArray) getAPIRequest(URLSubscribers);
        } catch (Exception ex) {
            System.out.println("getAllUsersFromAPI: " + ex);
        }
    }

    public static JSONArray getAPIRequest(String urlToRead) throws Exception {
        JSONArray APIInfo = new JSONArray();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            APIInfo.add(line);
        }
        rd.close();
        return APIInfo;
    }
}
