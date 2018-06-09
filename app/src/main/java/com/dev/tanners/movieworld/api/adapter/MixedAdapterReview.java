package com.dev.tanners.movieworld.api.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;

import java.util.ArrayList;

/**
 * Adapter for the movie objects
 */
public class MixedAdapterReview extends MixedAdapterBase<MovieReview> {

    /**
     * @param mContext
     */
    public MixedAdapterReview(@NonNull Context mContext) {
        this.mContext = mContext;
        this.mItems = new ArrayList<MovieReview>();
    }

    /**
     * @param mContext
     * @param mItems
     */
    public MixedAdapterReview(@NonNull Context mContext, @NonNull ArrayList<MovieReview> mItems) {
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
        MovieReview mItem = mItems.get(position);
        ReviewViewHolder mHolder = ((ReviewViewHolder) holder);
        mHolder.mAuthor.setText(mItem.getAuthor());
        mHolder.mContent.setText(mItem.getContent());
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
        View mItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(mItemLayout);
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
}


