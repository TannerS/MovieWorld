package com.dev.tanners.movieworld.api.support.callbacks;

import com.dev.tanners.movieworld.ListItem;

import java.util.ArrayList;

/**
 * This is used in the retrofit enqueue call back
 * methods to process the data after being received
 * to avoid async issues
 */
public interface IRestDataStorageCallBack {
    public void processData(ArrayList<? extends ListItem> mData);
}
