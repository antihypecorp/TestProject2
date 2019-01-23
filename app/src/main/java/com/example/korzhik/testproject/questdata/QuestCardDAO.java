package com.example.korzhik.testproject.questdata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.korzhik.testproject.questdata.QuestCard;

import java.util.List;

@Dao
public interface QuestCardDAO {

    @Insert
    void insert(QuestCard questCard);

    @Delete
    void delete(QuestCard questCard);

    @Query("DELETE FROM questCard_table")
    void deleteAllQuestCard();

    @Query("SELECT * FROM questCard_table")
    LiveData<List<QuestCard>> getAllQuestCard();

}
