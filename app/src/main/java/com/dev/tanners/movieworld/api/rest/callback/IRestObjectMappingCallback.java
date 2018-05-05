package com.dev.tanners.movieworld.api.rest.callback;

import java.io.IOException;
import java.util.List;

public interface IRestObjectMappingCallback<E>
{
    /**
     * Create object <code>E</code> based on response rest request
     * @param response
     * @return
     * @throws IOException
     */
    public List<E> getMappedObject(String response) throws IOException;
}
