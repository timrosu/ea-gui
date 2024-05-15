package com.timrosu.ea_gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.timrosu.ea_gui.api.auth.Auth;
import com.timrosu.ea_gui.api.tasks.ProfileTask;
import com.timrosu.ea_gui.cache.Data;
import com.timrosu.ea_gui.keystore.CryptoManager;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            logout(v.getContext());
            Toast.makeText(v.getContext(), "logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(v.getContext(), LoginActivity.class));
        });
        if (Data.childMap != null && !Data.childMap.isEmpty()) {
            printProfileInfo(view);
        } else {
            new Auth(view.getContext()); //pridobi avtentikacijsko kodo, ce se ni nastavljena
            new ProfileTask().execute(); //pridobi podatke za prikaz
            waitForResponse(view);
        }

    }

    private void waitForResponse(View view) { //caka na odziv in nato nalozi podatke
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.childMap != null && !Data.childMap.isEmpty()) {
                    printProfileInfo(view);
                } else {
                    hand.postDelayed((Runnable) this, 100);
                }
            }
        });
    }

    private void printProfileInfo(View view) { //nalozi podatke v fragment
        if (Objects.equals(Data.childMap.get("gender"), "m")) {
            ((TextView) view.findViewById(R.id.tvWelcome)).setText(String.format("%s, %s!", getString(R.string.welcome_male_label), Data.childMap.get("name")));
        } else {
            ((TextView) view.findViewById(R.id.tvWelcome)).setText(String.format("%s, %s!", getString(R.string.welcome_female_label), Data.childMap.get("name")));
        }
        ((TextView) view.findViewById(R.id.tvAge)).setText(String.format("%s: %s", getString(R.string.age_label), Data.childMap.get("age")));
        ((TextView) view.findViewById(R.id.tvGender)).setText(String.format("%s: %s", getString(R.string.gender_label), Data.childMap.get("gender")));
        ((TextView) view.findViewById(R.id.tvGrade)).setText(String.format("%s: %s", getString(R.string.grade_label), Data.childMap.get("school_year")));
        ((TextView) view.findViewById(R.id.tvId)).setText(String.format("%s: %s", getString(R.string.id_label), Data.childMap.get("id")));
        ((TextView) view.findViewById(R.id.tvType)).setText(String.format("%s: %s", getString(R.string.user_type_label), Data.childMap.get("type")));
    }

    public void logout(Context context) {
        CryptoManager.deleteCredentials(context);
    }
}
