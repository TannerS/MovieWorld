package com.dev.tanners.movieworld.api.callback;

import com.dev.tanners.movieworld.api.model.results.MovieResult;

/**
 * OnClick callback for movie adapter
 * This will handle clicking on the movies
 */
public interface IImageOnClickListener {
    public void onClick(MovieResult mMovieResult);
}
