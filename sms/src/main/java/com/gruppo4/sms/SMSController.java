// SMSController
package com.gruppo4.sms;

import android.telephony.SmsManager;

import java.util.ArrayList;

public class SMSController {

    // variabile d'esemplare che raccoglie i listeners in ricezione
    private static ArrayList<SMSReceivedListener> receivedListeners = new ArrayList<>();
    // Costruttore di default - non inizializzo nulla, perche'
    // receivedListeners e' gia' inizializzata.
    public SMSController(){};

    // Invio messaggio
    public static void sendMessage(SMSMessage messaggio){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(messaggio.getNumber(), null, messaggio.getText(),null, null);

    }
    // Aggiunge il listener da parametro nella variabile d'esemplare
    public static void addListener(SMSReceivedListener listener){
        receivedListeners.add(listener);
    }

    //
    public static void callReceivedListener(SMSMessage message){
        for (SMSReceivedListener listener:receivedListeners) {
            listener.onMessageReceived(message);
        }
    }


}