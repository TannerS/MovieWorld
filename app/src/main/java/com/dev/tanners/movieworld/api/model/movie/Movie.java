package com.dev.tanners.movieworld.api.model.movie;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoRoot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends MovieResult{

    public MovieReviewRoot getReviews() {
        return reviews;
    }

    public void setReviews(MovieReviewRoot reviews) {
        this.reviews = reviews;
    }

    public MovieVideoRoot getVideos() {
        return videos;
    }

    public void setVideos(MovieVideoRoot videos) {
        this.videos = videos;
    }

    private MovieReviewRoot reviews;
    private MovieVideoRoot videos;


}
