package com.gruppo4.ringUp.structure.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gruppo4.ringUp.R;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ContactDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.password_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    }

}
