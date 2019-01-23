package com.example.korzhik.testproject.questdata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.korzhik.testproject.questdata.LocalBDClass;
import com.example.korzhik.testproject.questdata.QuestCard;
import com.example.korzhik.testproject.questdata.QuestCardDAO;

import java.util.List;

public class QuestCardRepository {
    private QuestCardDAO questCardDAO;
    private LiveData<List<QuestCard>> allQuestCards;

    public QuestCardRepository(Application application) {
        LocalBDClass database = LocalBDClass.getInstance(application);
        questCardDAO = database.questCardDAO();
        allQuestCards = questCardDAO.getAllQuestCard();
    }

    public void insert(QuestCard questCard) {
        new InsertQuestCardAsyncTask(questCardDAO).execute(questCard);
    }

    public void delete(QuestCard questCard) {
        new DeleteAllQuestCardAsyncTask(questCardDAO).execute(questCard);
    }

    public void deleteAllQuestCards() {
        new DeleteAllQuestCardAsyncTask(questCardDAO).execute();
    }

    public LiveData<List<QuestCard>> getAllQuestCards() {
        return allQuestCards;
    }

    private static class InsertQuestCardAsyncTask extends AsyncTask
    <QuestCard, Void, Void>{

        private QuestCardDAO questCardDAO;

        private InsertQuestCardAsyncTask(QuestCardDAO questCardDAO) {
            this.questCardDAO = questCardDAO;
        }

        @Override
        protected Void doInBackground(QuestCard... questCards) {
            questCardDAO.insert(questCards[0]);
            return null;
        }
    }

    private static class DeleteQuestCardAsyncTask extends AsyncTask
            <QuestCard, Void, Void>{

        private QuestCardDAO questCardDAO;

        private DeleteQuestCardAsyncTask(QuestCardDAO questCardDAO) {
            this.questCardDAO = questCardDAO;
        }

        @Override
        protected Void doInBackground(QuestCard... questCards) {
            questCardDAO.delete(questCards[0]);
            return null;
        }
    }

    private static class DeleteAllQuestCardAsyncTask extends AsyncTask
            <QuestCard, Void, Void>{

        private QuestCardDAO questCardDAO;

        public DeleteAllQuestCardAsyncTask(QuestCardDAO questCardDAO) {
            this.questCardDAO = questCardDAO;
        }

        @Override
        protected Void doInBackground(QuestCard... questCards) {
            questCardDAO.deleteAllQuestCard();
            return null;
        }
    }
}
