package com.gruppo4.ringUp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gruppo4.ringUp.R;
import com.gruppo4.ringUp.structure.QRCodeManager;

public class QRCodeDialog extends AppCompatActivity {

    private ImageView mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_dialog);

        mDialog = (ImageView) findViewById(R.id.your_image);
        mDialog.setImageBitmap(QRCodeManager.getPublicKeyQRCode(this));
        mDialog.setClickable(true);

        //finish the activity (dismiss the image dialog) if the user clicks
        //anywhere on the image
        mDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
