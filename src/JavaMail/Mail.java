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

    private static String host;
    private static String senderName;
    private static String SenderPassword;
    private static String mailAddressSender;

    private static JSONParser parser;
    private static Properties propertiesMailServer;

    public Mail() {
        parser = new JSONParser();
    }

    /**
     * Settings of the mail server Get data mailAddressSender api call
     */
    public static void initializeMail() {
        setSettingsMailserver();
    }

    public static void setSettingsMailserver() {
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
     * This method will send a mail with tips to a subscriber
     *
     * @param subscriber Subscriber that wants to receive a mail
     */
    public static void sendMailToSubscriber(Subscriber subscriber, Tip taalTip, Tip interactionTip) throws NullPointerException{
        if (subscriber == null || taalTip == null||  interactionTip == null) {
            throw new NullPointerException("Variable is null");
        } 
        try {            
            setSessionToSendMail("dedeynebruno97@gmail.com", taalTip.tipContent, interactionTip.tipContent, subscriber.userName, taalTip.image);
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
    public static String initializeMailLayout(String team_tip, String interaction_tip, String name, String image) throws NullPointerException{
        if (team_tip == null || interaction_tip == null||  name == null || image == null) {
            throw new NullPointerException("Variable is null");
        } 
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
    
    
    public static Message createMessage(Session mailSession, String mail, String taal_tip, String interaction_tip, String name, String image) throws NullPointerException{
        if (mailSession == null || mail == null||  taal_tip == null || interaction_tip == null || name == null || image == null) {
            throw new NullPointerException("Variable is null");
        }
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

    public static void sendMail(Session mailSession, Message msg) throws NullPointerException{
        if (mailSession == null || msg == null) {
            throw new NullPointerException("Variable is null");
        }
        try {
            try (Transport transport = mailSession.getTransport("smtp")) {
                transport.connect(host, senderName, SenderPassword);
                transport.sendMessage(msg, msg.getAllRecipients());
            }
        } catch (Exception ex) {
            System.out.println("sendMail: " + ex);
        }
    }

    public static void setSessionToSendMail(String mail, String taal_tip, String interaction_tip, String name, String image) throws NullPointerException{
        if (mail == null || taal_tip == null||  interaction_tip == null || name == null || image == null) {
            throw new NullPointerException("Variable is null");
        }
        try {
            setSettingsMailserver();
            boolean sessionDebug = false; //debug

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
