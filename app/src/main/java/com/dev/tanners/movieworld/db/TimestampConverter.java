package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.TypeConverter;
import java.sql.Date;

/**
 * Used to convert the date object to a format for the sqlite db
 */
public class TimestampConverter {

    @TypeConverter
    public static Date toDateStamp(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}