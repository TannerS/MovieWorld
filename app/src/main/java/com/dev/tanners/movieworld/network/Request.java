package com.dev.tanners.movieworld.network;

import java.io.IOException;
import java.net.URL;
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

    /**
     * Json mapping rest request class
     *
     * @param mHeaders
     * @param mUrl
     * @param body
     * @param mCallback
     * @return
     * @throws IOException
     */
    public abstract T restJsonMapping(HashMap<String, String> mHeaders, URL mUrl, final String body, C mCallback) throws IOException;
}



