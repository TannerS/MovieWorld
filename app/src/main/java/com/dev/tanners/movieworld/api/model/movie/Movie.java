package com.dev.tanners.movieworld.api.model.movie;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoRoot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends MovieResult{
    private MovieReviewRoot reviews;
    private MovieVideoRoot videos;

    /**
     * @return
     */
    public MovieReviewRoot getReviews() {
        return reviews;
    }

    /**
     * @param reviews
     */
    public void setReviews(MovieReviewRoot reviews) {
        this.reviews = reviews;
    }

    /**
     * @return
     */
    public MovieVideoRoot getVideos() {
        return videos;
    }

    /**
     * @param videos
     */
    public void setVideos(MovieVideoRoot videos) {
        this.videos = videos;
    }
}
