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
        
        Mail mailObject = new Mail();
        mailObject.initializeMail();
        mailObject.sendMailToSubscriber();
        /*try{    
            Mail mailObject = new Mail();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {                 
                @Override
                public void run(){
                    mailObject.initializeMail();
                    mailObject.sendMailToSubscriber();
                }
            }, 0, 10000);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }*/
        
               
    }
}