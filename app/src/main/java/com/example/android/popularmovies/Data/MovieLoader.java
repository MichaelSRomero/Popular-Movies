package com.example.android.popularmovies.Data;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmovies.Movie;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getName();

    /** Query URL*/
    private String mUrl;

    /**
     * @param context           of Activity
     * @param url               to load data from
     */
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Movie> movieList = QueryUtils.fetchMovieData(mUrl);
        return movieList;
    }
}
