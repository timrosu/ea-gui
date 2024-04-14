package com.timrosu.ea_gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/** @noinspection deprecation*/
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.grades);
    }
    GradesFragment gradesFragment = new GradesFragment();
    ExamsFragment examsFragment = new ExamsFragment();
    ExemptionsFragment exemptionsFragment = new ExemptionsFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    // glede na izbran gumb da pravi fragment v ospredje
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId==R.id.grades) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, gradesFragment)
                    .commit();
            return true;
        } else if (menuItemId==R.id.exams) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, examsFragment)
                        .commit();
                return true;
        } else if (menuItemId==R.id.exemptions) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, exemptionsFragment)
                    .commit();
            return true;
        } else if (menuItemId==R.id.profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void launchLoginScreen(View v) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}