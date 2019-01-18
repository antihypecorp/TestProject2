package com.example.korzhik.testproject;


import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestActivity extends AppCompatActivity {

    private TextView name;
    private TextView shortInfo;
    private TextView fullInfo;
    private ConstraintLayout view;

    private Button acceptButton;
    private Button passButton;

    private boolean accepted = false;
    private boolean passed = false;

    final static public String KEY_NAME = "KEY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);


        //name = findViewById(R.id.quest_name_large);
        //shortInfo = findViewById(R.id.quest_description_short_large);
        //fullInfo = findViewById(R.id.quest_description_long_large);
        //view = findViewById(R.id.great);


        // ПОЛУЧЕНИЕ КВЕСТОВ С СЕРВЕРА
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.HOST)
                .addConverterFactory(GsonConverterFactory
                        .create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<QuestCard>> call = apiService.getQuestCard();

        acceptButton = findViewById(R.id.accept_button);
        passButton = findViewById(R.id.pass_button);
        Log.d("MyLog", "ОК");

        // отправка запроса
        call.enqueue(new Callback<List<QuestCard>>() {
            @Override
            public void onResponse(Call<List<QuestCard>> call,
                                   Response<List<QuestCard>> response) {
                List<QuestCard> questCard = response.body();
                Log.d("MyLog", "ЗАПРОС ПОЛУЧЕН");
                // ВРЕМЕННО: листы с полученными с сервера значениями имен и описаний
                ArrayList<String> showNames = new ArrayList<String>();
                ArrayList<String> showShortInfos = new ArrayList<String>();
                ArrayList<String> showFullInfos = new ArrayList<String>();
                // пушинг в листы
                for (QuestCard qcForeach : questCard) {
                    showNames.add(qcForeach.getName());
                    showShortInfos.add(qcForeach.getShort_info());
                    showFullInfos.add(qcForeach.getFull_info());
                }
                //name.setText(showNames.get(0));
                //shortInfo.setText(showShortInfos.get(0));
                //fullInfo.setText(showFullInfos.get(0));
            }
            @Override
            public void onFailure(Call<List<QuestCard>> call, Throwable t) {
                Log.d("MyLog", "ЗАПРОС НЕ ПОЛУЧЕН");
            }
        });

        passButton.setClickable(false);
        passButton.setEnabled(false);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accepted = true;

                acceptButton.setEnabled(false);
                acceptButton.setClickable(false);
                acceptButton.setBackgroundColor(ContextCompat
                        .getColor(QuestActivity.this,
                                R.color.accept_button_inactive));

                passButton.setEnabled(true);
                passButton.setClickable(true);
                passButton.setBackgroundColor(ContextCompat
                        .getColor(QuestActivity.this,
                                R.color.pass_button_active));

                Snackbar.make(view, "Задание взято!",
                        Snackbar.LENGTH_SHORT).show();

            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passed = true;


                passButton.setEnabled(false);
                passButton.setClickable(false);
                passButton.setBackgroundColor(ContextCompat
                        .getColor(QuestActivity.this,
                                R.color.pass_button_inactive));

                Snackbar.make(view, "Задание выполнено!",
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
