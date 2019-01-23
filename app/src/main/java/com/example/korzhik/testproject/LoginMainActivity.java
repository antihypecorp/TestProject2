package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginMainActivity extends AppCompatActivity {


    public String username;
    public String token;
    private StoreQuestInfo sqi;
    private StoreProfileInfo spi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        sqi = new StoreQuestInfo();
        spi = new StoreProfileInfo();

        // Из SharedPreferences достаем Никнейм и Токен для GET запроса на сервак
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        username = preferences.getString("username", "unknown");
        token = preferences.getString("token", "unknown");

        sqi.getAndSaveQuestInfo(getApplication());


        // Начали запрос
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILogin.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APILogin apiLogin = retrofit.create(APILogin.class);

        Call<ResponseBody> call = apiLogin.getToken(username, token);

        call.enqueue(new Callback<ResponseBody>() {
            // Что произойдет в случае удачного исхода
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                try {
                    // Проверили токен на сервере, если они не совпадают, то отправляем юзера
                    // в окно авторизации, а если совпали, то в окно профиля
                    final String mMessage = response.body().string();

                    if (mMessage.equals("Error")) {
                        Intent intentLogin = new Intent(
                                LoginMainActivity.this,
                                LoginActivity.class);
                        startActivity(intentLogin);
                    } else {
                        spi.getAndSaveProfileInfo(LoginMainActivity.this);
                        Intent intentEnter = new Intent(
                                LoginMainActivity.this,
                                MainActivity.class);
                        startActivity(intentEnter);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Что произойдет в случае неудачного исхода
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Показываем Тост с просьбой попробовать снова

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}

