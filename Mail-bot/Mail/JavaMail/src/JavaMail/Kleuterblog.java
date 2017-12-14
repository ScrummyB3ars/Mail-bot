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
        try{            
            Mail mailObject = new Mail();   
            mailObject.initializeMail();
            mailObject.initializeHtml();
            //mail.getAllHints();
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {                 
                @Override
                public void run(){
                    mailObject.checkMail();
                    //mailObject.api();
                }
            }, 100, 500);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
            
    }
}