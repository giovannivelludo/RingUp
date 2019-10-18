package com.gruppo4.sms;

import  com.gruppo4.sms.exceptions.*;

public class SMSMessage {

    private String telephoneNumber;
    private String messageText;

    public static final int MAX_MESSAGE_LENGTH = 160;
    public static final int MAX_TELEPHONE_NUMBER_LENGTH = 20;
    public static final int MIN_TELEPHONE_NUMBER_LENGTH = 7;

    /**
     * A set of states for the message validity tests
     */
    public enum MessageTextState{
        MESSAGE_TEXT_VALID,
        MESSAGE_TEXT_TOO_LONG
    }

    /**
     * A set of states for the phone number validity tests
     */
    public enum TelephoneNumberState {
        TELEPHONE_NUMBER_VALID,
        TELEPHONE_NUMBER_TOO_SHORT,
        TELEPHONE_NUMBER_TOO_LONG,
        TELEPHONE_NUMBER_NO_COUNTRY_CODE
    }

    public SMSMessage(String telephoneNumber, String messageText) throws InvalidSMSMessageException, InvalidTelephoneNumberException{

        TelephoneNumberState telephoneNumberState = checkTelephoneNumber(telephoneNumber);
        if(telephoneNumberState == TelephoneNumberState.TELEPHONE_NUMBER_VALID)
            this.telephoneNumber = telephoneNumber;
        else
            throw new InvalidTelephoneNumberException("Telephone number invalid, reason: " + telephoneNumberState);

        MessageTextState messageTextState = checkMessageText(messageText);
        if(messageTextState == MessageTextState.MESSAGE_TEXT_VALID)
            this.messageText = messageText;
        else
            throw new InvalidSMSMessageException("The is invalid, reason: " + messageTextState);
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getMessageText() {
        return messageText;
    }

    /**
     *  Checks if the message is valid
     * @param messageText the text to check
     * @return The state of the message after the tests
     */
    public static MessageTextState checkMessageText(String messageText) {
        if(messageText.length() > MAX_MESSAGE_LENGTH){
           return MessageTextState.MESSAGE_TEXT_TOO_LONG;
        }
        return MessageTextState.MESSAGE_TEXT_VALID;
    }

    /**
     * Checks if the phone number is valid
     * @param telephoneNumber the phone number to check
     * @return The state of the telephone number after the tests
     */
    public static TelephoneNumberState checkTelephoneNumber(String telephoneNumber){
        //check if there is a country code
        if(!telephoneNumber.substring(0,1).equals("+")){
            return TelephoneNumberState.TELEPHONE_NUMBER_NO_COUNTRY_CODE;
        }
        //Check if the number is shorter than the MAX
        if(telephoneNumber.length() > MAX_TELEPHONE_NUMBER_LENGTH){
            return TelephoneNumberState.TELEPHONE_NUMBER_TOO_LONG;
        }
        //Check if the number is longer than the MIN
        if(telephoneNumber.length() < MIN_TELEPHONE_NUMBER_LENGTH){
            return TelephoneNumberState.TELEPHONE_NUMBER_TOO_SHORT;
        }
        //If we passed all the tests we are sure the number is valid
        return TelephoneNumberState.TELEPHONE_NUMBER_VALID;
    }

}
