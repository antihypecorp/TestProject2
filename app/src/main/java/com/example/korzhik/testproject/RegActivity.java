package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegActivity extends AppCompatActivity {

    // Объявляем переменные
    private EditText etName;
    private EditText etSurname;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnBack;
    private LinearLayout view;
    private StoreProfileInfo spi;

    public String name;
    public String surname;
    public String username;
    public String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        spi = new StoreProfileInfo();

        // К приватным переменным привязываем элементы верстки
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnBack = findViewById(R.id.bBack);
        btnLogin = findViewById(R.id.bLogin);
        view = findViewById(R.id.great_reg);
        // Если нажали кнопку "Вернуться назад", то возвращаем пользователя назад
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackL = new Intent(
                        RegActivity.this,
                        LoginActivity.class);
                startActivity(intentBackL);
            }
        });

        // Если нажата кнопка "Зарегистрироваться", то...
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Присваиваем публичным переменным значения их полей
                name = etName.getText().toString();
                surname = etSurname.getText().toString();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                // Начинаем запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APILogin apiLogin = retrofit.create(APILogin.class);

                Call<ResponseBody> call = apiLogin.regUser(name, surname, username, password);

                call.enqueue(new Callback<ResponseBody>() {
                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        TextView answer = (TextView) findViewById(R.id.answer);
                        try {
                            // Если с сервера были возвращены ошибки, то отобразить их в строке
                            final String mMessage = response.body().string();
                            if (mMessage.equals("Переданы неверные данные") |
                                    mMessage.equals("Никнейм не менее 5 символов") |
                                    mMessage.equals("Пароль не менее 8 символов") |
                                    mMessage.equals("Пароли не совпадают") |
                                    mMessage.equals("Пользователь с таким ником уже зарегистрирован")) {
                                answer.setText(mMessage);
                            } else {
                                // Если с сервера не были возвращены ошибки, то...
                                // Открываем SharedPreferences и сохраняем в него Никнейм и токен
                                SharedPreferences preferences = PreferenceManager
                                        .getDefaultSharedPreferences(RegActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("fullname", name + " " + surname);
                                editor.putString("username", username);
                                editor.putString("token", mMessage);
                                editor.apply();
                                // Перекидываем пользователя на страницу профиля
                                spi.getAndSaveProfileInfo(RegActivity.this);
                                startActivity(new Intent(RegActivity.this,
                                        MainActivity.class));
                            }
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
