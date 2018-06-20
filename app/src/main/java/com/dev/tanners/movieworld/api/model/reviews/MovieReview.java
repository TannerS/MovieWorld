package com.dev.tanners.movieworld.api.model.reviews;

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

    /**
     * constructor
     */
    public MovieReview() {

    }

    /**
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
