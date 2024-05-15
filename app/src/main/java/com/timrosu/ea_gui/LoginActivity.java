package com.timrosu.ea_gui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.timrosu.ea_gui.api.Api;
import com.timrosu.ea_gui.api.model.request.LoginRequest;
import com.timrosu.ea_gui.api.tasks.LoginTask;
import com.timrosu.ea_gui.cache.AuthData;

/**
 * @noinspection deprecation
 */
public class LoginActivity extends AppCompatActivity {
    Api api;
    TextView errorTV;

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
        TextView etUsername = findViewById(R.id.etUsername);
        TextView etPassword = findViewById(R.id.etPassword);
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        errorTV = findViewById(R.id.errorTV);

        if (username.isEmpty() || password.isEmpty()) {
            errorTV.setText(getString(R.string.no_credentials_warning));
        } else {
            LoginRequest.setUporabnik(username);
            LoginRequest.setGeslo(password);

           new LoginTask(this).execute();

           waitForResponse();
        }
    }

    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void waitForResponse() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                String status = AuthData.getStatus();
                switch (status) {
                    case "success":
                        sendToast(getString(R.string.login_successful_toast));
                        finish();
                        break;
                    case "fail":
                        errorTV.setText(AuthData.getMessage());
                        sendToast(getString(R.string.login_failed));
                        break;
                    default:
                        hand.postDelayed((Runnable) this, 100);
                        break;
                }
            }
        });
    }
}