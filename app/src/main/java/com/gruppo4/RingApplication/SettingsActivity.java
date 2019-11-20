package com.gruppo4.RingApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gruppo4.RingApplication.ringCommands.PasswordManager;
import com.gruppo4.RingApplication.ringCommands.dialog.PasswordDialog;
import com.gruppo4.RingApplication.ringCommands.dialog.PasswordDialogListener;
import com.gruppo4.RingApplication.ringCommands.exceptions.IllegalCommandException;
import com.gruppo4.sms.dataLink.SMSHandler;
import static com.gruppo4.RingApplication.MainActivity.CHANGE_PASS_COMMAND;

/**
 * @author Alberto Ursino
 */
public class SettingsActivity extends AppCompatActivity implements PasswordDialogListener {

    private static final int WAIT_TIME = 2000;
    private static final int PERMISSION_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.actionBar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Context context = getApplicationContext();
        final FloatingActionButton CHANGE_PASSWORD_BUTTON = findViewById(R.id.change_password);
        final RadioButton RINGTONE_RADIO_BUTTON = findViewById(R.id.ringtone_mode);

        RINGTONE_RADIO_BUTTON.toggle();

        //Button used to open a dialog where the user can change the password
        CHANGE_PASSWORD_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    @Override
    public void applyText(String password, Context context) {
        new PasswordManager(context).setPassword(password);
        Toast.makeText(getApplicationContext(), "Password saved", Toast.LENGTH_SHORT).show();
        waitForPermissions(WAIT_TIME);
    }

    /**
     * Creates the dialog used to insert a valid password or exit/abort
     *
     * @throws IllegalCommandException
     */
    void openDialog() {
        if (PasswordDialog.isCommandChangePass(CHANGE_PASS_COMMAND)) {
            PasswordDialog passwordDialog = new PasswordDialog(CHANGE_PASS_COMMAND);
            passwordDialog.show(getSupportFragmentManager(), "Change Password");
        } else {
            throw new IllegalCommandException();
        }
    }

    /**
     * @param time to wait before checking permits
     */
    void waitForPermissions(int time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission(getApplicationContext());
            }
        }, time);
    }

    /**
     * Simple method used to check permissions
     *
     * @param context of the application
     */
    void checkPermission(Context context) {
        if (!SMSHandler.checkReceivePermission(context))
            requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, PERMISSION_CODE);
    }
}
