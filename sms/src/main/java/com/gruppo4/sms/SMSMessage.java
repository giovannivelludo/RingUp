package com.gruppo4.sms;

public class SMSMessage {

    private String numero = "";
    private String testo = "";

    //costruttore
    public SMSMessage(String numTel, String txt){
        numero = numTel;
        testo = txt;
    }

    //restituisce il numero di telefono
    public String getTelephoneNumber(){
        return numero;
    }

    //restituisce il testo del messaggio
    public String getTextMessage(){
        return testo;
    }
}