package com.dev.tanners.movieworld.api.Util;

import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static String movieObjectToJson(MovieResult mObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mObj);
    }
}
