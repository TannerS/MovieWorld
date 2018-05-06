package com.dev.tanners.movieworld.api.callback;

import com.dev.tanners.movieworld.api.model.results.MovieResult;

/**
 * OnClick callback for movie adapter
 * This will handle clicking on the movies
 */
public interface IImageOnClickListener {
    /**
     * Onclick listener for movie object data
     * @param mMovieResult
     */
    public void onClick(MovieResult mMovieResult);
}
