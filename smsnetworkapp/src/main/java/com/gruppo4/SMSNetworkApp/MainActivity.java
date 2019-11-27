package com.gruppo4.SMSNetworkApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.gruppo4.sms.dataLink.SMSHandler;
import com.gruppo4.sms.dataLink.SMSMessage;
import com.gruppo4.sms.network.SMSAbstractNetworkListener;

public class MainActivity extends AppCompatActivity
{
    static final int APP_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        class SMSNetworkListener extends SMSAbstractNetworkListener{
            public void onJoinProposal(SMSMessage message){
                MainActivity.this.onJoinProposal(message);
            }
        }
        SMSNetworkListener listener = new SMSNetworkListener();
        SMSHandler handler = SMSHandler.getInstance(getApplicationContext());
        handler.setup(APP_CODE);
        handler.addReceivedMessageListener(listener);
        setContentView(R.layout.activity_main);
    }

    public void onJoinProposal(SMSMessage message){

    }
}
