package com.example.korzhik.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private EditText etPasswordConf;
    private Button btnLogin;
    private Button btnBack;
    private LinearLayout view;
    private StoreProfileInfo spi;

    public String name;
    public String surname;
    public String username;
    public String password;
    public String passwordConf;


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
        etPasswordConf = findViewById(R.id.etConfPassword);
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

                if (name.matches("^[а-яА-ЯёЁa-zA-Z]+$") &&
                        surname.matches("^[а-яА-ЯёЁa-zA-Z]+$") &&
                        username.matches("^[a-zA-Z0-9]+$") &&
                        password.matches("^[a-zA-Z0-9]+$")) {
                    RegisterUser();
                } else if (name.equals("") | surname.equals("") | username.equals("") | password.equals("")) {
                    Snackbar.make(view, "Заполните пустые поля",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (!name.matches("^[а-яА-ЯёЁa-zA-Z]+$")) {
                    Snackbar.make(view, "Имя только из латиницы, кириллицы и цифр",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (!surname.matches("^[а-яА-ЯёЁa-zA-Z]+$")) {
                    Snackbar.make(view, "Фамилия только из латиницы, кириллицы и цифр",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (!username.matches("^[a-zA-Z0-9]+$")) {
                    Snackbar.make(view, "Никнейи только из латиницы и цифр",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (!password.matches("^[a-zA-Z0-9]+$")) {
                    Snackbar.make(view, "Пароль только из латиницы и цифр",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (name.length() < 3 | surname.length() < 3) {
                    Snackbar.make(view, "Имя и Фамилия не менее 3-х символов",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (username.length() < 5 | password.length() < 8) {
                    Snackbar.make(view, "Никнейм не менее 5 и пароль не менее 8 символов",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else if (!password.equals(passwordConf)) {
                    Snackbar.make(view, "Пароли не совпадают",
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    public void RegisterUser() {
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
                    final String mMessage = response.body().string();
                    answer.setText(mMessage);
                    SharedPreferences preferences = PreferenceManager
                            .getDefaultSharedPreferences(RegActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("token", mMessage);
                    editor.apply();
                    // Перекидываем пользователя на страницу профиля
                    startActivity(new Intent(RegActivity.this,
                            MainActivity.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Что произойдет в случае неудачного исхода
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Показываем Тост с просьбой попробовать снова
                Snackbar.make(view, "Проверьте подключение к интернету.",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
