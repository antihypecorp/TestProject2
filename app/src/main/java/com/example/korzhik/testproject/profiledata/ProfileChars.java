package com.example.korzhik.testproject.profiledata;

import com.google.gson.annotations.SerializedName;

public class ProfileChars {
    @SerializedName("lvl")
    public int lvl;

    @SerializedName("number")
    public int number;

    @SerializedName("intelligence")
    public int intelligence;

    @SerializedName("social_skills")
    public int socialSkills;

    @SerializedName("friendliness")
    public int friendliness;

    @SerializedName("home_economics")
    public int homeEconomics;

    @SerializedName("physical_activity")
    public int physicalActivity;


    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSocialSkills() {
        return socialSkills;
    }

    public void setSocialSkills(int socialSkills) {
        this.socialSkills = socialSkills;
    }

    public int getFriendliness() {
        return friendliness;
    }

    public void setFriendliness(int friendliness) {
        this.friendliness = friendliness;
    }

    public int getHomeEconomics() {
        return homeEconomics;
    }

    public void setHomeEconomics(int homeEconomics) {
        this.homeEconomics = homeEconomics;
    }

    public int getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(int physicalActivity) {
        this.physicalActivity = physicalActivity;
    }
}
