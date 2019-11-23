package com.example.retrofitmvvmsample.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.retrofitmvvmsample.interfaces.DatumDao;
import com.example.retrofitmvvmsample.modelClass.Datum;

@Database(entities = {Datum.class}, version = 1, exportSchema = false)
public abstract class RoomClient extends RoomDatabase {

    private static volatile RoomClient INSTANCE;

    public static RoomClient getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomClient.class, "user_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DatumDao datumDao();
}