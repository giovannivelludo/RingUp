package com.gruppo4.SMSApp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSMessage;
import com.gruppo4.sms.interfaces.SMSReceivedListener;
import com.gruppo4.sms.interfaces.SMSSentListener;

public class MainActivity extends AppCompatActivity implements SMSReceivedListener, SMSSentListener {

    /**
     * Il messaggio vero e proprio
     */
    SMSMessage smsMessage;
    /**
     * Le 3 caselle di testo, di cui la prima di queste e' quella in cui si scrive
     * il numero di telefono a cui si vuole inviare lo smiley
     */
    TextView numberTextView, smileReceiver, textView;
    /**
     * Il bottone che serve per inviare lo smiley :)
     */
    Button smileButton;

    /**
     * onCreate. la creazione dell'Activity. Fa parte del ciclo di vita dell'Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    /**
     * Operazioni preliminari, realizzate nella superclasse.
     */
        super.onCreate(savedInstanceState);
    /**
     * setContentView serve a collegare i nostri oggetti a un certo layout (?),
     * in questo caso si tratta di activity_main nel package layout di res (R).
     */
        setContentView(R.layout.activity_main);

     /**
      * R sta per Resources.
      * R e' molto utile per il collegamento diretto alle Risorse (package "res").
      * findViewById serve per cercare la risorsa, e abbinarla alla nostra
      * variabile d'esemplare.
      * Si ha creato delle variabili d'esemplare, e adesso si vuole collegarle
      * al loro identificativo, in questo caso e' relativo all'activity_main in layout.
      */
        numberTextView = findViewById(R.id.number);
        smileButton = findViewById(R.id.smile_button);
        smileReceiver = findViewById(R.id.smileReceiver);
        textView = findViewById(R.id.textView);

      /**
       * Questa Activity viene passata come listener (???)
       */
        SMSController.addOnReceivedListener(this);

        /**
         * Si imposta l'azione collegata al clic del bottone smileButton.
         */
        smileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      /**
       * Si estrae il numero telefonico dalla casella di testo.
       * Dato che l'output e' di tipo CharSequence, allora lo si deve
       * trasformare in stringa, grazie al metodo toString().
       */
                String telephoneNumber = numberTextView.getText().toString();
       /**
        * Controllo per verificare se il numero di telefono e' valido o no.
        * Credits: Luca Crema feat. gruppo 4.
       */
                if (!SMSMessage.checkTelephoneNumber(telephoneNumber)) {
       /**
        * Il Toast e' fantastico.
        * Questo Toast serve a creare una piccola finestra a comparsa in basso
        * che notifica se il numero inserito e' corretto oppure no.
        * Il .show() finale serve per svelare il Toast appena creato.
        * Appena fatto un toast, lo si vuole servire a tavola! :-P
       */
                    Toast.makeText(MainActivity.this, "Wrong telephone number", Toast.LENGTH_LONG).show();
                    return;
                }
       /**
        * Il messaggio vero e proprio! :)
        * Si passa per parametro il numero di telefono, e la stringa testuale!
        */
                smsMessage = new SMSMessage(telephoneNumber, "Sent you a smile :)");
        /**
         * L'invio del messaggio, gestito da SMSController, che a sua volta
         * sfrutta in parte la classe SMSManager della libreria Android.
         */
                SMSController.sendMessage(smsMessage, getBaseContext(), MainActivity.this);
            }
        });
    }

    /**
     * onSentReceived. realizza l'interfaccia SMSSentListener.
     *
     */
    @Override
    public void onSentReceived(SMSMessage message) {
        switch (message.getState()) {
            case MESSAGE_SENT:
                Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
                textView.setText("Message sent to " + smsMessage.getTelephoneNumber());
                break;
            default:
                Toast.makeText(this, "Message not sent", Toast.LENGTH_SHORT).show();
                textView.setText("Message not sent to " + smsMessage.getTelephoneNumber());
                break;
        }

    }

    @Override
    public void onMessageReceived(SMSMessage message) {
        smileReceiver.setText(message.getTelephoneNumber() + " " + message.getMessage());
    }
}
