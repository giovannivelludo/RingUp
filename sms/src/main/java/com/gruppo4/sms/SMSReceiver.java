package com.gruppo4.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            Object[] smsExtra = (Object[]) extras.get("pdus");
            String format = (String) extras.get("format");
            SMSMessage message;
            for(int i=0;i<smsExtra.length;++i){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i], format);
                String text = sms.getMessageBody();
                String number = sms.getOriginatingAddress();
                //Message building
                message = new SMSMessage(number, text);

                SMSController.callReceivedListener(message);
            }
        }
    }
}
