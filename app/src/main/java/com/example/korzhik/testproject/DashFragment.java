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
    private TextView tvCommon;
    private Button btnLogout;

    public String name;
    public String surname;
    public int lvl;
    public int common;
    public String username;
    public String token;

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

        name = preferences.getString("name", "unknown");
        surname = preferences.getString("surname", "unknown");
        username = preferences.getString("username", "unknown");
        token = preferences.getString("token", "unknown");
        v = inflater.inflate(R.layout.fragment_dash, container, false);
        chars[0] = v.findViewById(R.id.char_intelligence);
        chars[1] = v.findViewById(R.id.char_social);
        chars[2] = v.findViewById(R.id.char_friendly);
        chars[3] = v.findViewById(R.id.char_housekeeping);
        chars[4] = v.findViewById(R.id.char_health);

        btnLogout = v.findViewById(R.id.button_logout);

        // Вьюха имени и фамилии
        tvName = v.findViewById(R.id.name);

        // Начинаем запрос
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILogin.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APILogin apiLogin = retrofit.create(APILogin.class);

        Call<List<UserLogin>> call = apiLogin.getUserInfo(username);

        call.enqueue(new Callback<List<UserLogin>>() {
            // Что произойдет в случае удачного исхода
            @Override
            public void onResponse(Call<List<UserLogin>> call,
                                   Response<List<UserLogin>> response) {
                List<UserLogin> posts = response.body();

                // Цикл, выводящий данные на Вьюкшку
                for (UserLogin userLogin : posts) {
                    String name = "";

                    name += userLogin.getName() + " " + userLogin.getSurname();

                    tvName.setText(name);
                }
            }

            // Что произойдет в случае неудачного исхода
            @Override
            public void onFailure(Call<List<UserLogin>> call, Throwable t) {
                Snackbar.make(DashFragment.this.v, "Проверьте подключение к интернету",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

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

                Call<ResponseBody> call = apiLogin.getToken(username,token);

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
                        // SNACKBAR
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