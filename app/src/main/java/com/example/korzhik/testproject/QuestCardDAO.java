package com.example.korzhik.testproject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface QuestCardDAO {

    @Insert
    void insertAll(QuestCard... questCard);

    @Delete
    void delete(QuestCard questCard);

    @Query("SELECT * FROM questCard")
    List<QuestCard> getAllquestCard();

}
