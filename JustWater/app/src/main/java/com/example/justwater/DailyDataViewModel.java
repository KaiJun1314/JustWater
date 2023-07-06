package com.example.justwater;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.justwater.database.DailyData;
import com.example.justwater.database.DailyDataRepository;
import java.util.Date;
import java.util.List;

public class DailyDataViewModel extends AndroidViewModel {
    private final DailyDataRepository mRepository;
    private LiveData<List<DailyData>> mAllWords;
    private LiveData<Integer> count, total;

    public DailyDataViewModel(Application application) {
        super(application);
        mRepository = new DailyDataRepository(application);
    }

    public void readDailyData(Date date) {
        mAllWords = mRepository.getDailyData(date);
        count = mRepository.getCount(date);
        total = mRepository.getTotal(date);
    }

    public LiveData<List<DailyData>> getDailyData(){
        return mAllWords;
    }

    public LiveData<Integer> getTotal(){
        return total;
    }

    public LiveData<Integer> getCount(){
        return count;
    }

    void insert(DailyData data) {mRepository.insert(data);}

    void detele(DailyData data) {mRepository.delete(data);}
}