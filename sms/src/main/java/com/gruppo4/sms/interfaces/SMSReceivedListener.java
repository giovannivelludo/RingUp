package com.gruppo4.sms.interfaces;

import com.gruppo4.sms.SMSMessage;

public interface SMSReceivedListener {
    public void onMessageReceived(SMSMessage message);
}
