package com.example.korzhik.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {//начальный экран
    private ConstraintLayout c;
    String name = "name";
    ConstraintLayout exs[] = new ConstraintLayout[10];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        for(int i=1;i<=10;++i){

        }*/
        Intent intent = new Intent(getActivity(), QuestActivity.class);
        intent.putExtra(QuestActivity.KEY_NAME, name);
        startActivity(intent);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        exs[0] = view.findViewById(R.id.ex_1);
        return view;
    }



}
