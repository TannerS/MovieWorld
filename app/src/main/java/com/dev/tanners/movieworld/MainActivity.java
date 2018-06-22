package com.dev.tanners.movieworld;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.dev.tanners.movieworld.fragments.MovieFragmentApi;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point
 */
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
        // load activity_toolbar
        setSupportActionBar( (Toolbar) findViewById(R.id.main_toolbar));
    }

    /**
     * Set up tabs with fragments
     */
    private void setUpFragments()
    {
        mViewPager = findViewById(R.id.main_viewpager);
        TabbedFragmentPagerAdapter mViewPagerAdapter = new TabbedFragmentPagerAdapter(getSupportFragmentManager());
        // Add Fragments to adapter one by one
        mViewPagerAdapter.addItem(MovieFragmentApi.newInstance(MovieFragmentApi.State.POP), "Popular");
        mViewPagerAdapter.addItem(MovieFragmentApi.newInstance(MovieFragmentApi.State.TOP), "Top Rated");
        // set adapter
        mViewPager.setAdapter(mViewPagerAdapter);
        // set tabs for fragments
        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.WvDfc9Yh0U4
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




