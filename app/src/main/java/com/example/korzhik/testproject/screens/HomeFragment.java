package com.example.korzhik.testproject.screens;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.korzhik.testproject.questdata.LocalBDClass;
import com.example.korzhik.testproject.questdata.QuestCard;
import com.example.korzhik.testproject.questdata.QuestCardRepository;
import com.example.korzhik.testproject.R;
import com.example.korzhik.testproject.profiledata.StoreProfileInfo;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment implements LifecycleOwner {//начальный экран

    String name = "name";
    ConstraintLayout exs[] = new ConstraintLayout[5];
    // tvs - массивы textview для главного экрвнв
    TextView tvs_names[] = new TextView[5]; // массив имен квестов
    TextView tvs_shorts[] = new TextView[5]; // массив кратких описаний квестов
    View supView;
    private QuestCardRepository questCardRepository;

    private StoreProfileInfo spi;
    private Button btnSendTask;


    private SharedPreferences preferences;
    Application application;


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

        btnSendTask = supView.findViewById(R.id.button_add);

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

        spi = new StoreProfileInfo();
        try {
            spi.getAndSaveProfileInfo(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }


        application = getActivity().getApplication();
        questCardRepository = new QuestCardRepository(application);

        LiveData<List<QuestCard>> liveData = questCardRepository.getAllQuestCards();


        liveData.observe(HomeFragment.this, new Observer<List<QuestCard>>() {
            @Override
            public void onChanged(@Nullable List<QuestCard> questCards) {
                for (int i = 0; i < 5; ++i) {
                            tvs_names[i].setText(questCards.get(i).getName());
                            tvs_shorts[i].setText(questCards.get(i).getShort_info());
                }
            }
        });





        for (int i = 0; i < 5; ++i) {

            exs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.ex_1:
                            Intent intentFirst = new Intent(
                                    getActivity(),
                                    QuestActivity.class);
                            intentFirst.putExtra("idtp", "1");
                            startActivity(intentFirst);
                            break;

                        case R.id.ex_2:
                            Intent intentSecond = new Intent(
                                    getActivity(),
                                    QuestActivity.class);
                            intentSecond.putExtra("idtp", "2");
                            startActivity(intentSecond);

                            break;

                        case R.id.ex_3:
                            Intent intentThird = new Intent(
                                    getActivity(),
                                    QuestActivity.class);
                            intentThird.putExtra("idtp", "3");
                            startActivity(intentThird);

                            break;

                        case R.id.ex_4:
                            Intent intentFourth = new Intent(
                                    getActivity(),
                                    QuestActivity.class);
                            intentFourth.putExtra("idtp", "4");
                            startActivity(intentFourth);

                            break;

                        case R.id.ex_5:
                            Intent intentFifth = new Intent(
                                    getActivity(),
                                    QuestActivity.class);
                            intentFifth.putExtra("idtp", "5");
                            startActivity(intentFifth);

                            break;
                    }
                }
            });
        }

        btnSendTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), addTask.class));
            }
        });

        return supView;
    }

}