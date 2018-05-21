package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.ui.CategoryAdapter;

public class DetailActivity extends AppCompatActivity {

    public Movie mMovie;
    public String mTitle, mThumbnail, mOverview, mUserRating, mReleaseDate, mBackdropPath;
    public TextView mTitleView, mOverviewText, mRatingView, mReleaseDateView;
    public ImageView mThumbnailView, mBackdropView;

    public android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Enables back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager);
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Receive bundle from MainActivity
        // If bundle is not empty, then store all values within their respective String objects
        Bundle receiveBundle = getIntent().getExtras();
        if (!receiveBundle.isEmpty()) {
            mMovie = receiveBundle.getParcelable(Movie.MOVIE_KEY);

            mTitle = mMovie.getTitle();
            mThumbnail = mMovie.getThumbnail();
            mUserRating = mMovie.getUserRating();
            mReleaseDate = mMovie.getReleaseDate();
            mBackdropPath = mMovie.getBackdrop();
        }

        mTitleView = findViewById(R.id.tv_title);
        //mOverviewText = findViewById(R.id.tv_overview);
        mRatingView = findViewById(R.id.tv_rating);
        mReleaseDateView = findViewById(R.id.tv_release_date);
        mBackdropView = findViewById(R.id.iv_backdrop);
        mThumbnailView = findViewById(R.id.iv_poster_thumbnail);

        displayDetails();
        setUpAppBarLayout();
    }

    /**
     * Helper method to display details
     */
    public void displayDetails() {
        mTitleView.setText(mTitle);
        //mOverviewText.setText(mOverview);
        mRatingView.setText(mUserRating + getString(R.string.detail_10_stars));
        mReleaseDateView.setText(mReleaseDate);

        // Loads movie poster using Glide Library
        Glide.with(this)
                .load(mBackdropPath)
                .into(mBackdropView);
        Glide.with(this)
                .load(mThumbnail)
                .into(mThumbnailView);
    }

    /**
     * Displays and/or hides the Title of movie on AppbarLayout
     */
    private void setUpAppBarLayout() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setTitle(mTitle);
                    isShow = false;
                } else if (!isShow) {
                    getSupportActionBar().setTitle(" ");
                    isShow = true;
                }
            }
        });
    }
}
