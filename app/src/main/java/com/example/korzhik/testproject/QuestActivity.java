package com.example.korzhik.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuestActivity extends AppCompatActivity {
    final static public String KEY_NAME = "KEY_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_activity);
    }
}
