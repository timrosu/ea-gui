package com.timrosu.ea_gui.keystore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/** @noinspection deprecation*/
// razred za zapisovanje, brisanje in pridobivanje prijavnih podatkov
public class CryptoManager {
    private static final String CRED_FILE = "credentials";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static String getMasterKeyAlias(){
        try {
            return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (Exception e) {
            Log.d("MasterKeys exception", Arrays.toString(e.getStackTrace()));
        }
        return "";
    }

    //sprejme uporabnisko ime in geslo in jih kriptirane shrani v datoteko v mapi programa na /data/data/com.timrosu.ea_gui/...credentials.xml
    public static void saveCredentials(Context context, String username, String password) {
        try {
            //  initializing our encrypted
            // shared preferences and passing our key to it.
            EncryptedSharedPreferences sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    // passing a file name to share a preferences
                    CRED_FILE,
                    getMasterKeyAlias(),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // storing data in shared preferences file.
            SharedPreferences.Editor prefEditor = sharedPreferences.edit();
            prefEditor.putString(KEY_USERNAME, username);
            prefEditor.putString(KEY_PASSWORD, password);
            prefEditor.apply();
            Log.i("CredentialsSaved", "true");

        } catch (Exception e) {
            Log.i("CredentialsSaved", "false");
            Log.d("EncryptedSharedPreferences exception", Arrays.toString(e.getStackTrace()));
        }
    }
    //izbrise vse "nastavitve" iz datoteke
    public static void deleteCredentials(Context context) {
        EncryptedSharedPreferences sharedPreferences;
        try {
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    CRED_FILE,
                    getMasterKeyAlias(),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            Log.i("CredentialsDeleted", "true");
        } catch (GeneralSecurityException | IOException e) {
            Log.i("CredentialsDeleted", "false");
            throw new RuntimeException(e);
        }
        sharedPreferences.edit().clear().apply();

    }
    //vrne vrednost "nastavitve", ki vsebuje uporabnisko ime
    public static String getUsername(Context context) {
        EncryptedSharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    CRED_FILE,
                    getMasterKeyAlias(),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    //vrne vrednost "nastavitve", ki vsebuje geslo
    public static String getPassword(Context context) {
        EncryptedSharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    CRED_FILE,
                    getMasterKeyAlias(),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public static boolean checkCredentials(Context context) {
        EncryptedSharedPreferences sharedPreferences;
        try {
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    CRED_FILE,
                    getMasterKeyAlias(),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        return sharedPreferences.contains("username") && sharedPreferences.contains("password");

    }

}