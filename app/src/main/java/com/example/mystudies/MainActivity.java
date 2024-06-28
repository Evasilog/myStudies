package com.example.mystudies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            if (fragment == null) {
                bottomNavigationView.setSelectedItemId(R.id.home);
                replaceFragment(new HomeFragment());
            } else if (fragment.equals("tasks")) {
                bottomNavigationView.setSelectedItemId(R.id.tasks);
                replaceFragment(new TasksFragment());
            } else if (fragment.equals("instructors")) {
                bottomNavigationView.setSelectedItemId(R.id.instructors);
                replaceFragment(new InstructorsFragment());
            } else if (fragment.equals("notes")) {
                bottomNavigationView.setSelectedItemId(R.id.notes);
                replaceFragment(new NotesFragment());
            }
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.tasks) {
                replaceFragment(new TasksFragment());
            } else if (id == R.id.instructors) {
                replaceFragment(new InstructorsFragment());
            } else if (id == R.id.notes) {
                replaceFragment(new NotesFragment());
            }
            return true;
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // check and apply theme
        String theme = sharedPreferences.getString("preference_theme", "default");
        switch (theme) {
            case "default":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }

        String snackBar = intent.getStringExtra("snackBar");
        if (snackBar != null) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout, snackBar, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setTranslationY(-424);
            snackbar.show();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    public void openSettings(View view) {
        startActivityForResult(new Intent(this, SettingsActivity.class), 1);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            int id = bottomNavigationView.getSelectedItemId();
            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.tasks) {
                replaceFragment(new TasksFragment());
            } else if (id == R.id.instructors) {
                replaceFragment(new InstructorsFragment());
            } else if (id == R.id.notes) {
                replaceFragment(new NotesFragment());
            }
        }
    }
}