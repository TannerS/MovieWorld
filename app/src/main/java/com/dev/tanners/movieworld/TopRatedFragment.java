package com.dev.tanners.movieworld;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tanners.movieworld.api.MovieApiHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopRatedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopRatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends MovieFragment {
    public TopRatedFragment() {
        // let fragment know which state it is
        mState = State.TOP;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopRatedFragment.
     */
    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // load initial view
        loadList(MovieApiHelper.API_TOP);
        // return view
        return view;
    }
}
