package com.example.korzhik.testproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreProfileInfo {

    private ResponseBody profileChars;
    private String username;

    public void getAndSaveProfileInfo(final Context context) throws IOException {

        //profileChars = new ResponseBody();

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        username = preferences.getString("username", "unknown");

        // Начали запрос
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILogin.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APILogin apiLogin = retrofit.create(APILogin.class);

        Call<List<ProfileChars>> call = apiLogin.getUserInfo(username);

        call.enqueue(new Callback<List<ProfileChars>>() {
            @Override
            public void onResponse(Call<List<ProfileChars>> call, Response<List<ProfileChars>> response) {
                List<ProfileChars> profileChars = response.body();

                for (ProfileChars profileChars1 : profileChars) {
                    SharedPreferences preferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("lvl", profileChars1.getLvl());
                    editor.putInt("number", profileChars1.getNumber());
                    editor.putInt("iq", profileChars1.getIntelligence());
                    editor.putInt("social", profileChars1.getSocialSkills());
                    editor.putInt("charity", profileChars1.getFriendliness());
                    editor.putInt("home", profileChars1.getHomeEconomics());
                    editor.putInt("health", profileChars1.getPhysicalActivity());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<List<ProfileChars>> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
