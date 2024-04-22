package com.timrosu.ea_gui.api.auth;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.timrosu.ea_gui.api.tasks.BearerTask;
import com.timrosu.ea_gui.api.tasks.LoginTask;
import com.timrosu.ea_gui.cache.AuthData;

/**
 * @noinspection deprecation
 */
// razred za pridobivanje avtentikacijske kode (bearer) potrebne za dostop do podatkov posameznega fragmenta
public class Auth {
    private final Context context;

    public Auth(Context context) {
        this.context = context;
        if (AuthData.getBearer() == null) {
            if (AuthData.getCookie() == null) {
                setCookie();
            }
            setBearer();
        }
    }

    private void setCookie() {
        new LoginTask(context).execute();
        waitForCookie();
    }

    private void waitForCookie() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                String status = AuthData.getStatus();
                switch (status) {
                    case "success":
                        break;
                    case "fail":
                        Log.d("setCookieError", AuthData.getMessage());
                        break;
                    default:
                        hand.postDelayed((Runnable) this, 100);
                        break;
                }
            }
        });
    }

    private void setBearer() {
        new BearerTask().execute();
        waitForBearer();
    }

    private void waitForBearer() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (AuthData.getBearer()==null) {
                    hand.postDelayed((Runnable) this, 100);
                }
            }
        });
    }
}
