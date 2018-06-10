package com.dev.tanners.movieworld.api.model.videos;

import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Model for videos for each movie root
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieVideoRoot {
    private ArrayList<MovieVideo> results;

    /**
     *
     */
    public MovieVideoRoot() {
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

