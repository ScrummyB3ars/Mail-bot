package JavaMail;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;



/**
 *
 * @author User
 */
public class Kleuterblog {  
        
    /**
     * This is the main method of the application, the controller
     * @param args 
     */
    public static void main(String args[]){
     
        Controller controller = new Controller();
        controller.sendMailToSubscriber();
        


        
               
    }
}