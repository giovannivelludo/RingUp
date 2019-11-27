package com.gruppo4.SMSNetworkApp;

import androidx.annotation.NonNull;

import com.gruppo4.communication.network.SerializableObject;

public class Value extends SerializableObject
{
    private String value;

    public Value(String s){
        value = s;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Value){
            return ((Value) obj).value.equals(value);
        }
        return false;
    }

    @NonNull
    public String toString(){
        return value;
    }
}
