package com.example.android.popularmovies;

import android.net.Uri;

public class Movie {
    private String mTitle;
    private String mThumbnail;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;

    private final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/";
    private final String FILE_DEFAULT_SIZE = "w342";
    private final String FILE_LARGE_SIZE = "w780";

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
        return THUMBNAIL_BASE_URL + FILE_DEFAULT_SIZE + mThumbnail;
    }

    public String getLargeThumbnail() {
        return THUMBNAIL_BASE_URL + FILE_LARGE_SIZE + mThumbnail;
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

    private String buildImageUri(String thumbnailUrl) {
        Uri imageUri = Uri.parse(THUMBNAIL_BASE_URL).buildUpon()
                .appendPath(FILE_DEFAULT_SIZE)
                .appendPath(thumbnailUrl).build();

        return imageUri.toString();
    }
}
