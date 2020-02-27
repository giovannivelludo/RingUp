package com.gruppo4.ringUp.structure;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import it.lucacrema.preferences.PreferencesManager;

/**
 * Class used to control the saving of a password
 *
 * @author Alberto Ursino
 */
public class PasswordManager {

    /**
     * Through this key we can access the memory location where we store the password
     */
    static final String PREFERENCES_PASSWORD_KEY = "secret_password";
    private static final String PREFERENCES_SALT_KEY = "secret_salt";
    private static final String HASHING_ALG = "SHA-256";
    private static final int SALT_SIZE_BYTES = 32;

    /**
     * @return the password saved in memory
     */
    static String getPassword(Context context) {
        return PreferencesManager.getString(context, PREFERENCES_PASSWORD_KEY);
    }

    /**
     * @param password password that needs to be saved to disk
     */
    public static void setPassword(Context context, String password) {
        byte[] passwordHash = null;
        byte[] salt = getSalt();
        try {
            MessageDigest md = MessageDigest.getInstance(HASHING_ALG);
            md.update(password.getBytes());
            md.update(salt);
            passwordHash = md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        String base64PasswordHash;
        if (passwordHash == null) base64PasswordHash = PreferencesManager.DEFAULT_STRING_RETURN;
        else base64PasswordHash = Base64.getEncoder().encodeToString(passwordHash);
        String base64salt = Base64.getEncoder().encodeToString(salt);
        PreferencesManager.setString(context, PREFERENCES_PASSWORD_KEY, base64PasswordHash);
        PreferencesManager.setString(context, PREFERENCES_SALT_KEY, base64salt);
    }

    /**
     * Checks if there's a password saved in memory
     *
     * @return true if is it present, false otherwise
     */
    public static boolean isPassSaved(Context context) {
        return !(PreferencesManager.getString(context, PREFERENCES_PASSWORD_KEY).equals(PreferencesManager.DEFAULT_STRING_RETURN));
    }

    /**
     * Deletes the password saved in memory
     */
    static void deletePassword(Context context) {
        PreferencesManager.removeValue(context, PREFERENCES_PASSWORD_KEY);
    }

    /**
     * @return a random salt of size SALT_SIZE_BYTES.
     */
    private static byte[] getSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE_BYTES];
        random.nextBytes(salt);
        return salt;
    }

}
