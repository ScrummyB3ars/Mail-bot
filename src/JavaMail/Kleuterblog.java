package JavaMail;

import java.util.Timer;
import java.util.TimerTask;



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
        
        Weather weather = new Weather(); 
        try{    
            Mail mailObject = new Mail();
            Timer timer = new Timer();
            
            timer.scheduleAtFixedRate(new TimerTask() {                 
                @Override
                public void run(){
                    mailObject.initializeMail();
                    mailObject.sendMailToSubscriber();
                }
            }, 100, 604800);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
        
               
    }
}