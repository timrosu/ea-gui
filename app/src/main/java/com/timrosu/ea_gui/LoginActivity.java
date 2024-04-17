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
import com.timrosu.ea_gui.api.Api;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {
    Api api;

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
        api = Api.getInstance(this);
    }
    //TODO: popravi to
    public void savePassword(View v) throws GeneralSecurityException, IOException {
        TextView usernameTV = findViewById(R.id.username_input);
        String username = usernameTV.getText().toString();
        TextView passwordTV = findViewById(R.id.password_input);
        String password = passwordTV.getText().toString();

        String message = api.login(username,password);

        if (message.equals("success")){
            Toast.makeText(this, R.string.login_successful_toast,Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }
}