package com.dev.tanners.movieworld.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dev.tanners.movieworld.db.FavoriteMoviesContract.MovieEntry;

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;


    public FavoriteMoviesDbHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_TABLE_CREATE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_NAME_ID + " INTEGER NOT NULL," +
                MovieEntry.COLUMN_NAME_POSTER + " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                ");";

        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not needed
    }
}
