package com.gruppo4.ringUp.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gruppo4.ringUp.R;

/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * Main Activity. It takes UI commands as input, and, as an output, it gives them a certain action.
 * Its current scope is just to send SMS, based by what does the User decide to do.
 * @since 24/02/2020
 */
public class MainActivity extends AppCompatActivity {

    View ringButton;
    View locateButton;
    View lockButton;

    View sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        String telephoneNumber = findViewById(R.id.telephoneNumber).toString();
        Log.d("TelephoneNumberCheck", "Telephone number: " + telephoneNumber);


        ringButton = findViewById(R.id.ringButton);
        locateButton = findViewById(R.id.locateButton);
        lockButton = findViewById(R.id.lockButton);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this::onSend);


    }

    /**
     * Listener activated when user presses the "SEND" button.
     * It performs the action, based by the RadioButton choice.
     *
     * @param view The current view.
     * @throws IllegalArgumentException if nothing is selected in the RadioButton.
     */
    public void onSend(View view) throws IllegalArgumentException {
        if (ringButton.isSelected())
            Log.d("RingButton", "RING button is selected.");
        else if (locateButton.isSelected())
            Log.d("LocateButton", "Locate button is selected.");
        else if (lockButton.isSelected())
            Log.d("LockButton", "LOCK button is selected.");
        else throw new IllegalArgumentException("Nothing is selected.");
    }


}


