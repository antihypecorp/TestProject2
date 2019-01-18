package com.example.korzhik.testproject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {QuestCard.class}, version = 1)

public abstract class LocalBDClass extends RoomDatabase {
    public abstract QuestCardDAO getQuestCardDAO();
}
