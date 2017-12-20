package JavaMail;



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
        controller.sendMailToSubscriberById(9);
        //controller.sendMailToAllSubscribers();
        
    }
}