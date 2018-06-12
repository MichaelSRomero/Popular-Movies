package com.example.android.popularmovies.activities;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.DataKeys;
import com.example.android.popularmovies.data.MovieLoader;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.model.MainViewModel;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.ui.EmptyRecyclerView;
import com.example.android.popularmovies.ui.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    public AppDatabase mDatabase;
    public EmptyRecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private String mOrderBy;
    public final String FAVORITES = "favorite";


    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private NetworkInfo mNetworkInfo = null;
    private static final int MOVIE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        // Sets empty view
        mEmptyStateTextView = findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(mEmptyStateTextView);

        mMovieList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(this, mMovieList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mMovieAdapter);

        mDatabase = AppDatabase.getsInstance(getApplicationContext());

        checkNetworkConnectivity();
        returnOrderByPreference();

        if (mOrderBy.equals(FAVORITES)) {
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            setupViewModel();
        } else {
            // If there is a network connection, fetch data
            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                getLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
            } else {
                // Else display error and hide loading indicator
                View loadingIndicator = findViewById(R.id.progress_bar);
                loadingIndicator.setVisibility(View.GONE);

                mEmptyStateTextView.setText(R.string.no_internet_connection);
            }
        }
    }

    /**
     * Gets called whenever the "Order-by" settings is changed.
     * Value gets changed between "popular?" and "top-rated?"
     */
    private void returnOrderByPreference() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mOrderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        String movieUrl = DataKeys.MOVIE_BASE_URL
                + mOrderBy
                + DataKeys.API_KEY;

        return new MovieLoader(this, movieUrl);
    }

    /**
     *
     * @return the mNetworkInfo either null or with a network
     */
    private NetworkInfo checkNetworkConnectivity() {
        // Get a reference to the Connectivity Manager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        mNetworkInfo = connMgr.getActiveNetworkInfo();

        return mNetworkInfo;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {

        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        checkNetworkConnectivity();

        if (mNetworkInfo == null) {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            mEmptyStateTextView.setText(R.string.no_data);
        }

        // clear adapter of previous data
        mMovieAdapter.clearData(movieList);

        if (movieList != null && !movieList.isEmpty()) {
            mMovieAdapter.addData(movieList);
        }

        if (!mMovieList.isEmpty()) {
            Log.v(LOG_TAG, "MOVIE ID NAME = " + mMovieList.get(0).getTitle());
            Log.v(LOG_TAG, "MOVIE ID = " + mMovieList.get(0).getId());
            Log.v(LOG_TAG, "MOVIE ID NAME = " + mMovieList.get(1).getTitle());
            Log.v(LOG_TAG, "MOVIE ID = " + mMovieList.get(1).getId());
        } else {
            Log.v(LOG_TAG, "MOVIE ID = UNAVAILABLE");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Reset loader to clear existing data
        mMovieAdapter.clearData(mMovieList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mMovieAdapter.addData(movies);
                if (!mMovieList.isEmpty()) {
                    Log.v(LOG_TAG, "MOVIE ID NAME = " + mMovieList.get(0).getTitle());
                    Log.v(LOG_TAG, "MOVIE ID = " + mMovieList.get(0).getId());
                } else {
                    Log.v(LOG_TAG, "MOVIE ID = UNAVAILABLE");
                }
            }
        });
    }
}
