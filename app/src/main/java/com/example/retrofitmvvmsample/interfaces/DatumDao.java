package com.example.retrofitmvvmsample.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofitmvvmsample.modelClass.Datum;

import java.util.List;

@Dao // Give Data Access Object annotation
public interface DatumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Datum> data);

    @Query("DELETE FROM data_table")
        // Delete all values from table
    void deleteAllData();

    @Query("SELECT * FROM data_table")
    LiveData<List<Datum>> getAllData();// Observe the object , so if there is any changes in the table this value will be auto updated and the activity will be notified
}
