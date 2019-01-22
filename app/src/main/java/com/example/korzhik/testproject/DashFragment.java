package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashFragment extends Fragment {//профиль


    private TextView tvName;
    private TextView tvLvl;
    private TextView tvNumber;
    private TextView iqValue;
    private TextView socialValue;
    private TextView charityValue;
    private TextView homeValue;
    private TextView healthValue;
    private Button btnLogout;

    public String fullname;
    public int common;
    public String username;
    public String token;
    public int  lvl;
    public int number;
    public int iq;
    public int social;
    public int charity;
    public int home;
    public int health;

    int i;

    ImageView chars[] = new ImageView[5];

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(
                        getActivity()
                                .getApplicationContext());

        fullname = preferences.getString("fullname", "unknown");
        username = preferences.getString("username", "unknown");
        token = preferences.getString("token", "unknown");

        lvl = preferences.getInt("lvl", 20);
        number = preferences.getInt("number", 30);
        iq = preferences.getInt("iq", 30);
        social = preferences.getInt("social", 30);
        charity = preferences.getInt("charity", 30);
        home = preferences.getInt("home", 30);
        health = preferences.getInt("health", 30);

        v = inflater.inflate(R.layout.fragment_dash, container, false);

        // Вьюха имени и фамилии, уровень, количество выполненных квестов
        tvName = v.findViewById(R.id.name);
        tvLvl = v.findViewById(R.id.level);
        tvNumber = v.findViewById(R.id.quests_text);
        // характеристики
        chars[0] = v.findViewById(R.id.char_intelligence);
        chars[1] = v.findViewById(R.id.char_social);
        chars[2] = v.findViewById(R.id.char_friendly);
        chars[3] = v.findViewById(R.id.char_housekeeping);
        chars[4] = v.findViewById(R.id.char_health);

        iqValue = v.findViewById(R.id.value_intelligence);
        socialValue = v.findViewById(R.id.value_social);
        charityValue = v.findViewById(R.id.value_friendly);
        homeValue= v.findViewById(R.id.value_housekeeping);
        healthValue = v.findViewById(R.id.value_health);

        tvName.setText(username);
        tvLvl.setText("Уровень: " + Integer.toString(lvl));
        tvNumber.setText("Опыт: " + Integer.toString(number));
        iqValue.setText(Integer.toString(iq));
        socialValue.setText(Integer.toString(social));
        charityValue.setText(Integer.toString(charity));
        homeValue.setText(Integer.toString(home));
        healthValue.setText(Integer.toString(health));

        btnLogout = v.findViewById(R.id.button_logout);



        // Кнопка выхода
        // Если нажата кнопка выхода, то...
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Начинаем запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APILogin apiLogin = retrofit.create(APILogin.class);

                Call<ResponseBody> call = apiLogin.getToken(username, token);

                call.enqueue(new Callback<ResponseBody>() {

                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            // Если ошибки нет, то...
                            final String mMessage = response.body().string();

                            if (mMessage.equals("Success")) {
                                // Открывается SharedPreferences для очищения данных
                                SharedPreferences preferences
                                        = PreferenceManager
                                        .getDefaultSharedPreferences(
                                                getActivity().getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();

                                // После того, как очистили, переводит на окно авторизации
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            } else {
                                Snackbar.make(DashFragment.this.v, "Проверьте подключение к интернету",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Что произойдет в случае неудачного исхода
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Snackbar.make(DashFragment.this.v,
                                "Что-то пошло не так... Попробуйте снова...",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        for (i = 0; i < 5; ++i) {
            chars[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.char_intelligence:
                            Snackbar.make(DashFragment.this.v,
                                    "Интеллект",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_social:
                            Snackbar.make(DashFragment.this.v,
                                    "Социальные навыки",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_friendly:
                            Snackbar.make(DashFragment.this.v,
                                    "Дружелюбие",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_housekeeping:
                            Snackbar.make(DashFragment.this.v,
                                    "Домоводство",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_health:
                            Snackbar.make(DashFragment.this.v,
                                    "Физическое развитие",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        return v;
    }
}