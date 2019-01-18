package com.example.korzhik.testproject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {//начальный экран

    String name = "name";
    ConstraintLayout exs[] = new ConstraintLayout[5];
    // tvs - массивы textview для главного экрвнв
    TextView tvs_names[] = new TextView[5]; // массив имен квестов
    TextView tvs_shorts[] = new TextView[5]; // массив кратких описаний квестов
    View supView;

    List<QuestCard> questCard;
    List<QuestCard> outputQuestCard;
    LocalBDClass db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        supView = inflater.inflate(R.layout.fragment_home, container, false);
        exs[0] = supView.findViewById(R.id.ex_1);
        exs[1] = supView.findViewById(R.id.ex_2);
        exs[2] = supView.findViewById(R.id.ex_3);
        exs[3] = supView.findViewById(R.id.ex_4);
        exs[4] = supView.findViewById(R.id.ex_5);

        tvs_names[0] = supView.findViewById(R.id.quest_1_name);
        tvs_names[1] = supView.findViewById(R.id.quest_2_name);
        tvs_names[2] = supView.findViewById(R.id.quest_3_name);
        tvs_names[3] = supView.findViewById(R.id.quest_4_name);
        tvs_names[4] = supView.findViewById(R.id.quest_5_name);

        tvs_shorts[0] = supView.findViewById(R.id.quest_1_short_description);
        tvs_shorts[1] = supView.findViewById(R.id.quest_2_short_description);
        tvs_shorts[2] = supView.findViewById(R.id.quest_3_short_description);
        tvs_shorts[3] = supView.findViewById(R.id.quest_4_short_description);
        tvs_shorts[4] = supView.findViewById(R.id.quest_5_short_description);


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

                questCard = response.body();
                Log.d("MyLog", "ЗАПРОС ПОЛУЧЕН");
//                MyApplication ma = new MyApplication();
//                ma.onCreate();
//                mt = new MyTask();
//                mt.execute();


//                Log.d("MyLog", "BD CREATED");
//                for (int i = 0; i < 5; ++i) {
//                    tvs_names[i].setText(outputQuestCard.get(i).getName());
//                    tvs_shorts[i].setText(outputQuestCard.get(i).getShort_info());
//                }


                // showNames.add(qcForeach.getName());
                //showShortInfos.add(qcForeach.getShort_info());
                //showFullInfos.add(qcForeach.getFull_info());

                // ВРЕМЕННО: листы с полученными с сервера значениями имен и описаний
//                ArrayList<String> showNames = new ArrayList<String>();
//                ArrayList<String> showShortInfos = new ArrayList<String>();
//                ArrayList<String> showFullInfos = new ArrayList<String>();
//                // пушинг в листы
//                for (QuestCard qcForeach : questCard) {
//                    showNames.add(qcForeach.getName());
//                    showShortInfos.add(qcForeach.getShort_info());
//                    showFullInfos.add(qcForeach.getFull_info());
//                }
//
//                for (int i = 0; i < 5; ++i) {
//                    tvs_names[i].setText(showNames.get(i));
//                    tvs_shorts[i].setText(showShortInfos.get(i));
//                }
//
            }

            @Override
            public void onFailure(Call<List<QuestCard>> call, Throwable t) {
                Log.d("MyLog", "ЗАПРОС НЕ ПОЛУЧЕН");
            }
        });


        for (int i = 0; i < 5; ++i) {

            exs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), QuestActivity.class);
                    intent.putExtra(QuestActivity.KEY_NAME, name);
                    startActivity(intent);
                }
            });
        }
        return supView;
    }

//    class MyTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            for (QuestCard qcForeach : questCard) {
//                Log.d("MyLog", "qur");
//
//                MyApplication
//                        .getInstance()
//                        .getDatabase()
//                        .getQuestCardDAO()
//                        .insertAll(qcForeach);
//
//            }
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//    }

}