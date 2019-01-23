package com.example.korzhik.testproject.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.korzhik.testproject.R;
import com.example.korzhik.testproject.rating.RatingLineAdapter;
import com.example.korzhik.testproject.rating.StoreRating;

public class NotifFragment extends Fragment {

    public RecyclerView list;
    View v;


    private StoreRating storeRating;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);

        storeRating = new StoreRating();

        RatingLineAdapter adapter = storeRating.getRating();

        list = v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
        list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        return inflater.inflate(R.layout.fragment_notif, container, false);
    }
}
