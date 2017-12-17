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
        //Subscriber subscrive = new Subscriber();
        
        weather.getCondition(8820);    
        /*try{    
            Mail mailObject = new Mail();
            //Weather weather = new Weather();
            mailObject.initializeMail();
            //mail.getAllHints();
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {                 
                @Override
                public void run(){
                    mailObject.checkMail();
                    //weather.getCondition(9000);
                }
            }, 100, 5000);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }*/
        
               
    }
}