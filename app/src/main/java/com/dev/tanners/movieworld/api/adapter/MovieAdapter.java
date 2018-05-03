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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private IImageBackDropUrlCallback mImageCallback;
    private ArrayList<MovieResult> mMovieResults;
    private IImageOnClickListener mImageOnClickListener;

    public MovieAdapter(Context mContext, ArrayList<MovieResult> mMovieResults, IImageBackDropUrlCallback mImageCallback, IImageOnClickListener mImageOnClickListener ) {
        this.mContext = mContext;
        this.mMovieResults = mMovieResults;
        this.mImageCallback = mImageCallback;
        this.mImageOnClickListener = mImageOnClickListener;
    }

    public void updateAdapter(ArrayList<MovieResult> mItems) {
        int startPos = this.mMovieResults.size() + 1;
        this.mMovieResults.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
    }

    private void loadImage(String mUrl, ImageView view) {
        Glide.with(mContext)
                .load(mUrl)
                .apply(new RequestOptions()
                        .centerCrop()
//                        .error()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }

    // TODO ******* CALL BACK, PASS THIS IN FROM MAIN ******
//    private String formatUrl(String mUrl)
//    {
//        return (new StringBuilder())
//                .append(MovieConfig.API_IMAGE_BASE)
//                .append("/")
//                .append(MovieConfig.API_IMAGE_SIZES.get(MovieConfig.LARGE))
//                .append("/")
//                .append(mUrl).toString();
//    }

    @Override
    public int getItemCount() {
        return mMovieResults == null ? 0 : mMovieResults.size();
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieResult mItem = mMovieResults.get(position);
        loadImage(this.mImageCallback.formatUrl(mItem.getPoster_path()), holder.image);
    }


    // TODO need to create grid view item id
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.id.activity_main_gridview_main_item, parent, false);
        return new MovieViewHolder(mContext, view);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        public MovieViewHolder(final Context mContext, View view) {
            super(view);
            view.setOnClickListener(this);
            //todo create imageview id inside grid item
            image = view.findViewById(R.id.);
        }

        @Override
        public void onClick(View v) {
            int mAdapterPos = getAdapterPosition();
            MovieResult mMovieObject = mMovieResults.get(mAdapterPos);
            mImageOnClickListener.onClick(mMovieObject);
        }

        // TODO **** this is callback to pass into it ****
//        try {
//            String mMovieObjectJson = JsonUtil.movieObjectToJson(mMovieObject);
//            Intent intent = new Intent(mContext, ****.class);
//            intent.putExtra(<CLASS>.<KEY>, mMovieObjectJson);
//            mContext.startActivity(intent);
//
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
    }
}


