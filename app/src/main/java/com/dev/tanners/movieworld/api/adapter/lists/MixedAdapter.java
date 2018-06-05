package com.dev.tanners.movieworld.api.adapter.lists;

/*
    TODO eventually, instead of item_divider, maybe use https://www.bignerdranch.com/blog/a-view-divided-adding-dividers-to-your-recyclerview-with-itemdecoration/
 */


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
import com.dev.tanners.movieworld.ListItem;
import com.dev.tanners.movieworld.api.model.list_items.Divider;
import com.dev.tanners.movieworld.api.model.list_items.Header;
import com.dev.tanners.movieworld.api.model.list_items.Plot;
import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;
import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
import com.dev.tanners.movieworld.api.support.rest.lists.MovieApiList;
import com.dev.tanners.movieworld.util.ImageDisplay;

import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MixedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private Context mContext;
    private ArrayList<ListItem> mDetailsItems;
    // keep track of type of viewholder based on const
    public static final int REVIEW = 2;
    public static final int VIDEO = 4;
    public static final int DIVIDER = 6;
    public static final int HEADER = 8;
    public static final int PLOT = 10;

    /**
     * @param mContext
     */
    public MixedAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
        mDetailsItems = new ArrayList<ListItem>();
    }

    /**
     * @param mContext
     * @param mItems
     */
    public MixedAdapter(@NonNull Context mContext, @NonNull ArrayList<ListItem> mItems) {
        this(mContext);
        this.mContext = mContext;
        // add items and create list
        if(mItems != null)
            mDetailsItems.addAll(mItems);
    }

    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<? extends ListItem> mItems) {

        if(mItems != null)
        {
            if(mDetailsItems.size() == 0)
            {
                Log.i("ADAPTER 1", String.valueOf(mItems.size()));
                // create object
    //            mDetailsItems = new ArrayList<ListItem>();
                // want to get pos of last item in list
                int startPos = 0;
                // add new items to current adapter items
                this.mDetailsItems.addAll(mItems);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, mDetailsItems.size());
            }
            else {
                Log.i("ADAPTER 2", String.valueOf(mItems.size()));


                // want to get pos of last item in list
                int startPos = this.mDetailsItems.size() + 1;
                // add new items to current adapter items
                this.mDetailsItems.addAll(mItems);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, mDetailsItems.size());
            }
        }
    }


    /**
     * Update adapter with new data, or start data
     *
     * @param mItems
     */
    public void updateAdapter(ListItem mItem) {

        if(mItem != null)
        {
                // want to get pos of last item in list
                int startPos = this.mDetailsItems.size() + 1;
                // add new items to current adapter items
                this.mDetailsItems.add(mItem);
                // update recyclerview at position 'startPos'
                notifyItemRangeInserted(startPos, mDetailsItems.size());
        }
    }

    /**
     * Returns number of items in list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDetailsItems == null ? 0 : mDetailsItems.size();
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
            MovieVideo mItem = (MovieVideo) mDetailsItems.get(position);
            VideoViewHolder mHolder = ((VideoViewHolder) holder);
        }
        else if(holder instanceof MixedAdapter.ReviewViewHolder)
        {
            MovieReview mItem = (MovieReview) mDetailsItems.get(position);
            ReviewViewHolder mHolder = ((ReviewViewHolder) holder);
            mHolder.mAuthor.setText(mItem.getAuthor());
            mHolder.mContent.setText(mItem.getContent());
        }
        else if(holder instanceof MixedAdapter.HeaderViewHolder)
        {
            Header mItem = (Header) mDetailsItems.get(position);
            HeaderViewHolder mHolder = ((HeaderViewHolder) holder);
            mHolder.loadBanner(mItem.getmBannerUrl());
            // TODO fix later
            mHolder.mRating.setText(mItem.getmRating() + " / 10");
            mHolder.mReleaseDate.setText(mItem.getmReleaseDate());
        }
        else if(holder instanceof MixedAdapter.SynopsisViewHolder)
        {
            Plot mItem = (Plot) mDetailsItems.get(position);
            SynopsisViewHolder mHolder = ((SynopsisViewHolder) holder);
            mHolder.mPlotSynopsis.setText(mItem.getmPlotSynopsis());
//            mHolder.mPlotTitle.setText(mItem.getmPlotTitle());
        }
        else if(holder instanceof MixedAdapter.DividerViewHolder)
        {
//            Divider mItem = (Divider) mDetailsItems.get(position);
            // no data to bind, just need layout

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
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
                return new ReviewViewHolder(mItemLayout);
            case VIDEO:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
                return new VideoViewHolder(mItemLayout);
            case DIVIDER:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
                return new DividerViewHolder(mItemLayout);
            case HEADER:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(mItemLayout);
            case PLOT:
                mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plot, parent, false);
                return new SynopsisViewHolder(mItemLayout);
            default:
                // TODO fine better way
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mDetailsItems.get(position) instanceof MovieReview)
            return REVIEW;
        else if(mDetailsItems.get(position) instanceof MovieVideo)
            return VIDEO;
        else if(mDetailsItems.get(position) instanceof Divider)
            return DIVIDER;
        else if(mDetailsItems.get(position) instanceof Header)
            return HEADER;
        else if(mDetailsItems.get(position) instanceof Plot)
            return PLOT;
        else
            return -1;
    }

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

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView mAuthor;
        private TextView mContent;
        private TextView mReadMore;

        public TextView getmAuthor() {
            return mAuthor;
        }

        public void setmAuthor(TextView mAuthor) {
            this.mAuthor = mAuthor;
        }

        public TextView getmContent() {
            return mContent;
        }

        public void setmContent(TextView mContent) {
            this.mContent = mContent;
        }

        public TextView getmReadMore() {
            return mReadMore;
        }

        public void setmReadMore(TextView mReadMore) {
            this.mReadMore = mReadMore;
        }

        /**
         * Constructor
         *
         * @param view
         */
        public ReviewViewHolder(View view) {
            super(view);

            mAuthor = view.findViewById(R.id.review_author);
            mContent = view.findViewById(R.id.review_content);
            mReadMore = view.findViewById(R.id.review_readmore);
            mReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO intent to open browser here
                }
            });
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mRating;
        private TextView mReleaseDate;
        private View view;

        public HeaderViewHolder(View view) {
            super(view);
            this.view = view;

            mRating = view.findViewById(R.id.rating);
            mReleaseDate = view.findViewById(R.id.release_date);
        }

        private void loadBanner(String mBackDropPath)
        {
            // call helper method to load images
            // since image is passed in as relative path
            // and not absolute
            ImageDisplay.loadImage(
                    mContext,
                    MovieApiList.formatPathToRestPath(
                            mBackDropPath,
                            MovieApiList.MEDIUM
                    ),
                    R.drawable.ic_error,
                    (ImageView) this.view.findViewById(R.id.backsplash_image));
        }
    }

    public class SynopsisViewHolder extends RecyclerView.ViewHolder {
        private TextView mPlotSynopsis;
//        private TextView mPlotTitle;

        public SynopsisViewHolder(View view) {
            super(view);

            mPlotSynopsis = view.findViewById(R.id.plot_synopsis);
//            mPlotTitle = view.findViewById(R.id.plot_synopsis_title);
        }
    }

    public class DividerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;

        public DividerViewHolder(View view) {
            super(view);

            mTitle = view.findViewById(R.id.section_title);
        }
    }
}


