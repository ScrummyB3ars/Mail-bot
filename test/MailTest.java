/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import JavaMail.Mail;
import JavaMail.Subscriber;
import JavaMail.Tip;
import java.util.Properties;
import javax.mail.Message;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.mail.Session;

/**
 *
 * @author User
 */
public class MailTest {
    private static Properties propertiesMailServer;
    private Mail mail;
    
    public MailTest() {
        mail = new Mail();
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

    @Test(expected = NullPointerException.class)
    public void TestSendMailToSubscriberVariables() {
        System.out.println("TestSendMailToSubscriberVariables");
        mail.sendMailToSubscriber(null, null, null);
        
        fail("TestSendMailToSubscriberVariables Failed");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestinitializeMailLayoutVariables() {
        System.out.println("TestinitializeMailLayoutVariables");
        mail.initializeMailLayout(null, null, null, null);
        
        fail("TestinitializeMailLayoutVariables Failed");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestcreateMessageVariables() {
        System.out.println("TestcreateMessageVariables");
        mail.createMessage(null, null, null, null, null, null);
        
        fail("TestcreateMessageVariables Failed");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestsendMailVariables() {
        System.out.println("TestsendMailVariables");
        mail.sendMail(null, null);
        
        fail("TestsendMailVariables Failed");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestsetSessionToSendMailVariables() {
        System.out.println("TestsetSessionToSendMailVariables");
        mail.setSessionToSendMail(null, null, null, null, null);
        
        fail("TestsetSessionToSendMailVariables Failed");
    }   
    
    @Test
    public void TestinitializeMailLayoutObject() throws Exception{
        System.out.println("TestinitializeMailLayoutObject");         
        
        assertThat((mail.initializeMailLayout("test", "test", "test", "test")), instanceOf(String.class));        
    } 
}
