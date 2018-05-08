package com.example.android.popularmovies;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.Data.Keys;

public class DetailActivity extends AppCompatActivity {

    public String mTitle, mThumbnail, mOverview, mUserRating, mReleaseDate;
    public TextView mTitleView, mOverviewText, mRatingView, mReleaseDateView;
    public ImageView mThumbnailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Enables back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setting Translucent color on App Bar
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(this, R.color.TranslucentColor)));

        // Removing ActionBar Title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Receive bundle from MainActivity
        // If bundle is not empty, then store all values within their respective String objects
        Bundle receiveBundle = getIntent().getExtras();
        if (!receiveBundle.isEmpty()) {
            mTitle = receiveBundle.getString(Keys.TITLE);
            mThumbnail = receiveBundle.getString(Keys.THUMBNAIL);
            mOverview = receiveBundle.getString(Keys.OVERVIEW);
            mUserRating = receiveBundle.getString(Keys.USER_RATING);
            mReleaseDate = receiveBundle.getString(Keys.RELEASE_DATE);
        }

        mTitleView = findViewById(R.id.tv_title);
        mOverviewText = findViewById(R.id.tv_overview);
        mRatingView = findViewById(R.id.tv_rating);
        mReleaseDateView = findViewById(R.id.tv_release_date);
        mThumbnailView = findViewById(R.id.iv_thumbnail);

        displayDetails();
    }

    /**
     * Helper method to display details
     */
    public void displayDetails() {
        mTitleView.setText(mTitle);
        mOverviewText.setText(mOverview);
        mRatingView.setText(mUserRating + " / 10");
        mReleaseDateView.setText(mReleaseDate);

        // Loads movie poster using Glide Library
        Glide.with(this)
                .load(mThumbnail)
                .into(mThumbnailView);
    }
}
