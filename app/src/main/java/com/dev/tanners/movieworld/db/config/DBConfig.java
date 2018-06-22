package com.dev.tanners.movieworld.db.config;

public class DBConfig {
    // table now (for now only one)
    public final static String TABLE_NAME = "movies";
    // used for query order_by field column
    public final static String ORDER_BY_FIELD = "timestamp";
    // query for getting all movies
    public final static String GET_ALL_MOVIES_QUERY = "SELECT * FROM" + " " + TABLE_NAME + " " + "ORDER BY" + " " + ORDER_BY_FIELD;
    // query for getting a single movie
    public final static String GET_MOVIE_BY_ID_QUERY = "SELECT * FROM" + " " + TABLE_NAME + " " + "WHERE id" + " = " + ":id";
    // name of database
    public static final String DATABASE_NAME = "favorite_movies";
}
