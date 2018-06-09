//package com.dev.tanners.movieworld.api.support;
//
//import com.dev.tanners.movieworld.api.model.ListItem;
//import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
//
//import java.util.Comparator;
//
//public class VideoTypeAsc implements Comparator<ListItem> {
//    @Override
//    public int compare(ListItem mObject1, ListItem mObject12) {
//        if(mObject1 instanceof MovieVideo && mObject12 instanceof MovieVideo) {
//
//            return ((MovieVideo)mObject1).getType().compareTo(((MovieVideo)mObject12).getType());
//        }
//
//        throw new IllegalArgumentException("Not comparable object");
//    }
//}
