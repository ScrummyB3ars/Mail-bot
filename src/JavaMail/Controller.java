/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

/**
 *
 * @author User
 */
public class Controller {

    public Controller() {
    }
    
    /**
     * Sends mail with tips to a subscriber
     * @param id Id of the subscriber
     */
    public void sendMailToSubscriberById(int id){
        Weather weather = new Weather();
        Subscriber subscriber = Subscriber.getSubscriberInfoById(id);
        Tip interactionTip = Tip.getRandomInteraction_Tip();
        WeatherCondition weatherConditionForSubscriber = weather.getCondition(subscriber.zipCode);
        Tip taalTip = Tip.getRandomTaal_Tip_Based_On_Weather(weatherConditionForSubscriber);
        Mail.sendMailToSubscriber(subscriber, taalTip, interactionTip);   
    }
    
    /**
     * Sends mail with tips to all subscribers
     */
    public void sendMailToAllSubscribers(){
        Weather weather = new Weather();
        Subscriber[] allSubscribers = Subscriber.getAllSubscribers();
        for(Subscriber subscriber : allSubscribers){
            Tip interactionTip = Tip.getRandomInteraction_Tip();
            WeatherCondition weatherConditionForSubscriber = weather.getCondition(subscriber.zipCode);
            Tip taalTip = Tip.getRandomTaal_Tip_Based_On_Weather(weatherConditionForSubscriber);
            Mail.sendMailToSubscriber(subscriber, taalTip, interactionTip);
        }       
    }
}
