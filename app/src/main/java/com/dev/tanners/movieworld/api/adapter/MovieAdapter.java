package com.dev.tanners.movieworld.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.model.results.MovieResult;

import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private IImageBackDropUrlCallback mImageCallback;
    private ArrayList<MovieResult> mMovieResults;
    private IImageOnClickListener mImageOnClickListener;

    /**
     * @param mContext
     * @param mMovieResults
     * @param mImageCallback
     * @param mImageOnClickListener
     */
    public MovieAdapter(Context mContext, ArrayList<MovieResult> mMovieResults, IImageBackDropUrlCallback mImageCallback, IImageOnClickListener mImageOnClickListener ) {
        this.mContext = mContext;
        this.mMovieResults = mMovieResults;
        this.mImageCallback = mImageCallback;
        this.mImageOnClickListener = mImageOnClickListener;
    }

    /**
     * @param mItems
     */
    public void updateAdapter(ArrayList<MovieResult> mItems) {
        int startPos = this.mMovieResults.size() + 1;
        this.mMovieResults.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mMovieResults == null ? 0 : mMovieResults.size();
    }


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieResult mItem = mMovieResults.get(position);
        holder.loadImage(this.mImageCallback.formatUrl(mItem.getPoster_path()));
    }

    /**
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
     *
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        /**
         * @param view
         */
        public MovieViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            image = view.findViewById(R.id.grid_item_imageview);
            image.setClipToOutline(true);
        }

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            int mAdapterPos = getAdapterPosition();
            MovieResult mMovieObject = mMovieResults.get(mAdapterPos);
            mImageOnClickListener.onClick(mMovieObject);
        }

        /**
         *  Got the idea of putting this method in here from
         *  https://willowtreeapps.com/ideas/android-fundamentals-working-with-the-recyclerview-adapter-and-viewholder-pattern/
         * @param mUrl
         */
        public void loadImage(String mUrl) {
            Glide.with(mContext)
                    .load(mUrl)
                    .apply(new RequestOptions().fitCenter()

//                            .centerCrop().fitCenter()
//                        .error()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(this.image);
        }
    }
}


