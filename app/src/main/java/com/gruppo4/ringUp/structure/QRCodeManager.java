package com.gruppo4.ringUp.structure;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Base64;

import net.glxn.qrgen.android.QRCode;

/**
 * @author Giovanni Velludo
 */
public class QRCodeManager {

    public static Bitmap getPublicKeyQRCode(Context context) {
        byte[] publicKey = Crypto.getMyPublicKey(context).getEncoded();
        String base64key = Base64.getEncoder().encodeToString(publicKey);
        return QRCode.from(base64key).withSize(1080, 1080).bitmap();
    }
}
