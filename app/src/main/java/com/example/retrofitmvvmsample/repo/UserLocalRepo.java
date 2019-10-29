package com.example.retrofitmvvmsample.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.retrofitmvvmsample.DatumDao;
import com.example.retrofitmvvmsample.RoomClient;
import com.example.retrofitmvvmsample.modelClass.Datum;

import java.util.List;

public class UserLocalRepo {

    private static UserLocalRepo repoInstance;
    private final Application application;
    private DatumDao mDatumDao;
    private LiveData<List<Datum>> allData;

    private UserLocalRepo(Application application) {
        this.application = application;
        RoomClient roomClient = RoomClient.getDatabase(application);
        mDatumDao = roomClient.datumDao();
    }

    public static UserLocalRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new UserLocalRepo(application);
        }
        return repoInstance;
    }

    public LiveData<List<Datum>> getUsers() {
        return mDatumDao.getAllData();
    }

    //--------------- inserting --------------------- //

    public void insert(List<Datum> data) {
        new insertAsyncTask(mDatumDao).execute(data);
    }

    private static class insertAsyncTask extends AsyncTask<List<Datum>, Void, Void> {

        private DatumDao mAsyncTaskDao;

        insertAsyncTask(DatumDao dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<Datum>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
