/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Random;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author User
 */
public class Mail{
    private static JSONArray mailAddresses;
    private static JSONArray hints;
    private static JSONArray mail_has_hints;
    private static String host;
    private static String user;
    private static String pass;
    private static String from;
    private static JSONParser parser;
    private static Properties props;
    private static int counter = 0;

    public Mail (){        
        parser = new JSONParser();
    }
    
    /**
     * Settings of the mail server 
     * Get data from API call
     */
    public static void initializeMail() {
        setSettingsMailserver();
        getAllData();
    }
    
    public static String initializeHtml () {        
        try 
        {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
        
            VelocityContext context = new VelocityContext();
            context.put("name", "World");
            Template t = ve.getTemplate( "Templates/mail.vm" );
            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            return writer.toString();
        } catch (Exception ex){

        }
        return null;
    }

    private static void setSettingsMailserver (){
        host ="smtp.gmail.com" ;
        user = "bruno.testscrum@gmail.com";
        pass = "scrumkleuter";
        from = "bruno.testscrum@gmail.com";

        props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
    }

    private static void getAllData () {        
        try
        {
            mailAddresses = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\mail.json"));   
            hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\hints.json"));
            mail_has_hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\mail_has_hints.json"));
        } catch (Exception ex)
        {
            System.out.println(ex);
        }        
    }
    
    private static void getAllMailAddresses () {        
        try
        {
            mailAddresses = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\mail.json"));   
        } catch (Exception ex)
        {
            System.out.println(ex);
        }        
    }   
    
    private static void getAllHints(){
        try
        {
            hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\hints.json"));
        } catch (Exception ex)
        {
            System.out.println(ex);
        } 
    }
    
    private static void getAddressToHint(){
        try
        {
            mail_has_hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\mail_has_hints.json"));
        } catch (Exception ex)
        {
            System.out.println(ex);
        } 
    }

    public static void checkMail (){
        String mail = (String) getRandomMailAddress().get("mail");
        String hint = (String) getRandomHint().get("hint") ;
        sendMail(mail, hint);
    }
    
    private static JSONObject getRandomHint() {
        Random r = new Random();
	int random = r.nextInt(hints.size());
        JSONObject selectedRandomObject = (JSONObject) hints.get(random);
        return (selectedRandomObject);        
    }
    
    private static JSONObject getRandomMailAddress() {
        Random r = new Random();
	int random = r.nextInt(mailAddresses.size());
        JSONObject selectedRandomObject = (JSONObject) mailAddresses.get(random);
        return (selectedRandomObject);        
    }
    
    private static Message createMessage(Session mailSession, String mail, String hint){
        Message msg = new MimeMessage(mailSession);
        try{
            
            msg.setFrom(new InternetAddress(from));
            //InternetAddress[] address = {new InternetAddress((String)mail.get("mail"))};
            InternetAddress[] address = {new InternetAddress(mail)};
            String subject = "Hint (" + counter + ")";    
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setContent(initializeHtml(), "text/html; charset=utf-8");
            return msg;
        } catch (Exception ex)
        {

        }
        return null;
    }
    
    private static void sendMail(Session mailSession, Message msg){
        try{
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }catch (Exception ex)
        {
        }
    }
    
    public static void sendMail (String mail, String hint) {
        try 
        {
            setSettingsMailserver();   
            getAllData();   
            boolean sessionDebug = false; //debug
            counter++; //debug
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);

            Message msg = createMessage(mailSession, mail, "TEST");
            sendMail(mailSession, msg);
            System.out.println("Mail naar: " + mail + ", " + hint); //debug
        } catch (Exception ex)
        {

        }
    }

}