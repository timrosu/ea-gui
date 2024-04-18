package com.timrosu.ea_gui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.timrosu.ea_gui.api.Api;
import com.timrosu.ea_gui.api.callback.LoginCallback;

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

            api.setLogin(username, password, new LoginCallback() {
                @Override
                public void onLoginSuccess(int code) {
                    // Handle successful login
                    Log.i("LoginSuccess", "Login successful with code: " + code);
                    sendToast(getString(R.string.login_successful_toast));
                    finish();
                }

                @Override
                public void onLoginFailure(Throwable throwable) {
                    // Handle login failure
                    Log.e("LoginError", "Login failed", throwable);
                    errorTV.setText(R.string.login_failed);
                }
            });


        }

    }

    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}