/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestKleuterblog;

import JavaMail.Subscriber;
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
public class SubscriberUnitTest {

    private Subscriber testSubscriber;
    private static final int idSub = 12345;
    private static final String email = "tester@gmail.cam";
    private static final String userName = "tester46";
    private static final int zipCode = 7894;

    public SubscriberUnitTest() {
        testSubscriber = new Subscriber();
    }

    @Test
    public void testSubscriberOject() {
        Subscriber testSub = new Subscriber(idSub, userName, email, zipCode);
        assertEquals(testSub.emailAddress, email);
        assertEquals(testSub.userName, userName);
        assertEquals(testSub.idSubrsciber, idSub);
        assertEquals(testSub.zipCode, zipCode);
    }
    
    @Test
    public void testGetSubscriberInfoById(){
        int idTestUser = 9;
        testSubscriber = new Subscriber();
        Subscriber testSubscriber = Subscriber.getSubscriberInfoById(idTestUser);
        assertNotNull(testSubscriber);
        assertEquals(testSubscriber.emailAddress, "test@user.be");
        assertEquals(testSubscriber.userName, "testuser");
        assertEquals(testSubscriber.idSubrsciber, 9);
        assertEquals(testSubscriber.zipCode, 9000);
    }
    
    @Test
    public void testGetAllSubscribers(){
        Subscriber[] allSubscribers = Subscriber.getAllSubscribers();
        assertNotNull(allSubscribers);
        for(Subscriber subsciber : allSubscribers){
            if(subsciber.idSubrsciber == 9){
                assertEquals(subsciber.emailAddress, "test@user.be");
                assertEquals(subsciber.userName, "testuser");
                assertEquals(subsciber.idSubrsciber, 9);
                assertEquals(subsciber.zipCode, 9000);
            }
        }
    }

}
