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

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HomeFragment extends Fragment {//начальный экран
    PrintWriter out;
    {
        try {
            out = new PrintWriter("myfile.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    String name = "name";
    ConstraintLayout exs[] = new ConstraintLayout[10];
    View supView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        supView = inflater.inflate(R.layout.fragment_home, container, false);
        exs[0] = supView.findViewById(R.id.ex_1);
        exs[1] = supView.findViewById(R.id.ex_2);
        exs[2] = supView.findViewById(R.id.ex_3);
        exs[3] = supView.findViewById(R.id.ex_4);
        exs[4] = supView.findViewById(R.id.ex_5);

        for(int i=0;i<5;++i){
            exs[i].setOnClickListener(new View.OnClickListener(){
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
}