package com.example.korzhik.testproject;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@android.arch.persistence.room.Database(entities = {QuestCard.class}, version = 1)

public abstract class LocalBDClass extends RoomDatabase {

    private static LocalBDClass instance;

    public abstract QuestCardDAO questCardDAO();

    public static synchronized LocalBDClass getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    LocalBDClass.class,
                    "questCard_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

//    private static  RoomDatabase.Callback roomCallback =
//            new RoomDatabase.Callback() {
//                @Override
//                public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                    super.onCreate(db);
//                }
//            };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private QuestCardDAO questCardDAO;
//
//        public PopulateDbAsyncTask(LocalBDClass db) {
//            questCardDAO = db.questCardDAO();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            questCardDAO.insert(new QuestCard());
//            return null;
//        }
//    }

}
