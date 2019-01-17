package com.example.korzhik.testproject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APILogin {

    String HOST = "http://game.mifoza.com";

    // GET запрос для получения токена
    @GET("/getToken.php")
    Call<ResponseBody> getToken(@Query("username") String username,
                                @Query("token") String token);

    // GET запрос для выхода из приложения
    @GET("/logout.php")
    Call<ResponseBody> logoutUser(@Query("username") String username,
                                  @Query("token") String token);

    // GET запрос для получения информации пользователя
    @GET("/getUserInfo.php")
    Call<List<UserLogin>> getUserInfo(@Query("username") String username);

    // POST запрос для регистрации пользователя
    @FormUrlEncoded
    @POST("/reg.php")
    Call<ResponseBody> regUser(@Field("name") String name,
                               @Field("surname") String surname,
                               @Field("username") String userName,
                               @Field("password") String password);

    // POST запрос для авторизации пользователя
    @FormUrlEncoded
    @POST("/login.php")
    Call<ResponseBody> loginUser(@Field("username") String userName,
                                 @Field("password") String password);
}
