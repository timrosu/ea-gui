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
        api = new Api(this);
    }

    public void getInputs(View v) {
//        LoginResponse loginResponse = new LoginResponse();

        TextView etUsername = findViewById(R.id.etUsername);
        String username = etUsername.getText().toString();
        TextView etPassword = findViewById(R.id.etPassword);
        String password = etPassword.getText().toString();

        TextView errorTV = findViewById(R.id.errorTV);

        if (username.isEmpty() && password.isEmpty()) {
            errorTV.setText(getString(R.string.no_credentials_warning));
        } else {
            sendToast(String.valueOf(api.setLogin(username, password)));
//            Log.i("grades", api.getChild().toString());
            finish();
        }

    }

    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}