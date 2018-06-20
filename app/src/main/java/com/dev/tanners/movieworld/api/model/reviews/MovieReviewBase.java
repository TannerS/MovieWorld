package com.dev.tanners.movieworld.api.model.reviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Base model for review result
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReviewBase {
    private ArrayList<MovieReview> results;

    /**
     *
     */
    public MovieReviewBase() {

    }

    /**
     * @return
     */
    public ArrayList<MovieReview> getResults() {
        return results;
    }

    /**
     * @param results
     */
    public void setResults(ArrayList<MovieReview> results) {
        this.results = results;
    }
}

