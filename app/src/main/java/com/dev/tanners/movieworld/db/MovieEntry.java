package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.config.DBConfig;

import java.util.Date;

@TypeConverters(TimestampConverter.class)
@Entity(tableName = DBConfig.TABLE_NAME)
public class MovieEntry {
//    @PrimaryKey(autoGenerate = true)
    // primary key
//    protected int id;
    protected Date timestamp;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

