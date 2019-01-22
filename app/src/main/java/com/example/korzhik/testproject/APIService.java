package com.example.korzhik.testproject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    String HOST = "http://game.mifoza.com";

    // GET запрос для получения Квестов
    @GET("tasks/get5task.php")
    Call<List<QuestCard>> getQuestCard();

    // GET запрос, регистрирующий взятие Квеста
    @GET("tasks/logic/accept.php")
    Call<ResponseBody> acceptTask(@Query("username") String username,
                                  @Query("id") int id);

    // GET запрос, регистрирующий выполнение Квеста
    @GET("tasks/logic/pass.php")
    Call<ResponseBody> passTask(@Query("username") String username,
                                @Query("token") String token);

    // POST запрос, регистрирующий выполнение Квеста
    @FormUrlEncoded
    @POST("tasks/send.php")
    Call<ResponseBody> sendTask(@Field("name") String name,
                                @Field("shortInfo") String shortInfo,
                                @Field("fullInfo") String fullInfo);
}
