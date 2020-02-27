package com.gruppo4.ringUp.structure;

import android.content.Context;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import it.lucacrema.preferences.PreferencesManager;

/**
 * Class used to control the saving of a password
 *
 * @author Alberto Ursino
 * @author Giovanni Velludo
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
     * @param context  Context of the app component requesting to set the password.
     * @param password password that needs to be saved to disk
     */
    public static void setPassword(@NonNull Context context, @NonNull String password) {
        byte[] passwordHash = null;
        byte[] salt = getSalt();
        try {
            MessageDigest md = MessageDigest.getInstance(HASHING_ALG);
            md.update(password.getBytes());
            md.update(salt);
            passwordHash = md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        String base64passwordHash;
        if (passwordHash == null) base64passwordHash = PreferencesManager.DEFAULT_STRING_RETURN;
        else base64passwordHash = Base64.getEncoder().encodeToString(passwordHash);
        String base64salt = Base64.getEncoder().encodeToString(salt);
        PreferencesManager.setString(context, PREFERENCES_PASSWORD_KEY, base64passwordHash);
        PreferencesManager.setString(context, PREFERENCES_SALT_KEY, base64salt);
    }

    /**
     * Checks if there's a password saved in memory
     *
     * @param context Context of the app component calling this method.
     * @return true if is it present, false otherwise
     */
    public static boolean isPassSaved(@NonNull Context context) {
        return !(PreferencesManager.getString(context, PREFERENCES_PASSWORD_KEY).equals(PreferencesManager.DEFAULT_STRING_RETURN));
    }

    /**
     * Deletes the password saved in memory
     *
     * @param context Context of the app component requesting deletion of the password.
     */
    static void deletePassword(@NonNull Context context) {
        PreferencesManager.removeValue(context, PREFERENCES_PASSWORD_KEY);
        PreferencesManager.removeValue(context, PREFERENCES_SALT_KEY);
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

    /**
     * Checks if the password passed as argument and the one saved in memory are the same.
     *
     * @param context  Context of the app component requesting to check the password.
     * @param password a non null string
     * @return true if the password is correct, false otherwise
     */
    static boolean checkPassword(@NonNull Context context, @NonNull String password) {
        byte[] passwordHash = null;
        String base64salt = PreferencesManager.getString(context, PREFERENCES_SALT_KEY);
        byte[] salt = Base64.getDecoder().decode(base64salt);
        try {
            MessageDigest md = MessageDigest.getInstance(HASHING_ALG);
            md.update(password.getBytes());
            md.update(salt);
            passwordHash = md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        String actualBase64passwordHash = PreferencesManager.getString(context,
                PREFERENCES_PASSWORD_KEY);
        byte[] actualPasswordHash = Base64.getDecoder().decode(actualBase64passwordHash);
        return Arrays.equals(passwordHash, actualPasswordHash);
    }

}
