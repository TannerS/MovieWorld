package com.dev.tanners.movieworld.api.model.movies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.dev.tanners.movieworld.db.MovieEntry;
import com.dev.tanners.movieworld.db.TimestampConverter;
import com.dev.tanners.movieworld.db.config.DBConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Movie model, this is used to get only list related data.
 * Basically, this will show only what is needed in the list
 * the rest of the info will be fecthed via rest call on the page due to
 * the fact that the information can change, so we will update the fields
 * each time we load the page, while this data is only used in the list
 * to show the movie photo with its id, so we dont request the same data twice
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = DBConfig.TABLE_NAME)
@TypeConverters(TimestampConverter.class)
public class MovieResult extends MovieEntry
{
    /**
     * The reason some fields are ignored is the possibility that the information can be changed or updated (stale data)
     * So in this case, only the fields needed to display in the favorites list will be kept and rest will be loaded
     * in the second activity via its id
     */
    @PrimaryKey
    protected int id;
    protected String poster_path;
    // used for UI to keep track of favorite movies and non favorite movies
    // this will always be true in db, but other movies that use this class use it
    protected boolean is_favorite;

    /**
     * Constructor
     * used for json serialization/de-serialization
     */
    @Ignore
    public MovieResult() {
        // for json de-serialization
    }

    /**
     * Constructor
     *
     * Used for db entity
     *
     * @param id
     * @param poster_path
     * @param timestamp
     * @param is_favorite
     */
    public MovieResult(int id, String poster_path, Date timestamp, boolean is_favorite) {
        this.id = id;
        this.poster_path = poster_path;
        this.timestamp = timestamp;
        this.is_favorite = is_favorite;
    }

    /**
     * Constructor
     *
     * Used for internal
     *
     * @param title
     * @param poster_path
     * @param timestamp
     * @param is_favorite
     */
    @Ignore
    public MovieResult(String poster_path, Date timestamp, boolean is_favorite) {
        this.poster_path = poster_path;
        this.timestamp = timestamp;
        this.is_favorite = is_favorite;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public boolean isIs_favorite() {
        return is_favorite;
    }

    /**
     * @param is_favorite
     */
    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }


    /**
     * @return
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @param poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
