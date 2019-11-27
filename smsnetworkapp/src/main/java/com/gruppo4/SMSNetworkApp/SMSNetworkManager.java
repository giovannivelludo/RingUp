package com.gruppo4.SMSNetworkApp;

import com.gruppo4.communication.network.SerializableObject;
import com.gruppo4.sms.network.SMSAbstractNetworkManager;

public class SMSNetworkManager extends SMSAbstractNetworkManager
{
    protected SerializableObject getKeyFromString(String key){
        return new Key(Integer.parseInt(key));
    }

    protected SerializableObject getValueFromString(String value){
        return new Value(value);
    }
}
