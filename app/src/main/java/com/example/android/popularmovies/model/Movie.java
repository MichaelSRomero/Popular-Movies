package com.example.android.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorites")
public class Movie implements Parcelable {

    /** Key to access and send data with **/
    @Ignore
    public static final String MOVIE_KEY = "movie";

    @PrimaryKey (autoGenerate = true)
    private int mId;
    private String mTitle;
    private String mThumbnail;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;
    private String mBackdrop;
    private int mMovieId;

    @Ignore
    private final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/";
    @Ignore
    private final String FILE_DEFAULT_SIZE = "w342";
    @Ignore
    private final String FILE_LARGE_SIZE = "w780";

    /*
     * Constructor that Room will be using
     */
    public Movie(int id, int movieId, String title, String thumbnail, String overview,
                 String userRating, String releaseDate, String backdrop) {
        mId = id;
        mMovieId = movieId;
        mTitle = title;
        mThumbnail = thumbnail;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mBackdrop = backdrop;
    }

    @Ignore
    public Movie(int movieId, String title, String thumbnail, String overview,
                 String userRating, String releaseDate, String backdrop) {
        mMovieId = movieId;
        mTitle = title;
        mThumbnail = thumbnail;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mBackdrop = backdrop;
    }

    @Ignore
    protected Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mThumbnail = in.readString();
        mOverview = in.readString();
        mUserRating = in.readString();
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
        mMovieId = in.readInt();
    }

    @Ignore
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

//    public void setId(int id) {
//        mId = id;
//    };

    public int getId() {
        return mId;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Ignore
    public String getThumbnailPath() {
        return THUMBNAIL_BASE_URL + FILE_DEFAULT_SIZE + mThumbnail;
    }

    /** Used for Database */
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

    @Ignore
    public String getBackdropPath() {
        return THUMBNAIL_BASE_URL + FILE_LARGE_SIZE + mBackdrop;
    }

    /** Used for Database */
    public String getBackdrop() {
        return mBackdrop;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
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
