package com.timrosu.ea_gui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.timrosu.ea_gui.api.keystore.CryptoManager;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void savePassword(View v) throws GeneralSecurityException, IOException {
        TextView usernameTV = findViewById(R.id.username_input);
        String username = usernameTV.getText().toString();
        TextView passwordTV = findViewById(R.id.password_input);
        String password = passwordTV.getText().toString();

        // cryptomanager test:
        CryptoManager.saveCredentials(this,username,password);

        Toast.makeText(this,"username: "+CryptoManager.getUsername(this),Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this,"username: "+CryptoManager.getPassword(this),Toast.LENGTH_SHORT).show();
    }
}