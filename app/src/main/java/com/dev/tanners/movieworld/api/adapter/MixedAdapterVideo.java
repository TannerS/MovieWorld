package com.dev.tanners.movieworld.api.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
import com.dev.tanners.movieworld.api.support.ThumbnailBuilder;
import com.dev.tanners.movieworld.util.ImageDisplay;

import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MixedAdapterVideo extends MixedAdapterBase<MovieVideo> {

    /**
     * @param mContext
     */
    public MixedAdapterVideo(@NonNull Context mContext) {
        this.mContext = mContext;
        this.mItems = new ArrayList<MovieVideo>();
    }

    /**
     * @param mContext
     * @param mItems
     */
    public MixedAdapterVideo(@NonNull Context mContext, @NonNull ArrayList<MovieVideo> mItems) {
        this(mContext);
        this.mContext = mContext;
        // add items and create list
        if(mItems != null)
            this.mItems.addAll(mItems);
    }

    /**
     * Bind views
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieVideo mItem = mItems.get(position);
        VideoViewHolder mHolder = ((VideoViewHolder) holder);

        Log.i("URL",  ThumbnailBuilder.buildThumbnail(
                ThumbnailBuilder.Scheme.HTTPS,
                mItem.getKey(),
                ThumbnailBuilder.Thumbnail.HQ_FILENAME
        ));

//            mHolder.getmVideoType().setText(mItem.getType());

        mHolder.getmVideoThumbnailTitle().setText(mItem.getName());


        mHolder.loadThumbnail(
                ThumbnailBuilder.buildThumbnail(
                        ThumbnailBuilder.Scheme.HTTPS,
                        mItem.getKey(),
                        ThumbnailBuilder.Thumbnail.HQ_FILENAME
                )
        );
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
        View mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(mItemLayout);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVideoThumbnail;
        private TextView mVideoThumbnailTitle;
        private TextView mVideoType;

        /**
         * Constructor
         *
         * @param view
         */
        public VideoViewHolder(View view) {
            super(view);

            mVideoThumbnail = view.findViewById(R.id.video_thumbnail);
            mVideoThumbnailTitle = view.findViewById(R.id.video_thumbnail_sub_title);
//            mVideoType = view.findViewById(R.id.video_thumbnail_type);
        }

        public void loadThumbnail(String mUrl)
        {
            ImageDisplay
                    .loadImage(mContext,
                            mUrl,
                            R.drawable.ic_error,
                            this.mVideoThumbnail,
                            R.drawable.ic_baseline_play_arrow_48px
                    );
        }

        public ImageView getmVideoThumbnail() {
            return mVideoThumbnail;
        }

        public void setmVideoThumbnail(ImageView mVideoThumbnail) {
            this.mVideoThumbnail = mVideoThumbnail;
        }

        public TextView getmVideoThumbnailTitle() {
            return mVideoThumbnailTitle;
        }

        public void setmVideoThumbnailTitle(TextView mVideoThumbnailTitle) {
            this.mVideoThumbnailTitle = mVideoThumbnailTitle;
        }

        public TextView getmVideoType() {
            return mVideoType;
        }

        public void setmVideoType(TextView mVideoType) {
            this.mVideoType = mVideoType;
        }
    }
}


