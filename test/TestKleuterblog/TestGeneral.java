/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestKleuterblog;

import static JavaMail.Subscriber.getAPIRequest;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MilanT
 */
public class TestGeneral {
    
    
    @Test
    public void testGetAPIRequest() {
        try {
            String testPaginaAPI = "https://api-toddlr.herokuapp.com/test";
            JSONArray testArray = (JSONArray) getAPIRequest(testPaginaAPI); 
            JSONParser parser = new JSONParser();
            parser.parse(testArray.toString());
            assertEquals(testArray.get(0).toString(), "Welcome to the toddlr-api.");
        } catch (Exception e) {

        }
    }
}
