package com.dev.tanners.movieworld.api.model.list_items;

import com.dev.tanners.movieworld.ListItem;

public class Header extends ListItem {
    private String mBannerUrl;
    private String mRating;
    private String mReleaseDate;


    public Header() {
    }

    public String getmBannerUrl() {
        return mBannerUrl;
    }

    public void setmBannerUrl(String mBannerUrl) {
        this.mBannerUrl = mBannerUrl;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }
}
