package com.dev.tanners.movieworld.api.model.videos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Base model for videos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieVideoBase {
    private ArrayList<MovieVideo> results;

    /**
     *
     */
    public MovieVideoBase() {
    }

    /**
     * @return
     */
    public ArrayList<MovieVideo> getResults() {
        return results;
    }

    /**
     * @param results
     */
    public void setResults(ArrayList<MovieVideo> results) {
        this.results = results;
    }
}

