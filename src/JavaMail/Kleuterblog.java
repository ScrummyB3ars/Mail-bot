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
        

        /*try{    
            Controller mailObject = new Controller();
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