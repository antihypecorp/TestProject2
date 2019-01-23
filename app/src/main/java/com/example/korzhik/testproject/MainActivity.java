package com.example.korzhik.testproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_NAME = "KEY_NAME";
    String t;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        //листенер навигации
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_top:
                    selectedFragment = new NotifFragment();
                    break;
                case R.id.navigation_tasks:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new DashFragment();
                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        navigation.setSelectedItemId(R.id.navigation_tasks);



    }

    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(), "Exit dialog");

    }

}



























