package com.example.korzhik.testproject;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestActivity extends AppCompatActivity {

    private TextView name;
    private TextView shortInfo;
    private TextView fullInfo;
    private ImageView questPicture;
    private CoordinatorLayout view;
    private QuestCardRepository questCardRepository;
    private Application application;
    private Bundle extras;
    private android.support.v7.widget.Toolbar toolbar;
    String login;

    private Button acceptButton;
    private Button passButton;

    private boolean accepted = false;
    private boolean passed = false;
    private int id;
    private String title;
    private String shortDescr;
    private String fullDescr;
    private SharedPreferences preferences;

    public String username;
    public String token;

    final static public String KEY_NAME = "KEY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        toolbar = findViewById(R.id.main_toolbar);
        application = getApplication();
        shortInfo = findViewById(R.id.quest_short_description);
        fullInfo = findViewById(R.id.quest_full_description);
        questPicture = findViewById(R.id.main_backdrop);
        acceptButton = findViewById(R.id.accept_button);
        passButton = findViewById(R.id.pass_button);
        view = findViewById(R.id.main_container);

        questCardRepository = new QuestCardRepository(application);

        LiveData<List<QuestCard>> liveData = questCardRepository.getAllQuestCards();


        liveData.observe(QuestActivity.this, new Observer<List<QuestCard>>() {
            @Override
            public void onChanged(@Nullable List<QuestCard> questCards) {
                switch (getIntent().getStringExtra("idtp")) {
                    case "1":
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle(questCards.get(0).getName());
                        shortInfo.setText(questCards.get(0).getShort_info());
                        fullInfo.setText(questCards.get(0).getFull_info());
                        questPicture.setImageResource(R.drawable.iqfull);
                        id = questCards.get(0).getId();

                        break;

                    case "2":
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle(questCards.get(1).getName());
                        shortInfo.setText(questCards.get(1).getShort_info());
                        fullInfo.setText(questCards.get(1).getFull_info());
                        questPicture.setBackgroundResource(R.drawable.socialfull);
                        id = questCards.get(1).getId();

                        break;

                    case "3":
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle(questCards.get(2).getName());
                        shortInfo.setText(questCards.get(2).getShort_info());
                        fullInfo.setText(questCards.get(2).getFull_info());
                        questPicture.setBackgroundResource(R.drawable.charityfull);
                        id = questCards.get(2).getId();

                        break;

                    case "4":
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle(questCards.get(3).getName());
                        shortInfo.setText(questCards.get(3).getShort_info());
                        fullInfo.setText(questCards.get(3).getFull_info());
                        questPicture.setBackgroundResource(R.drawable.housekeepfull);
                        id = questCards.get(3).getId();

                        break;

                    case "5":
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle(questCards.get(4).getName());
                        shortInfo.setText(questCards.get(4).getShort_info());
                        fullInfo.setText(questCards.get(4).getFull_info());
                        questPicture.setBackgroundResource(R.drawable.sportsfull);
                        id = questCards.get(4).getId();

                        break;
                }
            }
        });


        passButton.setClickable(false);
        passButton.setEnabled(false);

        // Если нажата кнопка "Взять квест", то...
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accepted = true;

                // Достаем Никнейм и Токен Из SharedPreferences для GET запроса на сервак
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(QuestActivity.this);
                username = preferences.getString("username", "unknown");
                token = preferences.getString("taskToken", "unknown");

                // Начинаем запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ResponseBody> call = apiService.acceptTask(username, id);

                // Результаты запроса
                call.enqueue(new Callback<ResponseBody>() {
                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            // Сохраняем токен квеста в SharedPreferences
                            final String mMessage = response.body().string();
                            SharedPreferences preferences = PreferenceManager
                                    .getDefaultSharedPreferences(
                                            QuestActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("taskToken", mMessage);
                            editor.apply();

                            // Делаем кнопку "Взять квест" неактивной
                            acceptButton.setEnabled(false);
                            acceptButton.setClickable(false);
                            /*acceptButton.setBackgroundColor(ContextCompat
                                    .getColor(QuestActivity.this,
                                            R.color.accept_button_inactive));*/
                            acceptButton.setVisibility(View.GONE);

                            // Делаем кнопку "Сдать квест" активной
                            passButton.setEnabled(true);
                            passButton.setClickable(true);
                            /*passButton.setBackgroundColor(ContextCompat
                                    .getColor(QuestActivity.this,
                                            R.color.pass_button_active));*/

                            // Выводим сообщение об удачном взятии задания
                            Snackbar.make(view, "Задание взято!",
                                    Snackbar.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Что произойдет в случае неудачного исхода
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Snackbar.make(view, "Проверьте подключение к интернету",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passed = true;

                // Достаем Никнейм и Токен Из SharedPreferences для GET запроса на сервак
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(QuestActivity.this);
                username = preferences.getString("username", "unknown");
                token = preferences.getString("taskToken", "unknown");

                // Начинаем запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ResponseBody> call = apiService.passTask(username, token);

                // Результаты запроса
                call.enqueue(new Callback<ResponseBody>() {
                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            final String mMessage = response.body().string();

                            // Делаем кнопку "Сдать квест" неактивной
                            passButton.setEnabled(false);
                            passButton.setClickable(false);
                            passButton.setBackgroundColor(ContextCompat
                                    .getColor(QuestActivity.this,
                                            R.color.colorPrimaryDark));
                            passButton.setTextColor( ContextCompat
                                    .getColor(QuestActivity.this,
                                            R.color.colorTextHint));

                            // Выводим сообщение об удачном выполнении задания
                            Snackbar.make(view, "Задание выполнено!",
                                    Snackbar.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Что произойдет в случае неудачного исхода
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Показываем Тост с просьбой попробовать снова
                        Snackbar.make(view, "Проверьте подключение к интернету",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
