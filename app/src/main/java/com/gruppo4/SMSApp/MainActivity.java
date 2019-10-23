package com.gruppo4.SMSApp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSMessage;
import com.gruppo4.sms.SMSReceivedListener;

//estende l'Activity AppCompat che serve a rendere la app retrocompatibile
public class MainActivity extends AppCompatActivity implements SMSReceivedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Richiediamo il permesso di leggere i messaggi
        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1);
        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // aggiungi listener (cerca nuovi messaggi)...
        SMSController.addListener(this);
        // invio messaggio di prova
        SMSMessage messaggio = new SMSMessage("+393925788313","PROVA PROVONE");
        SMSController.sendMessage(messaggio);

    }

    @Override
    public void onMessageReceived(SMSMessage message) {
        Toast.makeText(this, message.getNumber()+" ti ha inviato "+message.getText(), Toast.LENGTH_LONG).show();
    }
}
