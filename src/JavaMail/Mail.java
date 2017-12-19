/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author User
 */
public class Mail {

    private static JSONArray subscriberInfo;
    private static JSONArray interaction_tip_random;
    private static JSONArray taal_tip_random;

    private static String host;
    private static String senderName;
    private static String SenderPassword;
    private static String mailAddressSender;

    private static JSONParser parser;
    private static Weather weather;
    private static Properties propertiesMailServer;
    private static int counter = 0;

    public Mail() {
        parser = new JSONParser();
    }

    /**
     * Settings of the mail server Get data mailAddressSender api call
     */
    public static void initializeMail() {
        getAllDataFromAPI();
        setSettingsMailserver();
    }

    private static void setSettingsMailserver() {
        host = "smtp.gmail.com";
        senderName = "bruno.testscrum@gmail.com";
        SenderPassword = "scrumkleuter";
        mailAddressSender = "bruno.testscrum@gmail.com";

        propertiesMailServer = System.getProperties();
        propertiesMailServer.put("mail.smtp.starttls.enable", "true");
        propertiesMailServer.put("mail.smtp.host", host);
        propertiesMailServer.put("mail.smtp.port", "587");
        propertiesMailServer.put("mail.smtp.auth", "true");
        propertiesMailServer.put("mail.smtp.starttls.required", "true");
    }

    /**
     * Makes API calls to get all info about the users and tips
     */
    private static void getAllDataFromAPI() {
        try {
            subscriberInfo = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/users");
            interaction_tip_random = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/interaction_tips");
            taal_tip_random = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips");
        } catch (Exception ex) {
            System.out.println("GetAllDataFromApi: " + ex);
        }
    }

    /**
     * Makes API call to get all info about the users
     */
    private static void getAllUsersFromAPI() {
        try {
            subscriberInfo = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/users");
        } catch (Exception ex) {
            System.out.println("getAllUsersFromAPI: " + ex);
        }
    }

    /**
     * Makes API call to get all info about the tips
     */
    private static void getRandomInteractionAndThemeTipFromAPI() {
        try {
            interaction_tip_random = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/interaction_tips/random");
            taal_tip_random = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips/random");
        } catch (Exception ex) {
            System.out.println("getRandomInteractionAndThemeTipFromAPI: " + ex);
        }
    }

