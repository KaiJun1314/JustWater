package com.example.justwater.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface DailyDataDao {
    @Query("SELECT * FROM daily_data WHERE date=:date")
    LiveData<List<DailyData>> get_daily_data(Date date);

    @Query("DELETE FROM daily_data")
    void deleteAll();

    @Insert
    void insert(DailyData dailyData);

    @Delete
    void delete(DailyData dailyData);

    @Query("SELECT COUNT(amount) FROM daily_data WHERE date=:date")
    LiveData<Integer> getDataCount(Date date);

    @Query("SELECT SUM(amount) FROM daily_data WHERE date=:date")
    LiveData<Integer> getTotalWater(Date date);
}
