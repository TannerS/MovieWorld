package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.dev.tanners.movieworld.db.config.DBConfig;

@Database(entities = {MovieEntry.class}, version = 1, exportSchema = false)
@TypeConverters(TimestampConverter.class)
public abstract class MovieDatabase extends RoomDatabase {
    // used for synchronization
    private static final Object LOCK = new Object();
    // name of database
    private static MovieDatabase mInstance;

    public static MovieDatabase getInstance(Context context) {
        // check if instance already exist
        if (mInstance == null) {
            // used for synchronization of threads by locking this code chunk
            // read more: https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
            synchronized (LOCK) {
                // create database instance using Room
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, DBConfig.DATABASE_NAME)
                        .build();
            }
        }
        // return current instance
        return mInstance;
    }

    /**
     * Abstract method to return movie dao
     *
     * @return MovieDao
     */
    public abstract MovieDao getMovieDao();

}