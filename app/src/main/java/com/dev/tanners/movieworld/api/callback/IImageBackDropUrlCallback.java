package com.dev.tanners.movieworld.api.callback;

/**
 * Callback to format the url for the movie's image.
 * By default, the movie has relative path and needs to format the
 * url to properly have the url to view the image
 */
public interface IImageBackDropUrlCallback {
    /**
     * Format url before rest call
     * @param object
     * @return
     */
    public String formatUrl(String object);
}
