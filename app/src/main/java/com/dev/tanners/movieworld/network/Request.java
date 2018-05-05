package com.dev.tanners.movieworld.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Base class that the caller can request a json mapper of a restful api call
 * This uses the ConnectRequest Class as the connection
 * @param <T, C>
 */
public abstract class Request<T, C>
{
    protected ConnectionRequest mConnectionRequest;
    public abstract List<T> restJsonMapping(HashMap<String, String> mHeaders, String mUrl, final String body, C mCallback) throws IOException;
}



