package com.dev.tanners.movieworld.api.model.movies;

import android.arch.persistence.room.Ignore;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewBase;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoBase;
import com.dev.tanners.movieworld.db.MovieEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * This model acts as a wrapper for the append_to_response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResultAppend extends MovieEntry{
    private MovieReviewBase reviews;
    private MovieVideoBase videos;

    public MovieResultAppend()
    {

    }


    // used for database. excludes reviews and videos
    public MovieResultAppend(int id, int movieId, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp) {
        super(id, movieId, vote_average, title, poster_path, backdrop_path, overview, release_date, timestamp);
    }

    @Ignore
    // used for database. excludes reviews and videos
    public MovieResultAppend(int movieId, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp) {
        super(movieId, vote_average, title, poster_path, backdrop_path, overview, release_date, timestamp);
    }

    /**
     * @return
     */
    public MovieReviewBase getReviews() {
        return reviews;
    }

    /**
     * @param reviews
     */
    public void setReviews(MovieReviewBase reviews) {
        this.reviews = reviews;
    }

    /**
     * @return
     */
    public MovieVideoBase getVideos() {
        return videos;
    }

    /**
     * @param videos
     */
    public void setVideos(MovieVideoBase videos) {
        this.videos = videos;
    }
}
