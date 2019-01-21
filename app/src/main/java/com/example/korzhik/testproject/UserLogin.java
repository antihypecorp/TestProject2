package com.example.korzhik.testproject;

import com.google.gson.annotations.SerializedName;

public class UserLogin {
    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("lvl")
    public int lvl;

    @SerializedName("common")
    public int common;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("passwordConf")
    public String passwordConf;

    @SerializedName("token")
    public String token;

    public UserLogin(String name, String surname, int lvl, int common, String username) {
        this.name = name;
        this.surname = surname;
        this.lvl = lvl;
        this.common = common;
        this.username = username;
    }

    public UserLogin(String name, String surname, String username, String password, String passwordConf) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.passwordConf = passwordConf;
    }

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getCommon() {
        return common;
    }

    public void setCommon(int common) {
        this.common = common;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConf() {
        return passwordConf;
    }

    public void setPasswordConf(String passwordConf) {
        this.passwordConf = passwordConf;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
