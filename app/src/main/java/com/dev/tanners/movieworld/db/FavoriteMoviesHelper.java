//package com.dev.tanners.movieworld.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.dev.tanners.movieworld.api.model.movies.MovieResultAppend;
//import com.dev.tanners.movieworld.db.FavoriteMoviesContract.MovieEntry;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FavoriteMoviesHelper {
//    private SQLiteDatabase mDb;
//
//    public FavoriteMoviesHelper(Context mContext) {
//        FavoriteMoviesDbHelper mHelper = new FavoriteMoviesDbHelper(mContext);
//        mDb = mHelper.getWritableDatabase();
//    }
//
//    public Cursor getAll(String mTable, String mColumn)
//    {
//        return mDb.query(
//                MovieEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                MovieEntry.COLUMN_NAME_TIMESTAMP
//        );
//    }
//
//    public Cursor getSingle(String mWhere)
//    {
//        return mDb.query(
//                MovieEntry.TABLE_NAME,
//                new String[]{
//                        MovieEntry.COLUMN_NAME_ID,
//                        MovieEntry.COLUMN_NAME_POSTER,
//                        MovieEntry.COLUMN_NAME_TIMESTAMP
//                },
//                mWhere,
//                null,
//                null,
//                null,
//                null,
//                MovieEntry.COLUMN_NAME_TIMESTAMP
//
//        );
////        Cursor findEntry = db.query("sku_table", columns, "owner="+owner, null, null, null, null)
//    }
//
//
//
//    private boolean insertMovieData(MovieResultAppend mMovieResultAppend)
//    {
//        if(mDb == null || mMovieResultAppend == null ){
//            return false;
//        }
//        List<ContentValues> mInsertList = new ArrayList<ContentValues>();
//
//        ContentValues mCv = new ContentValues();
//
//        mCv.put(MovieEntry.COLUMN_NAME_ID, mMovieResultAppend.getId());
//        mCv.put(MovieEntry.COLUMN_NAME_POSTER, mMovieResultAppend.getPoster_path());
//
//        mInsertList.add(mCv);
//
//        try
//        {
//            mDb.beginTransaction();
//
//            for(ContentValues mValue : mInsertList){
//                mDb.insert(MovieEntry.TABLE_NAME, null, mValue);
//            }
//
//            mDb.setTransactionSuccessful();
//        }
//        catch (SQLException e) {
//            return false;
//            // TODO error handling back to UI
//            // TODO if fail, still endTransaction, right now it does not
//        }
//        finally
//        {
//            mDb.endTransaction();
//        }
//
//        return true;
//    }
//
//}
//
//