    /**
     * Convert JSON taal_tip to string
     *
     * @return String with the taal tip
     */
    private static Tips getRandomTaal_Tip(WeatherCondition weatherCondition) {
        try {
            parser.parse(taal_tip_random.toString());
            JSONArray allTips = (JSONArray) parser.parse(taal_tip_random.get(0).toString());
            ArrayList conditionTips = new ArrayList();
            for (int i = 0; i < allTips.size(); i++) {
                JSONObject jsonObject = (JSONObject) parser.parse(allTips.get(i).toString());
                if(weatherCondition == WeatherCondition.REGEN){
                    if(((String) jsonObject.get("circumstances").toString()).contains("regen")){
                        conditionTips.add(jsonObject);
                    }
                }else if(weatherCondition == WeatherCondition.WOLKEN){
                    if(((String) jsonObject.get("circumstances").toString()).contains("wolk") || ((String) jsonObject.get("circumstances").toString()).contains("wind")){
                        conditionTips.add(jsonObject);
                    }
                }else if(weatherCondition == WeatherCondition.SNEEUW){
                    if(((String) jsonObject.get("circumstances").toString()).contains("winter") || ((String) jsonObject.get("circumstances").toString()).contains("sneeuw")){
                        conditionTips.add(jsonObject);
                    }
                }else if(weatherCondition == WeatherCondition.ZONNIG){
                    if(((String) jsonObject.get("circumstances").toString()).contains("zon")){
                        conditionTips.add(jsonObject);
                    }
                }else{
                    if(((String) jsonObject.get("circumstances").toString()).contains("zon")){
                        conditionTips.add(jsonObject);
                    }
                }                
            }
            Random random = new Random();
            
            int r = random.nextInt((conditionTips.size() - 0) + 0) + 0;
            JSONObject jsonObject = (JSONObject) parser.parse(conditionTips.get(r).toString());
            Tips tip = new Tips (
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
    private static Tips getRandomInteraction_Tip() {
        try {
            parser.parse(interaction_tip_random.toString());
            JSONArray allTips = (JSONArray) parser.parse(interaction_tip_random.get(0).toString());            
            Random random = new Random();
            int r = random.nextInt((allTips.size() - 0) + 0) + 0;
            JSONObject jsonObject = (JSONObject) parser.parse(allTips.get(r).toString());
            Tips tip = new Tips (
                (Long) (jsonObject.get("id")),
                jsonObject.get("tip_content").toString()                
            );
            return (tip);
        } catch (Exception ex) {
            System.out.println("getRandomInteraction_Tip: " + ex);
        }
        return null;
    }

    private static Subscriber getSubscriberInfo() {
        try {
            Subscriber subscriber = new Subscriber();
            parser.parse(subscriberInfo.toString());
            JSONArray jsonObject = (JSONArray) parser.parse(subscriberInfo.get(0).toString());
            JSONObject json = (JSONObject) jsonObject.get(5); //debug
            subscriber.emailAddress = json.get("email").toString();
            subscriber.userName = json.get("username").toString();
            subscriber.idSubrsciber = Integer.parseInt(json.get("id").toString());
            getAllSubscribers();
            return subscriber;
        } catch (Exception ex) {
            System.out.println("getSubscriberInfo: " + ex);
        }
        return null;
    }

    private static Subscriber[] getAllSubscribers() {
        try {
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
     * This method will send a mail with tips to all subscribers
     */
    public static void sendMailToAllSubscribers() {
        weather = new Weather();
        try {
            Subscriber[] subscribers = getAllSubscribers();
            for (Subscriber subscriber : subscribers) {
                WeatherCondition weatherCondition = (weather.getCondition(subscriber.zipCode));
                Tips taal_tip = getRandomTaal_Tip(weatherCondition);
                Tips interaction_tip = getRandomInteraction_Tip();
                setSessionToSendMail(subscriber.emailAddress, taal_tip.tipContent, interaction_tip.tipContent, subscriber.userName, taal_tip.image);
            }
        } catch (Exception ex) {
            System.out.println("sendMailToAllSubscribers: " + ex);
        }
    }

    /**
     * This method will send a mail with tips to a subscriber
     *
     * @param subscriber Subscriber that wants to receive a maikl
     */
    public static void sendMailToSubscriber() {
        try {
            weather = new Weather();
            Subscriber subscriber = getAllSubscribers()[5];
            WeatherCondition weatherCondition = (weather.getCondition(subscriber.zipCode));
            Tips taal_tip = getRandomTaal_Tip(weatherCondition);        
            Tips interaction_tip = getRandomInteraction_Tip();
            setSessionToSendMail("dedeynebruno97@gmail.com", taal_tip.tipContent, interaction_tip.tipContent, subscriber.userName, taal_tip.image);
        } catch (Exception ex) {
            System.out.println("sendMailToSubscriber: " + ex);
        }
    }

    /**
     * This method will create the HTML for inside the email
     *
     * @param team_tip Team_tip for the senderName
     * @param interaction_tip Interaction_tip for the senderName
     * @param name Name of the senderName
     * @return String with the HTML
     */
    private static String initializeMailLayout(String team_tip, String interaction_tip, String name, String image) {
        try {
            VelocityEngine velocityEngineObject = new VelocityEngine();
            velocityEngineObject.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngineObject.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngineObject.init();
            Template mailTemplate = velocityEngineObject.getTemplate("Templates/mail.vm");
            VelocityContext context = new VelocityContext();
            context.put("name", name);
            context.put("interaction_tip", interaction_tip);
            context.put("team_tip", team_tip);
            context.put("image", image);

            StringWriter writer = new StringWriter();
            mailTemplate.merge(context, writer);
            return writer.toString();
        } catch (Exception ex) {
            System.out.println("initializeMailLayout: " + ex);
        }
        return null;
    }
    
    
    private static Message createMessage(Session mailSession, String mail, String taal_tip, String interaction_tip, String name, String image) {
        Message msg = new MimeMessage(mailSession);
        try {
            msg.setFrom(new InternetAddress(mailAddressSender));
            InternetAddress[] address = {new InternetAddress(mail)};
            String subject = "Toddlr - Nieuwe tips!";
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(initializeMailLayout(taal_tip, interaction_tip, name, image), "text/html; charset=utf-8");
            return msg;
        } catch (Exception ex) {
            System.out.println("createMessage: " + ex);
        }
        return null;
    }

    private static void sendMail(Session mailSession, Message msg) {
        try {
            try (Transport transport = mailSession.getTransport("smtp")) {
                transport.connect(host, senderName, SenderPassword);
                transport.sendMessage(msg, msg.getAllRecipients());
            }
        } catch (Exception ex) {
            System.out.println("sendMail: " + ex);
        }
    }

    private static void setSessionToSendMail(String mail, String taal_tip, String interaction_tip, String name, String image) {
        try {
            setSettingsMailserver();
            getAllDataFromAPI();
            boolean sessionDebug = false; //debug
            counter++; //debug

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            Session mailSession = Session.getDefaultInstance(propertiesMailServer, null);
            mailSession.setDebug(sessionDebug);

            Message msg = createMessage(mailSession, mail, taal_tip, interaction_tip, name, image);
            sendMail(mailSession, msg);
            System.out.println("Mail naar: " + mail + ", " + taal_tip); //debug
        } catch (Exception ex) {
            System.out.println("setSessionToSendMail: " + ex);
        }
    }

    private static JSONArray getAPIRequest(String urlToRead) throws Exception {
        JSONArray jsonArray = new JSONArray();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonArray.add(line);
            }
        }
        catch (Exception ex) {
            System.out.println("getAPIRequest: " + ex);
        }
        return jsonArray;
    }

}
