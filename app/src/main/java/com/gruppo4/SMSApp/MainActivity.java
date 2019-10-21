package com.gruppo4.SMSApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.gruppo4.sms.SMSController;
import com.gruppo4.sms.SMSMessage;
import com.gruppo4.sms.SMSReceivedMessage;
import com.gruppo4.sms.exceptions.InvalidSMSMessageException;
import com.gruppo4.sms.exceptions.InvalidTelephoneNumberException;
import com.gruppo4.sms.listeners.SMSRecieveListener;
import com.gruppo4.sms.listeners.SMSSentListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SMSRecieveListener, SMSSentListener {

    SMSController smsController;

    private RecyclerView recyclerView;
    private SmileAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Richiediamo il permesso di leggere i messaggi
        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);

        ArrayList<String> smiles = new ArrayList<>();
        smiles.add("+393467965447 sent you a smile :)");

        recyclerView = findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new SmileAdapter(smiles);
        recyclerView.setAdapter(mAdapter);

        smsController = new SMSController(123);

        smsController.addOnReceiveListener(this);

        Button sendSmileButton = findViewById(R.id.sendSmileButton);
        sendSmileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendSmileButton();
            }
        });
    }

    public void onSendSmileButton(){
        String phoneNumber = ((AutoCompleteTextView)findViewById(R.id.phoneNumberTextView)).getText().toString();
        try {
            SMSMessage message = new SMSMessage(phoneNumber, "Smile! :)",1);
            smsController.sendMessage(this, message, this);
        }catch(InvalidSMSMessageException messageException){
            Log.e("MainActivity",messageException.getMessage());
        }catch(InvalidTelephoneNumberException telephoneException){
            String error = "";
            switch (telephoneException.getState()) {
                case TELEPHONE_NUMBER_TOO_SHORT:
                    error = "Telephone number is too short!";
                    break;
                case TELEPHONE_NUMBER_TOO_LONG:
                    error="Telephone number is too long!";
                    break;
                case TELEPHONE_NUMBER_NO_COUNTRY_CODE:
                    error = "You have to insert the country code";
                    break;
                case TELEPHONE_NUMBER_NOT_A_NUMBER:
                    error = "The telephone number is not valid";
                    break;
            }
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            Log.e("MainActivity",telephoneException.getMessage());
        }
    }

    @Override
    public void onSMSRecieve(SMSReceivedMessage message) {
        if(message.getMessageCode() == 1) {
            Log.d("MainActivity", "Received message:" + message.getMessage());
            Toast.makeText(this, message.getTelephoneNumber() + " sent you a smile :)", Toast.LENGTH_LONG).show();
            mAdapter.getSmiles().add(message.getTelephoneNumber() + " sent you a smile :)");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSMSSent(SMSMessage message) {
        Log.d("MainActivity","Message sent");
        switch (message.getSentState()){
            case MESSAGE_SENT:
                Toast.makeText(this, "Smile sent :)", Toast.LENGTH_SHORT).show();
                mAdapter.getSmiles().add("You sent a smile");
                mAdapter.notifyDataSetChanged();
                break;
            default:
                Log.w("MainActivity","Unable to send sms, reason: "+message.getSentState());
                Toast.makeText(this, "Unable to send smile :(", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
