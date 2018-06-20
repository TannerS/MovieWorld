package com.dev.tanners.movieworld.api.model.videos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *  Model for videos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieVideo {
    private String id;
    private String key;
    private String name;
    private int size;

    /**
     * constructor
     */
    public MovieVideo() {
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
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }
}
