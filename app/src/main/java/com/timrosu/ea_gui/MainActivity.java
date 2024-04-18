package com.timrosu.ea_gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timrosu.ea_gui.keystore.CryptoManager;

/** @noinspection deprecation*/
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView; //navigacijska vrstica

    // fragmenti, med katerimi navigira
    GradesFragment gradesFragment = new GradesFragment();
    ExamsFragment examsFragment = new ExamsFragment();
    AbsencesFragment absencesFragment = new AbsencesFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    //ProfileFragment:

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


        if(!CryptoManager.checkCredentials(this)){
            Log.i("credentials","missing");
             startActivity(new Intent(this,LoginActivity.class));
        } else {
            Log.i("credentials","available");
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.grades);
    }

    // glede na izbran gumb da pravi fragment v ospredje
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
                    .replace(R.id.flFragment, absencesFragment)
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
}