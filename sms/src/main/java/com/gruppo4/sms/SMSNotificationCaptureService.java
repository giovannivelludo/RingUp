package com.gruppo4.sms;

import android.app.Notification;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class SMSNotificationCaptureService extends NotificationListenerService {

    //Cancel statusBarNotification
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onNotificationPosted(StatusBarNotification statusBarNotification){
        cancelNotification(statusBarNotification.getKey());
        Log.e("1", "ciaoooooo");
    }

}
