package com.gruppo4.sms.dataLink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceivedBroadcastReceiver extends BroadcastReceiver {

    public static Class listener;

    public void onReceive(Context context, Intent intent) {
        Log.v("SMSReceiver", "Received message from android broadcaster");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");
            String format = (String) extras.get("format");
            Log.v("SMSReceiver", "Extras length: " + smsExtra.length);
            for (int i = 0; i < smsExtra.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i], format);
                String smsContent = sms.getMessageBody();
                String phoneNumber = sms.getOriginatingAddress();
                Log.d("SMSReceiver", "Parsing the message");
                SMSMessage message = SMSMessageHandler.getInstance().parseMessage(smsContent, phoneNumber);
                if (message != null && message.getApplicationID() == SMSHandler.getInstance(context).getApplicationCode()) {
                    Log.d("SMSReceiver", "Message is valid");
                    if (listener != null) {
                        Log.d("SMSReceiver", "Calling serivice via intent");
                        Intent serviceIntent = new Intent(context, listener);
                        serviceIntent.putExtra("Message", message);
                        context.startService(serviceIntent);
                    } else {
                        Log.e("SMSReceiver", "Listener class is null");
                    }
                }
            }
        }
    }
}
