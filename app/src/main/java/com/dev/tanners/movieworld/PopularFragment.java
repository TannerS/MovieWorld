package com.dev.tanners.movieworld;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tanners.movieworld.api.MovieApiHelper;

public class PopularFragment extends MovieFragment {
    public PopularFragment() {
        // let fragment know which state it is
        mState = State.POP;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PopularFragment.
     */
    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // load initial view
        loadList(MovieApiHelper.API_POPULAR);
        // return view
        return view;
    }
}
