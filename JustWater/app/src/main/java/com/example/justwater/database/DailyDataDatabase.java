package com.example.justwater.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(DataTypeConverter.class)
@Database(entities = {DailyData.class}, version = 1, exportSchema = false)
abstract class DailyDataDatabase extends RoomDatabase {

    private volatile static DailyDataDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    abstract DailyDataDao dailyDataDao();

    static DailyDataDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (DailyDataDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), DailyDataDatabase.class, "daily_data")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }

        // Dummy Data
        databaseWriteExecutor.execute(() -> {
            DailyDataDao dao = instance.dailyDataDao();
            dao.deleteAll();
            Calendar myCalendar = Calendar.getInstance();
            Date date = new Date(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            for (int i = 1; i <= 8; i++) {
                DailyData word = new DailyData(date, new Time(i, 30, 00), 100);
                dao.insert(word);
            }
            date = new Date(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH) - 1);
            for (int i = 1; i <= 12; i++) {
                DailyData word1 = new DailyData(date, new Time(10, 30, 00), 1120);
                dao.insert(word1);
            }
        });
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
