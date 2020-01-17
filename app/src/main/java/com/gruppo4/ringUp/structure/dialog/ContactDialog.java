package com.gruppo4.ringUp.structure.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.google.android.material.snackbar.Snackbar;
import com.gruppo4.ringUp.MainActivity;
import com.gruppo4.ringUp.R;
import com.gruppo4.ringUp.structure.Contact;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatDialogFragment;
import it.lucacrema.preferences.PreferencesManager;

public class ContactDialog extends AppCompatDialogFragment {

    View dialogView;
    ArrayList<Contact> knownContacts;

    public ContactDialog(ArrayList<Contact> knownContacts) {
        this.knownContacts = knownContacts;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        dialogView = layoutInflater.inflate(R.layout.add_contact_dialog, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.give_permissions_button_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onDialogPositiveButton();
                            }
                        }
                )
                .setNegativeButton(R.string.set_dialog_button_text,
                        (dialog, whichButton) -> dialog.cancel()
                )
                .setView(dialogView)
                .create();

        alertDialog.setCanceledOnTouchOutside(true);
        return alertDialog;
    }

    /**
     * Callback for click on dialog's positive button. Retrieves data and creates a contact.
     */
    public void onDialogPositiveButton() {
        Log.v("ContactDialog", "1");
        String newContactName = ((EditText) dialogView.findViewById(R.id.new_contact_name)).getText().toString();
        String newContactTelephoneNumber = ((EditText) dialogView.findViewById(R.id.new_contact_phone_number)).getText().toString();
        if (newContactName.equals("")) {
            Snackbar.make(dialogView, R.string.not_valid_contact_name, Snackbar.LENGTH_LONG).show();
            return;
        }
        SMSPeer parsedPeer;
        Log.v("ContactDialog", "2");
        try {
            parsedPeer = new SMSPeer(newContactTelephoneNumber);
        } catch (InvalidTelephoneNumberException e) {
            //TODO: check for every invalid telephone number reason and display a different message
            Snackbar.make(dialogView, "Invalid telephone number", Snackbar.LENGTH_LONG).show();
            return;
        }
        Log.v("ContactDialog", "3");
        Contact newContact = new Contact(newContactName, parsedPeer);
        knownContacts.add(newContact);
        try {
            PreferencesManager.setObject(dialogView.getContext(), MainActivity.CONTACTS_PREFERENCES_ID, knownContacts);
        } catch (IOException e) {
            Log.e("ContactDialog", "Unable to save in memory: " + e.getMessage());
            Snackbar.make(dialogView, "IOException when saving contact", Snackbar.LENGTH_LONG).show();
            return;
        }
    }

}
