package com.gruppo4.ringUp.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

    String telephoneNumber;
    View ringButton, locateButton, invalidButton;

    View sendButton;

    enum SelectedCommand {
        INVALID, RING, LOCATE;
    }

    SelectedCommand command;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();
        Log.d("TelephoneNumberCheck", "Telephone number: " + telephoneNumber);


        ringButton = findViewById(R.id.ringButton);
        ringButton.setOnClickListener(this::onClick);
        locateButton = findViewById(R.id.locateButton);
        locateButton.setOnClickListener(this::onClick);
        invalidButton = findViewById(R.id.invalidButton);
        invalidButton.setOnClickListener(this::onClick);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this::onSend);

        Log.d("COMMAND-TEST", "Testing command: " + command);

    }


    /**
     * Listener activated when user presses the "SEND" button.
     * It performs the action, based by the RadioButton choice.
     *
     * @param view The current view. SEND button is expected. It can't be null.
     * @throws IllegalArgumentException if nothing is selected in the RadioButton, or
     *                                  if an unexpected command is selected.
     */
    private void onSend(@NonNull View view) throws IllegalArgumentException {
        if (command == SelectedCommand.RING)
            Log.d("COMMAND-PERFORMED", "RING command is selected.");
        else if (command == SelectedCommand.LOCATE)
            Log.d("COMMAND-PERFORMED", "LOCATE command is selected.");
        else if (command == SelectedCommand.INVALID)
            throw new IllegalArgumentException("Unexpected command is selected");
        else throw new IllegalArgumentException("No command is selected.");

        telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();

        Log.d("TelephoneNumberCheck", "NEW Telephone number: " + telephoneNumber);
    }

    /**
     * Listener activated when a choice is made by the user.
     * The String modifies based by the user's choice.
     *
     * @param view The current view. It can't be null.
     */
    private void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.ringButton: {
                command = SelectedCommand.RING;
            }
            break;
            case R.id.locateButton: {
                command = SelectedCommand.LOCATE;
            }
            break;
            default:
                command = SelectedCommand.INVALID;
        }
        Log.d("SELECTED-COMMAND","Selected command: "+command);
    }


}


