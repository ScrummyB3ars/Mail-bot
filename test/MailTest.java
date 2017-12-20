/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import JavaMail.Mail;
import JavaMail.Subscriber;
import JavaMail.Tip;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class MailTest {
    
    public MailTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test(expected = Exception.class)
    public void TestsendMailToSubscriber() {
        System.out.println("Test Send Mail to Subscirber");
        Mail instance = new Mail();
        Subscriber subs = new Subscriber();
        //Tip
        
        //instance.sendMailToSubscriber(subs, Tip taalTip, Tip interactionTip);
        
    }
}
