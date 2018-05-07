package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.Data.MovieLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    private static final int MOVIE_LOADER_ID = 1;

    /** Add your own API Key by accessing "https://developers.themoviedb.org" */
    private final String API_KEY = "api_key=";
    private final String MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mMovieList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(this, mMovieList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mMovieAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            getLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
        } // Add else method to display error and hide loading indicator
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, MOVIE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        // Add loading indicator,
        // clear adapter of previous data
        mMovieAdapter.clearData(movieList);

        // Add if/else statement,
        // if valid list exist, add them to bundle to trigger ListView to update;
        // else set empty state text
        if (movieList != null && !movieList.isEmpty()) {
            mMovieAdapter.addData(movieList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Reset loader to clear existing data
        mMovieAdapter.clearData(mMovieList);
    }
}
