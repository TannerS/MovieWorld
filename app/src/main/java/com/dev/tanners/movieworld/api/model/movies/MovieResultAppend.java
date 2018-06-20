package com.dev.tanners.movieworld.api.model.movies;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewBase;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This model acts as a wrapper for the append_to_response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResultAppend extends MovieResult{
    private MovieReviewBase reviews;
    private MovieVideoBase videos;

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
