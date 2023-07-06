package com.example.justwater.database;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.util.Date;
import java.sql.Time;

@ProvidedTypeConverter
public class DataTypeConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Time toTime(Long time) {
        return time == null ? null : new Time(time);
    }

    @TypeConverter
    public static Long timeToTimestamp(Time time) {
        return time == null ? null : time.getTime();
    }
}