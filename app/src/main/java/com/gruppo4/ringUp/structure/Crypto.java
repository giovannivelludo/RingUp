package com.gruppo4.ringUp.structure;

import android.content.Context;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import it.lucacrema.preferences.PreferencesManager;

/**
 * @author Giovanni Velludo
 */
public class Crypto {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String PREFERENCES_PRIVATE_KEY = "my_private_RSA_key";
    private static final String PREFERENCES_PUBLIC_KEY = "my_public_RSA_key";

    /**
     * Generates a private and public RSA key pair and saves it to memory.
     * @param context the Context of the app component calling this method.
     * @throws NoSuchAlgorithmException if no Provider supports a KeyPairGeneratorSpi implementation for the {@link Crypto#KEY_ALGORITHM}.
     * @throws IOException if an I/O error occurs while writing to disk.
     */
    public static void generateKeyPair(Context context) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        PreferencesManager.setObject(context.getApplicationContext(), PREFERENCES_PUBLIC_KEY, publicKey);
        PreferencesManager.setObject(context.getApplicationContext(), PREFERENCES_PRIVATE_KEY, privateKey);
    }

    static RSAPublicKey getMyPublicKey(Context context) {
        return (RSAPublicKey) PreferencesManager.getObject(context, PREFERENCES_PUBLIC_KEY);
    }
}
