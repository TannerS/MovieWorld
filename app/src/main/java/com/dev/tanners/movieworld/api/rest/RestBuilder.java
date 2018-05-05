package com.dev.tanners.movieworld.api.rest;

import android.net.Uri;

import java.security.InvalidParameterException;

/**
 * A way to build the rest url's in one spot
 */
public class RestBuilder
{
    private static Uri.Builder setScheme(Uri.Builder mBuilder, String scheme)
    {
        // the following chunks of code handles missing parameters
        if(scheme != null && !scheme.isEmpty())
            mBuilder = mBuilder.scheme(scheme);
        // this is the only requirement
        else
            throw new InvalidParameterException();

        return mBuilder;
    }

    public static Uri buildUri(String scheme) {
        Uri.Builder mBuilder = new Uri.Builder();
        return setScheme(mBuilder, scheme).build();
    }

    /*
        Since java can't do parameters by name, we have to have one
        with path and query but we can check for what is missing
     */
    public static Uri buildUri(String scheme, String path, String query)
    {
        Uri.Builder mBuilder = new Uri.Builder();

        mBuilder = setScheme(mBuilder, scheme);

        if(path != null && !path.isEmpty())
            mBuilder = mBuilder.path(path);

        if(query != null && !query.isEmpty())
            mBuilder = mBuilder.query(query);

        return mBuilder.build();
    }
}
