package com.dev.tanners.movieworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.tanners.movieworld.api.MovieApiHelper;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.api.MovieApi;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity{
    // view pager to sliding fragments
    private ViewPager mViewPager;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFragments();
        // prevents shadow that messes up UI with tabs
        getSupportActionBar().setElevation(0);
    }

    /**
     * Set up tabs with fragments
     */
    private void setUpFragments()
    {
        mViewPager = findViewById(R.id.main_viewpager);
        TabbedFragmentPagerAdapter mViewPagerAdapter = new TabbedFragmentPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        mViewPagerAdapter.addItem(PopularFragment.newInstance(), "Popular");
        mViewPagerAdapter.addItem(TopRatedFragment.newInstance(), "Top Rated");

        mViewPager.setAdapter(mViewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.WvDfc9Yh0U4

     *
     * Adapter to handle fragments for the viewpager UI
     */
    private class TabbedFragmentPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mTitleList = new ArrayList<String>();

        /**
         * @param mFragmentManager
         */
        public TabbedFragmentPagerAdapter(FragmentManager mFragmentManager ) {
            super(mFragmentManager);
        }

        /**
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        /**
         * @return
         */
        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        /**
         * https://stackoverflow.com/questions/38049076/tablayout-tabs-text-not-displaying?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return  mTitleList.get(position);
        }

        /**
         * @param mFragment
         * @param mTitle
         */
        public void addItem(Fragment mFragment, String mTitle)
        {
            mFragmentList.add(mFragment);
            mTitleList.add(mTitle);
        }
    }


    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.credits_menu_item:
                // TODO make activity or dialog to give credits to any material that request it
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * https://developer.android.com/training/animation/screen-slide
     */
    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // on first fragment item, back should do super method
            super.onBackPressed();
        } else {
            // Otherwise go to previous fragment
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }
}




