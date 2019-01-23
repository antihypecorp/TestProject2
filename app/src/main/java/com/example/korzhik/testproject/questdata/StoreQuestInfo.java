package com.example.korzhik.testproject.questdata;

import android.app.Application;
import android.util.Log;

import com.example.korzhik.testproject.questdata.APIService;
import com.example.korzhik.testproject.questdata.QuestCard;
import com.example.korzhik.testproject.questdata.QuestCardRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// ОТВЕЧАЕТ ЗА ЗАГРУЗКУ КВЕСТОВ И ИХ ЗАПИСЬ В ЛБД
public class StoreQuestInfo {

    private QuestCardRepository questCardRepository;
    private List<QuestCard> questCard;
    private boolean result;

    // Возвращает статус загрузки квестов
    public boolean getAndSaveQuestInfo(Application application) {

        questCardRepository = new QuestCardRepository(application);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.HOST)
                .addConverterFactory(GsonConverterFactory
                        .create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<QuestCard>> call = apiService.getQuestCard();

        // отправка запроса
        call.enqueue(new Callback<List<QuestCard>>() {
            @Override
            public void onResponse(Call<List<QuestCard>> call,
                                   Response<List<QuestCard>> response) {

                questCardRepository.deleteAllQuestCards();
                questCard = response.body();
                Log.d("MyLog", "ЗАПРОС ПОЛУЧЕН");
                for (QuestCard qcForeach : questCard) {
                    questCardRepository.insert(qcForeach);
                }

                result = true;
            }

            @Override
            public void onFailure(Call<List<QuestCard>> call, Throwable t) {
                Log.d("MyLog", "WRONG");
                result = false;
            }
        });

       return result;
    }
}
