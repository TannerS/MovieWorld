package com.dev.tanners.movieworld.api.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.model.videos.MovieVideo;
import com.dev.tanners.movieworld.api.support.ThumbnailBuilder;
import com.dev.tanners.movieworld.api.support.VideoLinkBuilder;
import com.dev.tanners.movieworld.util.ImageDisplay;

import java.util.ArrayList;

/**
 * Adapter for the movie videos
 */
public class MovieAdapterVideo extends MovieAdapterGeneric<MovieVideo> {

    /**
     * @param mContext
     */
    public MovieAdapterVideo(@NonNull Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.mItems = new ArrayList<MovieVideo>();
    }

    /**
     * @param mContext
     * @param mItems
     */
    public MovieAdapterVideo(@NonNull Context mContext, @NonNull ArrayList<MovieVideo> mItems) {
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
        final MovieVideo mItem = mItems.get(position);
        VideoViewHolder mHolder = ((VideoViewHolder) holder);

        mHolder.getmVideoThumbnailTitle().setText(mItem.getName());

        mHolder.loadThumbnail(
                ThumbnailBuilder.buildThumbnail(
                        ThumbnailBuilder.Scheme.HTTPS,
                        mItem.getKey(),
                        ThumbnailBuilder.Thumbnail.HQ_FILENAME
                )
        );

        View.OnClickListener mVideoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW,
                                VideoLinkBuilder.buildVideoUrl(
                                        mItem.getKey()
                                )
                        )
                );
            }
        };

        // the image thumbnail may not be needed since play button overlaps it
        // but just in case
        mHolder.mVideoThumbnail.setOnClickListener(mVideoClickListener);
        mHolder.mVideoThumbnailPlay.setOnClickListener(mVideoClickListener);
        mHolder.mVideoThumbnailTitle.setOnClickListener(mVideoClickListener);
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

    /**
     * View holder for the video items
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVideoThumbnail;
        // only needed for onclick since it sits on top of the image
        private ImageView mVideoThumbnailPlay;
        private TextView mVideoThumbnailTitle;

        /**
         * Constructor
         *
         * @param view
         */
        public VideoViewHolder(View view) {
            super(view);

            mVideoThumbnail = view.findViewById(R.id.video_thumbnail);
            mVideoThumbnailTitle = view.findViewById(R.id.video_thumbnail_sub_title);
            mVideoThumbnailPlay = view.findViewById(R.id.video_thumbnail_play);
        }

        /**
         * Load image url into view
         * @param mUrl
         */
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

        public ImageView getmVideoThumbnailPlay() {
            return mVideoThumbnailPlay;
        }

        public void setmVideoThumbnailPlay(ImageView mVideoThumbnailPlay) {
            this.mVideoThumbnailPlay = mVideoThumbnailPlay;
        }

        /**
         * @return
         */
        public ImageView getmVideoThumbnail() {
            return mVideoThumbnail;
        }

        /**
         * @param mVideoThumbnail
         */
        public void setmVideoThumbnail(ImageView mVideoThumbnail) {
            this.mVideoThumbnail = mVideoThumbnail;
        }

        /**
         * @return
         */
        public TextView getmVideoThumbnailTitle() {
            return mVideoThumbnailTitle;
        }

        /**
         * @param mVideoThumbnailTitle
         */
        public void setmVideoThumbnailTitle(TextView mVideoThumbnailTitle) {
            this.mVideoThumbnailTitle = mVideoThumbnailTitle;
        }
    }
}


