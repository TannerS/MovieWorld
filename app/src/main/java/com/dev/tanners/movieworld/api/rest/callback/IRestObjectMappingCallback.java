package com.dev.tanners.movieworld.api.rest.callback;

import java.io.IOException;
import java.util.List;

/**
 * Callback for converting any object to a json string.
 * This will be used to handle object mapping to json
 * in the <class>Request</class> class for any objects
 *
 * @param <E>
 */
public interface IRestObjectMappingCallback<E>
{
    /**
     * Create object <code>E</code> based on response rest request
     * @param response
     * @return
     * @throws IOException
     */
    public E getMappedObject(String response) throws IOException;
}
