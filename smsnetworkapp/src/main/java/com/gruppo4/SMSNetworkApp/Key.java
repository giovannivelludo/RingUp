package com.gruppo4.SMSNetworkApp;

import androidx.annotation.NonNull;

import com.gruppo4.communication.network.SerializableObject;

public class Key extends SerializableObject
{
    private int key;

    public Key(int k){
        key = k;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Key){
            return ((Key) obj).key == key;
        }
        return false;
    }

    @NonNull
    public String toString(){
        return Integer.toString(key);
    }
}
