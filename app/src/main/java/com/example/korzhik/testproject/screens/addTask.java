package com.example.korzhik.testproject.screens;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.korzhik.testproject.R;
import com.example.korzhik.testproject.authorization.APILogin;
import com.example.korzhik.testproject.questdata.APIService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addTask extends AppCompatActivity {

    private EditText etTaskName;
    private EditText etShortInfo;
    private EditText etFullInfo;
    private Button SendBtn;
    private LinearLayout view;

    public String name;
    public String shortInfo;
    public String fullInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        view = findViewById(R.id.addTask);
        etTaskName = findViewById(R.id.taskName);
        etShortInfo = findViewById(R.id.shortInfo);
        etFullInfo = findViewById(R.id.fullInfo);
        SendBtn = findViewById(R.id.sendBtn);

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etTaskName.getText().toString();
                shortInfo = etShortInfo.getText().toString();
                fullInfo = etFullInfo.getText().toString();

                // Начали запрос
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ResponseBody> call = apiService.sendTask(name, shortInfo, fullInfo);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Snackbar.make(view, "Квест отправлен на модерацию! Спасибо!",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Snackbar.make(view, "Упс... Что-то пошло не так...",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                });

            }
        });

    }
}