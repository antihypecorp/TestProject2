package com.example.korzhik.testproject.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.korzhik.testproject.R;
import com.example.korzhik.testproject.rating.APIRating;
import com.example.korzhik.testproject.rating.RatingLine;
import com.example.korzhik.testproject.rating.RatingLineAdapter;
import com.example.korzhik.testproject.rating.StoreRating;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotifFragment extends Fragment {

    public RecyclerView list;
    private RatingLineAdapter adapter;
    View v;


    private StoreRating storeRating;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);
        list = v.findViewById(R.id.list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIRating.HOST)
                .addConverterFactory(GsonConverterFactory
                        .create())
                .build();

        APIRating apiRating = retrofit.create(APIRating.class);

        Call<List<RatingLine>> call = apiRating.getRating();

        call.enqueue(new Callback<List<RatingLine>>() {
            @Override
            public void onResponse(Call<List<RatingLine>> call, Response<List<RatingLine>> response) {
                adapter = new RatingLineAdapter(response.body());
                list.setAdapter(adapter);
                list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<RatingLine>> call, Throwable t) {
                Log.d("MyLog", "WRONG");
            }
        });



        return v;
    }
}
