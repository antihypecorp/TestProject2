package com.example.korzhik.testproject;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "questCard_table")
public class QuestCard {
    @PrimaryKey(autoGenerate = true)
    private int idtable;


    private String id;

    private String name;
    private String short_info;
    private String full_info;
    private String group;
    // int points;
    private String lvl;

    public void setIdtable(int idtable) {
        this.idtable = idtable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
    }

    public void setFull_info(String full_info) {
        this.full_info = full_info;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }


    public int getIdtable() {
        return idtable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShort_info() {
        return short_info;
    }

    public String getFull_info() {
        return full_info;
    }

    public String getGroup() {
        return group;
    }

    public String getLvl() {
        return lvl;
    }

    // boolean accept;
    // boolean done;
}
