package com.dev.tanners.movieworld.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.MovieApiHelper;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.util.ImageDisplay;
import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private ArrayList<MovieResult> mMovieResults;
    private IImageOnClickListener mImageOnClickListener;

    /**
     * @param mContext
     * @param mMovieResults
     * @param mImageOnClickListener
     */
    public MovieAdapter(Context mContext, ArrayList<MovieResult> mMovieResults, IImageOnClickListener mImageOnClickListener ) {
        this.mContext = mContext;
        this.mMovieResults = mMovieResults;
        this.mImageOnClickListener = mImageOnClickListener;
    }

    /**
     * Update adapter with new data
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<MovieResult> mItems) {
        int startPos = this.mMovieResults.size() + 1;
        this.mMovieResults.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
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
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieResult mItem = mMovieResults.get(position);
        holder.loadImage(MovieApiHelper.formatPathToRestPath(mItem.getPoster_path(), MovieApiHelper.MEDIUM));
    }

    /**
     * Creates the views
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MovieViewHolder(view);
    }

    /**
     *  Holds the view elements
     *
     * VIew holder to hold UI elements to be recycled
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
            MovieResult mMovieObject = mMovieResults.get(mAdapterPos);
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
    public interface IImageOnClickListener {
        /**
         * Onclick listener for movie object data
         * @param mMovieResult
         */
        public void onClick(MovieResult mMovieResult);
    }
}


