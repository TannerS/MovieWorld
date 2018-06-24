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
 *
 * Below is the flow of how this data is used
 *
 * This object is filled up with the id, poster_path, and favorite variables from either
 * the rest call, or the database call. Two ways (rest, local storage) use this class to
 * display data on the list. Since we only need these at minimum.
 *
 * Once this data is used to display the list items, each click of these list items will
 * transfer the movie id to the next activity which will use that to download the rest of the data
 *
 * the rest of the data may change, that is why I prefer it to re-download instead of saving all data locally
 *
 * the favorite is also used. data coming from rest favorite = false, while db data is favorite = true.
 * the movie activity will use this boolean to determine if this is a favorite movie or not (which affects local
 * UI settings and functionality on the page). since if the data comes from, db, it is favorite, else not
 *
 * When the movie actiivy page loads, it will use the class MovieResultFull (which MovieResult is a parent of)
 * to store the rest of the page's data after making the rest call, and since this class is the child of MovieResult,
 * we are using inheritance to use the same variables that are in MovieResult instead of redefining the member vairables
 * in two separate classes that act the same
 *
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
     */
    public MovieResult(int id, String poster_path, Date timestamp) {
        this.id = id;
        this.poster_path = poster_path;
        this.timestamp = timestamp;
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
