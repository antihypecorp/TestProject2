package com.example.korzhik.testproject.rating;

import android.app.Application;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreRating {

    private RatingLineAdapter adapter;

    public RatingLineAdapter getRating() {

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
            }

            @Override
            public void onFailure(Call<List<RatingLine>> call, Throwable t) {
                Log.d("MyLog", "WRONG");
                adapter = null;
            }
        });
        return adapter;
    }
}
