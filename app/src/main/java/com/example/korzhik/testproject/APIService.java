package com.example.korzhik.testproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    String HOST = "http://game.mifoza.com";

    @GET("tasks/get.php")
    Call<List<QuestCard>> getQuestCard();
}
