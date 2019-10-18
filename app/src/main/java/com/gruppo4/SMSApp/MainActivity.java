package com.gruppo4.SMSApp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.telephony.SmsManager;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSMessage;
import com.gruppo4.sms.exceptions.InvalidSMSMessageException;
import com.gruppo4.sms.exceptions.InvalidTelephoneNumberException;
import com.gruppo4.sms.listeners.SMSRecieveListener;

public class MainActivity extends AppCompatActivity implements SMSRecieveListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Richiediamo il permesso di leggere i messaggi
        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);

        SMSController.addOnReceiveListener(this);

        SMSController controller = new SMSController();
        try {
            SMSMessage message = new SMSMessage("+393467965447", "Ciao Luca");
            controller.sendMessage(this, message);
        }catch(InvalidSMSMessageException messageException){
            Log.e("MainActivity",messageException.getMessage());
        }catch(InvalidTelephoneNumberException telephoneException){
            Log.e("MainActivity",telephoneException.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSMSRecieve(SMSMessage message) {
        Log.d("MainActivity","Ricevuto onReceive");
        Toast.makeText(this,"MAIN ACTIVITY:"+message.getMessageText(), Toast.LENGTH_LONG).show();
    }
}
