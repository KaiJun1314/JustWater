package com.example.justwater.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.sql.Time;

@Entity(tableName = "daily_data")
public class DailyData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final Date date;
    private final Time time;
    private final int amount;

    public DailyData(Date date,  Time time, int amount) {
        this.time = time;
        this.date = date;
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public Time getTime() {
        return this.time;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
