package com.dev.tanners.movieworld.api.util;

import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Util class for movie objects
 */
public class MovieUtil {
    /**
     * Convert <class>MovieResult</class> object to a json string
     *
     * @param mObj MovieResult with data filled from rest call
     * @return MovieResult object as string
     * @throws JsonProcessingException
     */
    public static String movieObjectToJson(MovieResult mObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mObj);
    }
}
