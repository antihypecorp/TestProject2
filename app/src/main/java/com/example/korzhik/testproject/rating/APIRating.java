package com.example.korzhik.testproject.rating;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRating {

    String HOST = "http://game.mifoza.com";

    @GET("tasks/get_rating.php")
    Call<List<RatingLine>> getRating();
}
