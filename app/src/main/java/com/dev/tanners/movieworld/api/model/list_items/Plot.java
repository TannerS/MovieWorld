package com.dev.tanners.movieworld.api.model.list_items;

import com.dev.tanners.movieworld.api.model.ListItem;

public class Plot extends ListItem {
//    private String mPlotTitle;
    private String mPlotSynopsis;

    public Plot() {
    }

//    public String getmPlotTitle() {
//        return mPlotTitle;
//    }

//    public void setmPlotTitle(String mPlotTitle) {
//        this.mPlotTitle = mPlotTitle;
//    }

    public String getmPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setmPlotSynopsis(String mPlotSynopsis) {
        this.mPlotSynopsis = mPlotSynopsis;
    }
}