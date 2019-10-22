package com.gruppo4.SMSApp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSMessage;

//estende l'Activity AppCompat che serve a rendere la app retrocompatibile
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Richiediamo il permesso di leggere i messaggi
        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // invio messaggio vero e proprio
        SMSMessage messaggio = new SMSMessage("+393925788313","PROVA PROVONE");
    }
}
