package com.dev.tanners.movieworld.api.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.util.ImageDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the movie objects
 */
public class MovieAdapterGeneric<I> extends MovieAdapterBase<I>{

    /**
     * @param mContext
     */
    public MovieAdapterGeneric(@NonNull Context mContext) {
        super(mContext);
    }

    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapterAdded(List<I> mItems) {
        // check for null
        if(mItems != null && this.mItems != null)
        {
            // check if fresh start of data
            if(this.mItems.size() == 0)
            {
                // want to get pos of last item in list
                int startPos = 0;
                // add new items to current adapter items
                this.mItems.addAll(mItems);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, this.mItems.size());
            }
            else {
                // want to get pos of last item in list
                int startPos = this.mItems.size() + 1;
                // add new items to current adapter items
                this.mItems.addAll(mItems);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, this.mItems.size());
            }
        }
    }

    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapterRemovedOrAdded(List<I> mItems) {
        // check for data
        if(mItems != null && this.mItems != null)
        {
            // since we can't tell the data's position it was removed from, we will jsut load all items
            this.mItems = mItems;
            // update recyclerview
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // must override
    }
}
