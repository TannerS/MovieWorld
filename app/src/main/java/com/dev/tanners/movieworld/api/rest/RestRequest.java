package com.dev.tanners.movieworld.api.rest;

import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.rest.callback.IRestObjectMappingCallback;
import com.dev.tanners.movieworld.network.ConnectionRequest;
import com.dev.tanners.movieworld.network.Request;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class RestRequest extends Request<MovieRoot, IRestObjectMappingCallback>
{
    @Override
    public List<MovieRoot> restJsonMapping(HashMap<String, String> headers, String mUrl, String body, IRestObjectMappingCallback mCallback) throws IOException {
        // temp list of images
        List<MovieRoot> mData = null;
        // make connect to url
            mConnectionRequest = new ConnectionRequest(mUrl);
        // if set body does not exist
        if (body != null && body.length() > 0) {
            // put body length
            headers.put("Content-Length", "" + body.getBytes().length);
            // set as post to deliver body content
            mConnectionRequest.setRequestType(ConnectionRequest.TYPES.POST);
            // set body
            mConnectionRequest.addBasicBody(body);
        }
        else
        {
            // no body, use get
            mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);
            // set empty body
            mConnectionRequest.addBasicBody("");
        }
        // put needed header
//        headers.put("Content-Language", "" + "en-US");
        mConnectionRequest.addRequestHeader(headers);
        // connect to url
        mConnectionRequest.connect();
        // get response
        if(mConnectionRequest.getConnection() != null) {
            try
            {
                // get string response
                String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream(), StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault()));
                // callback to handle any object that response matches too
                mData = mCallback.getMappedObject(response);
                // close connection
                mConnectionRequest.closeConnection();
            }
            catch(java.net.UnknownHostException ex)
            {
                return null;
            }
        }
        // return new photos
        return mData;
    }
}



