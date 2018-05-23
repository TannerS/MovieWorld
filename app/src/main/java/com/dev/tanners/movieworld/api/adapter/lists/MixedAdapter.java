package com.dev.tanners.movieworld.api.adapter.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.model.MovieBase;
import com.dev.tanners.movieworld.api.model.list.results.MovieResult;
import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;
import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MixedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private Context mContext;
    private ArrayList<MovieBase> mMovieResults;
    // keep track of type of viewholder based on const
    public static final int REVIEW = 2;
    public static final int VIDEO = 4;
    // exception of object type string
    private final String INVALID_OBJ_MESSAGE = "Invalid list item";

    /**
     * @param mContext
     */
    public MixedAdapter(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * @param mContext
     * @param mItems
     */
    public MixedAdapter(@NonNull Context mContext, @NonNull ArrayList<MovieBase> mItems) {
        this.mContext = mContext;
        // add items and create list
        if(mItems != null)
            mMovieResults = new ArrayList<MovieBase>(mItems);
    }

    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<? extends MovieBase> mItems) {

        if(mItems == null)
            return;

        if(mMovieResults == null || mMovieResults.size() <= 0)
        {
            // create object
            mMovieResults = new ArrayList<MovieBase>();
            // want to get pos of last item in list
            int startPos = 0;
            // add new items to current adapter items
            this.mMovieResults.addAll(mItems);
            // update recyclerview at position 'startPos'
            notifyItemRangeInserted(startPos, mItems.size());
        }
        else {
            // want to get pos of last item in list
            int startPos = this.mMovieResults.size() + 1;
            // add new items to current adapter items
            this.mMovieResults.addAll(mItems);
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
        return mMovieResults == null ? 0 : mMovieResults.size();
    }

    /**
     * Bind views
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MixedAdapter.VideoViewHolder)
        {
            MovieVideo mItem = (MovieVideo) mMovieResults.get(position);
            // TODO setup view contents
        }
        else if(holder instanceof MixedAdapter.ReviewViewHolder)
        {
            MovieReview mItem = (MovieReview) mMovieResults.get(position);
        }
        else
            throw new IllegalArgumentException();

    }

    /**
     * Creates the views
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemLayout = null;

        switch(viewType) {
            case REVIEW:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
                return new ReviewViewHolder(mItemLayout);
            case VIDEO:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
                return new VideoViewHolder(mItemLayout);
            default:
                // TODO fine better way
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mMovieResults.get(position) instanceof MovieReview)
            return REVIEW;
        else if(mMovieResults.get(position) instanceof MovieVideo)
            return VIDEO;
        else
            return 404;
    }

    /**
     *  Holds the video view elements
     *
     * View holder to hold UI elements to be recycled
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constructor
         *
         * @param view
         */
        public VideoViewHolder(View view) {
            super(view);
        }
    }

    /**
     *  Holds the review view elements
     *
     * View holder to hold UI elements to be recycled
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constructor
         *
         * @param view
         */
        public ReviewViewHolder(View view) {
            super(view);
        }
    }
}


