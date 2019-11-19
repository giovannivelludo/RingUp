package com.gruppo4.sms.packeting;

import com.gruppo4.communication.packeting.Packet;
import com.gruppo4.sms.dataLink.SMSPeer;

/**
 * Represents a single SMS as a part of a bigger message
 */
class SMSPacket extends Packet<String, SMSPeer> {

    public static final String SEPARATOR = "_";
    static final int MAX_PACKET_TEXT_LEN = 140; //160 - 3(applicationCode) - 3(messageId) - 3(packetNumber) - 3(totalNumber) - 4(SEPARATOR)
    private int applicationCode;
    private int messageID;

    /**
     * @param applicationCode    valid application code
     * @param messageID          valid message id
     * @param message            content
     * @param destination        peer of destination or sender
     * @param sequenceNumber     index of the packet in the message packets list
     * @param totalPacketsNumber length of the message packets list
     */
    protected SMSPacket(int applicationCode, int messageID, String message, SMSPeer destination, int sequenceNumber, int totalPacketsNumber) {
        super(message, destination, sequenceNumber, totalPacketsNumber);
        this.applicationCode = applicationCode;
        this.messageID = messageID;
    }

    /**
     * Separates the various parts of the
     * packet with SEPARATORs and returns the chained string
     *
     * @return A String that can be sent in a SMS
     */
    @Override
    public String getOutput() {
        return applicationCode + SEPARATOR + messageID +
                SEPARATOR + sequenceNumber + SEPARATOR + totalPacketsNumber + SEPARATOR + data;
    }

    public int getApplicationCode() {
        return applicationCode;
    }

    public int getMessageID() {
        return messageID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SMSPacket))
            return false;
        SMSPacket packet = (SMSPacket) o;
        if (applicationCode != packet.applicationCode)
            return false;
        if (messageID != packet.messageID)
            return false;
        if (sequenceNumber != packet.sequenceNumber)
            return false;
        if (totalPacketsNumber != packet.totalPacketsNumber)
            return false;
        if (!destination.equals(packet.destination))
            return false;
        return data.equals(packet.data);
    }
}