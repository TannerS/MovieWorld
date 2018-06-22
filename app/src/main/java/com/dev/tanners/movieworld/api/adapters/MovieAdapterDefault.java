package com.dev.tanners.movieworld.api.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.util.ImageDisplay;

/**
 * Adapter for the movie objects
 */
public class MovieAdapterDefault extends MovieAdapterGeneric<MovieResult>{
    private OnClickListener mImageOnClickListener;

    public MovieAdapterDefault(@NonNull Context mContext, OnClickListener mImageOnClickListener) {
        super(mContext);
        this.mImageOnClickListener = mImageOnClickListener;
    }

    /**
     * Bind views
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder mHolder = (MovieViewHolder) holder;
        MovieResult mItem = mItems.get(position);
        mHolder.loadImage(MovieApi.formatPathToRestPath(mItem.getPoster_path(), MovieApi.MEDIUM));
    }

    /**
     *  Holds the view elements
     *
     * View holder to hold UI elements to be recycled
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        /**
         * Constructor
         *
         * @param view
         */
        public MovieViewHolder(View view) {
            super(view);


            // set grid item with a on click
            view.setOnClickListener(this);
            image = view.findViewById(R.id.grid_item_imageview);
            image.setClipToOutline(true);
        }

        /**
         * Onclick listener
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            int mAdapterPos = getAdapterPosition();
            MovieResult mMovieObject = mItems.get(mAdapterPos);
            mImageOnClickListener.onClick(mMovieObject);
        }

        /**
         * Load image from url into view
         *
         * @param mUrl
         */
        public void loadImage(String mUrl) {
            // call helper class to load image
            ImageDisplay.loadImage(mContext, mUrl, R.drawable.ic_error, this.image, R.drawable.ic_movie);
        }
    }

    /**
     * OnClick callback for images for movie adapter
     */
    public interface OnClickListener {
        /**
         * Onclick listener for movie object data
         * @param mMovieResult
         */
        public void onClick(MovieResult mMovieResult);
    }

    /**
     * Creates the views
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MovieViewHolder(view);
    }
}


