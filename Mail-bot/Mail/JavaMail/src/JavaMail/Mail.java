/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
import javax.net.ssl.HttpsURLConnection;

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
    private static JSONArray interaction_hints;
    private static JSONArray taal_hints;
    private static JSONArray mail_has_hints;
    private static String host;
    private static String user;
    private static String pass;
    private static String from;
    private static JSONParser parser;
    private static JSONParser parser2;
    private static Properties props;
    private static int counter = 0;

    public Mail (){        
        parser = new JSONParser();
    }
    
    /**
     * Settings of the mail server 
     * Get data from api call
     */
    public static void initializeMail() {
        getAllData();
        setSettingsMailserver();
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
    
        public static String initializeHtml (String team_tip, String interaction_tip, String name) {        
        try 
        {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate( "Templates/mail.vm" );
            VelocityContext context = new VelocityContext();
            context.put("name", name.toString());
            context.put("interaction_tip", interaction_tip.toString());
            context.put("team_tip", team_tip.toString());
            
            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            return writer.toString();
        } catch (Exception ex){

        }
        return null;
    }

    private static void getAllData () {        
        try
        {
            //mailAddresses = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/users");   
            //interaction_hints = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/interaction_tips/random");
            //taal_hints = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips/random");
            mail_has_hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\mail_has_hints.json"));
            mailAddresses = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\mail.json"));   
            taal_hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\hints.json"));   
            interaction_hints = (JSONArray) parser.parse(new FileReader("..\\JavaMail\\jsonFiles\\hints.json"));

        } catch (Exception ex)
        {
            System.out.println(ex);
        }        
    }
    
    private static void getAllMailAddresses () {        
        try
        {     
            mailAddresses = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/users");   
        } catch (Exception ex)
        {
            System.out.println(ex);
        }        
    }   
    
    private static void getAllHints(){
        try
        {
            interaction_hints = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/interaction_tips/random");
            taal_hints = (JSONArray) getAPIRequest("https://api-toddlr.herokuapp.com/theme_tips/random");
        } catch (Exception ex)
        {
            System.out.println(ex);
        } 
    }
    
    private static void getAddressToHint(){
        try
        {
            mail_has_hints = (JSONArray) parser.parse(new FileReader("..\\..\\Jsonfiles\\mail_has_hints.json"));
        } catch (Exception ex)
        {
            System.out.println(ex);
        } 
    }

    public static void checkMail (){
        String mail = (String) getRandomMailAddress();
        String taal_tip = (String) getRandomTaal_Hint();
        String interaction_tip = (String) getRandomInteraction_Hint();
        String name = mail ;
        sendMail(mail, taal_tip, interaction_tip, name);
    }
    
    private static String getRandomTaal_Hint() {
        Random r = new Random();
	int random = r.nextInt(taal_hints.size());
        JSONObject selectedRandomObject = (JSONObject) taal_hints.get(random);
        String rtrn = selectedRandomObject.get("hint").toString();
        return (rtrn);    
        
        //Code voor api        
        /*parser.parse(taal_hints.toString());
        JSONObject jsonObject2 = (JSONObject) parser.parse(taal_hints.get(0).toString());
        return(jsonObject2.get("tip_content").toString());*/
    }
    
    private static String getRandomInteraction_Hint() {
        Random r = new Random();
	int random = r.nextInt(interaction_hints.size());
        JSONObject selectedRandomObject = (JSONObject) interaction_hints.get(random);        
        String rtrn = selectedRandomObject.get("hint").toString();
        return (rtrn);    
        
        //Code voor api
        /*parser.parse(interaction_hints.toString());
        JSONObject jsonObject = (JSONObject) parser.parse(interaction_hints.get(0).toString());
        return(jsonObject.get("tip_content").toString());*/
    }
    
    private static String getRandomMailAddress() {       
        Random r = new Random();
	int random = r.nextInt(mailAddresses.size());
        JSONObject selectedRandomObject = (JSONObject) mailAddresses.get(random);
        String rtrn = selectedRandomObject.get("mail").toString();
        return (rtrn);   
        
        //Code voor api
        /*parser.parse(mailAddresses.toString());
        JSONArray jsonObject = (JSONArray) parser.parse(mailAddresses.get(0).toString());
        JSONObject json = (JSONObject) jsonObject.get(r);
        JSONObject mail = json.get("email");
        return (mail.toString())*/
    }
    
    private static Message createMessage(Session mailSession, String mail, String taal_tip, String interaction_tip, String name){
        Message msg = new MimeMessage(mailSession);
        try{
            
            msg.setFrom(new InternetAddress(from));
            //InternetAddress[] address = {new InternetAddress((String)mail.get("mail"))};
            InternetAddress[] address = {new InternetAddress(mail)};
            String subject = "Hint (" + counter + ")";    
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setContent(initializeHtml(taal_tip, interaction_tip, name), "text/html; charset=utf-8");
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
    
    public static void sendMail (String mail, String taal_tip, String interaction_tip , String name) {
        try 
        {
            setSettingsMailserver();   
            getAllData();   
            boolean sessionDebug = false; //debug
            counter++; //debug
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);

            Message msg = createMessage(mailSession, mail, taal_tip, interaction_tip, name);
            sendMail(mailSession, msg);
            System.out.println("Mail naar: " + mail + ", " + taal_tip); //debug
        } catch (Exception ex)
        {

        }
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



}