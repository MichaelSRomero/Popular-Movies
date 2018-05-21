package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    /** Key to access and send data with **/
    public static final String MOVIE_KEY = "movie";

    private String mTitle;
    private String mThumbnail;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;
    private String mBackdrop;
    private int mMovieId;

    private final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/";
    private final String FILE_DEFAULT_SIZE = "w342";
    private final String FILE_LARGE_SIZE = "w780";

    public Movie(int id, String title, String thumbnail, String overview,
                 String userRating, String releaseDate, String backdrop) {
        mMovieId = id;
        mTitle = title;
        mThumbnail = thumbnail;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mBackdrop = backdrop;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mThumbnail = in.readString();
        mOverview = in.readString();
        mUserRating = in.readString();
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
        mMovieId = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getMovieId() {
        return mMovieId;
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

    public String getBackdrop() {
        return THUMBNAIL_BASE_URL + FILE_LARGE_SIZE + mBackdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mThumbnail);
        parcel.writeString(mOverview);
        parcel.writeString(mUserRating);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mBackdrop);
        parcel.writeInt(mMovieId);
        parcel.writeString(THUMBNAIL_BASE_URL);
        parcel.writeString(FILE_DEFAULT_SIZE);
        parcel.writeString(FILE_LARGE_SIZE);
    }
}
