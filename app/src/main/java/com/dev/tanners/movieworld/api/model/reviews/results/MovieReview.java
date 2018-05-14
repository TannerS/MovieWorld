package com.dev.tanners.movieworld.api.model.reviews.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model for review result
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReview {
    private String author;
    private String content;
    private String id;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MovieReview() {

    }
}
