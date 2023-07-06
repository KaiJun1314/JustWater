package com.example.justwater.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

public class DailyDataRepository {
    private final DailyDataDao dailyDataDao;
    private LiveData<List<DailyData>> dailyData;
    private LiveData<Integer> count, total;

    public DailyDataRepository(Application application) {
        DailyDataDatabase db = DailyDataDatabase.getInstance(application);
        dailyDataDao = db.dailyDataDao();
    }

    public LiveData<List<DailyData>> getDailyData(Date date) {
        dailyData = dailyDataDao.get_daily_data(date);
        return dailyData;
    }

    public LiveData<Integer> getCount(Date date){
        count = dailyDataDao.getDataCount(date);
        return count;
    }

    public LiveData<Integer> getTotal(Date date){
        total = dailyDataDao.getTotalWater(date);
        return total;
    }

    public void insert(DailyData data) {
        DailyDataDatabase.databaseWriteExecutor.execute(() -> {
            dailyDataDao.insert(data);
        });
    }

    public void delete(DailyData data){
        DailyDataDatabase.databaseWriteExecutor.execute(() -> {
            dailyDataDao.delete(data);
        });
    }
}
