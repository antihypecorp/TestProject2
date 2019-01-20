package com.example.korzhik.testproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotifFragment extends Fragment {//квест

    private EditText etName;
    private EditText etShortInfo;
    private EditText etFullInfo;
    private TextView tvGroupText;
    private Spinner etGroupList;
    private TextView tvLvlText;
    private Spinner etLvlList;
    private Button sendBtn;
    private LinearLayout view;

    public String name;
    public String shortInfo;
    public String fullInfo;
    public String[] group = {"Интеллект", "Социальные навыки", "Благотворительность", "Домоводство", "Физическая активность"};
    public String[] lvl = {"1", "2", "3"};

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);

        etName = v.findViewById(R.id.name);
        etShortInfo = v.findViewById(R.id.shortInfo);
        etFullInfo = v.findViewById(R.id.fullInfo);
        sendBtn = v.findViewById(R.id.send);
        view = v.findViewById(R.id.great);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                shortInfo = etShortInfo.getText().toString();
                fullInfo = etFullInfo.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APILogin.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ResponseBody> call = apiService.sendTask(name, shortInfo, fullInfo);

                // Результаты запроса
                call.enqueue(new Callback<ResponseBody>() {
                    // Что произойдет в случае удачного исхода
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            // Сохраняем токен квеста в SharedPreferences
                            final String mMessage = response.body().string();

                            // Выводим сообщение об удачном взятии задания
                            Snackbar.make(view, "Квест отправлен! Спасибо!",
                                    Snackbar.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Что произойдет в случае неудачного исхода
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Показываем Тост с просьбой попробовать снова
                        Snackbar.make(view, "Что-то пошло не так...Попробуйте снова...",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;

    }
}
