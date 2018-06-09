package com.dev.tanners.movieworld.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.dev.tanners.movieworld.api.model.ListItem;

import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public abstract class MixedAdapterBase<I extends ListItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    protected Context mContext;
    protected ArrayList<I> mItems;

    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<I> mItems) {

        if(mItems != null)
        {
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
     * @param mItem
     */
    public void updateAdapter(I mItem) {

        if(mItem != null)
        {
                // want to get pos of last item in list
                int startPos = this.mItems.size() + 1;
                // add new items to current adapter items
                this.mItems.add(mItem);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, mItems.size());
        }
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

}



