package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class LoginMainActivity extends AppCompatActivity {

    private LinearLayout view;
    private TextView statusText;

    public String username;
    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        view = findViewById(R.id.great);
        statusText = findViewById(R.id.statusText);

        // Из SharedPreferences достаем Никнейм и Токен для GET запроса на сервак
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        username = preferences.getString("username", "unknown");
        token = preferences.getString("token", "unknown");

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
                        getInfo();
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
                Toast.makeText(getApplicationContext(),
                        "Что-то пошло не так...Попробуйте снова...",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void getInfo() throws IOException {
        // Начали запрос
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILogin.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APILogin apiLogin = retrofit.create(APILogin.class);

        Call<ResponseBody> call = apiLogin.getUserInfo(username);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String text = response.body().string();
                    statusText.setText(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Показываем Тост с просьбой попробовать снова
                Toast.makeText(getApplicationContext(),
                        "Что-то пошло не так...Попробуйте снова...",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
