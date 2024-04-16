package com.timrosu.ea_gui.api.keystore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Objects;

/** @noinspection deprecation*/
public class CryptoManager {
    private static String masterKeyAlias = null;
    private static final String CRED_FILE = "credentials";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    public static void saveCredentials(Context context, String username, String password) {
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (Exception e) {
            Log.d("MasterKeys exception", Arrays.toString(e.getStackTrace()));
        }
        try {
            //  initializing our encrypted
            // shared preferences and passing our key to it.
            EncryptedSharedPreferences sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    // passing a file name to share a preferences
                    CRED_FILE,
                    Objects.requireNonNull(masterKeyAlias),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // storing data in shared preferences file.
            SharedPreferences.Editor prefEditor = sharedPreferences.edit();
            prefEditor.putString(KEY_USERNAME, username);
            prefEditor.putString(KEY_PASSWORD, password);
            prefEditor.apply();

        } catch (Exception e) {
            Log.d("EncryptedSharedPreferences exception", Arrays.toString(e.getStackTrace()));
        }
    }
    public static String getUsername(Context context) throws GeneralSecurityException, IOException {
        EncryptedSharedPreferences sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                CRED_FILE,
                Objects.requireNonNull(masterKeyAlias),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public static String getPassword(Context context) throws GeneralSecurityException, IOException {
        EncryptedSharedPreferences sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                CRED_FILE,
                Objects.requireNonNull(masterKeyAlias),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

}