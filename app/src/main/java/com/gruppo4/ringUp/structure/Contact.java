package com.gruppo4.ringUp.structure;

import com.eis.smslibrary.SMSPeer;

/**
 * Defines a name and its address.
 *
 * @author Luca Crema
 * @since 17/01/2020
 */
public class Contact {

    private SMSPeer address;
    private String name;

    public Contact(String name, SMSPeer address) {
        this.address = address;
        this.name = name;
    }

    public SMSPeer getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

}
