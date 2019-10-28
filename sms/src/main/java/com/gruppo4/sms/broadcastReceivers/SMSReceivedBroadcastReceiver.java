package com.gruppo4.sms.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSPacket;
import com.gruppo4.sms.utils.SMSChecks;

public class SMSReceivedBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            Object[] smsExtra = (Object[]) extras.get("pdus");
            String format = (String) extras.get("format");
            for(int i = 0; i < smsExtra.length; ++i){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i], format);
                String smsContent = sms.getMessageBody();
                String number = sms.getOriginatingAddress();
                SMSPacket packet = null;
                String[] splits = smsContent.split(SMSPacket.SEPARATOR, 5);
                if(splits.length == 5){
                    try {
                        int applicationCode = Integer.parseInt(splits[0]);
                        int messageId = Integer.parseInt(splits[1]);
                        int packetNumber = Integer.parseInt(splits[2]);
                        int totalNumber = Integer.parseInt(splits[3]);
                        String text = splits[4];
                        if(applicationCode == SMSController.getApplicationCode())
                            packet = new SMSPacket(applicationCode, messageId, packetNumber, totalNumber, text);
                    } catch (NumberFormatException e) {

                    }
                }
                if(packet != null)
                    SMSController.onReceive(packet, number);
            }
        }
    }
}