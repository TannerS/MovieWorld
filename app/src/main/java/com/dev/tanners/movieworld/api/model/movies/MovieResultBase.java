package com.dev.tanners.movieworld.api.model.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

/**
 * Base model for movie search (popular and top rated movies)
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieResultBase {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<MovieResult> results;

    /**
     *
     */
    public MovieResultBase() {
    }

    /**
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return
     */
    public int getTotal_results() {
        return total_results;
    }

    /**
     * @param total_results
     */
    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    /**
     * @return
     */
    public int getTotal_pages() {
        return total_pages;
    }

    /**
     * @param total_pages
     */
    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    /**
     * @return
     */
    public ArrayList<MovieResult> getResults() {
        return results;
    }

    /**
     * @param results
     */
    public void setResults(ArrayList<MovieResult> results) {
        this.results = results;
    }

}