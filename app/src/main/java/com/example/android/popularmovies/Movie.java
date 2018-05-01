package com.example.android.popularmovies;

public class Movie {
    private String mTitle;
    private String mThumbnail;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;

    public Movie(String title, String thumbnail, String overview,
                 String userRating, String releaseDate) {
        mTitle = title;
        mThumbnail = thumbnail;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
