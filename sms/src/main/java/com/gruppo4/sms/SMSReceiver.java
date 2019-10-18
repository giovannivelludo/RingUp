package com.gruppo4.sms;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.gruppo4.sms.exceptions.InvalidSMSMessageException;
import com.gruppo4.sms.exceptions.InvalidTelephoneNumberException;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReciever";
	private static final String PDU_TYPE = "pdus";

	/**
	 * This method is subscribed to the intent of a message received, and will be called whenever a new message is received.
	 * @param context
	 * @param intent
	 */
	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public void onReceive(Context context, Intent intent) {
		// Get the SMS message.
		Bundle bundle = intent.getExtras();
		android.telephony.SmsMessage[] messages;
		String format = bundle.getString("format");
		// Retrieve the SMS message received.
		Object[] pdus = (Object[]) bundle.get(PDU_TYPE);
		if (pdus != null) {
			// Check the Android version.
			boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
			// Fill the messages array.
			messages = new android.telephony.SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				// Check Android version and use appropriate createFromPdu.
				if (isVersionM) {
					// If Android version M or newer:
					messages[i] = android.telephony.SmsMessage.createFromPdu((byte[]) pdus[i], format);
				} else {
					// If Android version L or older:
					messages[i] = android.telephony.SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				try {
				    //Create a library message class instance.
					SMSMessage receivedMessage = new SMSMessage(messages[i].getOriginatingAddress(), messages[i].getMessageBody());
					//Call every listener subscribed to this event.
					SMSController.onReceive(receivedMessage);
				}catch(InvalidTelephoneNumberException telephoneException){
					//Should NEVER happened but we must catch the exception.
					Log.e(TAG,telephoneException.getMessage());
				}catch(InvalidSMSMessageException messageException){
					//Should NEVER happened too but we must catch the exception.
					Log.e(TAG,messageException.getMessage());
				}
			}
		}
	}

}
