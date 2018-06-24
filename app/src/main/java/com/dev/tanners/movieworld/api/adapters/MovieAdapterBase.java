package com.dev.tanners.movieworld.api.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Base adapter for common functionality
 */
public abstract class MovieAdapterBase<I> extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    protected Context mContext;
    protected List<I> mItems;

    /**
     * Constructor
     *
     * @param mContext
     * @param mItems
     */
    public MovieAdapterBase(Context mContext, ArrayList<I> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    /**
     *
     * Constructor
     * @param mContext
     */
    public MovieAdapterBase(Context mContext) {
        this.mContext = mContext;
        mItems = new ArrayList<>();
    }

    /**
     * Returns number of items in list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public List<I> getmItems() {
        return mItems;
    }

//    public void setmItems(List<I> mItems) {
//        this.mItems = mItems;
//    }
}



