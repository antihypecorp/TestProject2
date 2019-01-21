package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // Объявляем переменные
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnReg;

    public String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // К приватным переменным привязываем элементы верстки
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.bReg);
        btnLogin = findViewById(R.id.bLogin);

        // Если нажали кнопку "Зарегистрироваться", то перенаправить на окно регистрации
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(
                        LoginActivity.this,
                        RegActivity.class);
                startActivity(intentReg);
            }
        });

        // Если нажата кнопка "Войти", то происходить следующее...
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // К нашим публичным строкам присваиваем значения из полей
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                // Начали запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APILogin apiLogin = retrofit.create(APILogin.class);

                Call<ResponseBody> call = apiLogin.loginUser(username, password);

                call.enqueue(new Callback<ResponseBody>() {
                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        TextView answer = (TextView) findViewById(R.id.answer);
                        try {
                            // Если с сервера не были возвращены ошибки, то...
                            final String mMessage = response.body().string();
                            if (!mMessage.equals("Пользователя с таким ником не существует")) {
                                if (!mMessage.equals("Введен неверный пароль")) {
                                    SharedPreferences preferences = PreferenceManager
                                            .getDefaultSharedPreferences(
                                                    LoginActivity.this);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("username", username);
                                    editor.putString("token", mMessage);
                                    editor.apply();
                                    Intent intentReged = new Intent(
                                            LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intentReged);
                                } else {
                                    answer.setText(mMessage);
                                }
                            } else {
                                answer.setText(mMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    // Что произойдет в случае неудачного исхода
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Показываем Тост с просьбой попробовать снова
                        Toast.makeText(LoginActivity.this,
                                "Что-то пошло не так...Попробуйте снова",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
