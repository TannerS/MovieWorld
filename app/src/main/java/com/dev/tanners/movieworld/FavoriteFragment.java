package com.dev.tanners.movieworld;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.tanners.movieworld.api.support.rest.methods.MovieApiPopular;

/**
 * Contains popular movies data
 */
public class FavoriteFragment extends MovieFragmentRoot {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoriteFragment.
     */
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    /**
     * Load methods after view has been loaded
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // return view
        return view;
    }
}
//
//
//  mDb.query(
//          MovieEntry.TABLE_NAME,
//          null,
//          null,
//          null,
//          null,
//          null,
//          MovieEntry.COLUMN_NAME_TIMESTAMP
//          );
