package com.example.android.popularmovies.activities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.DataKeys;
import com.example.android.popularmovies.data.VideoLoader;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.ui.EmptyRecyclerView;
import com.example.android.popularmovies.ui.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Video>> {

    public VideoFragment() {
        // Required empty public constructor
    }

    private EmptyRecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private List<Video> mVideoList;
    public Movie mMovie;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private NetworkInfo mNetworkInfo = null;
    private static final int VIDEO_LOADER_ID = 1;

    /** String path to query for Videos */
    private final String VIDEO_PATH = "/videos?";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mMovie = bundle.getParcelable(Movie.MOVIE_KEY);
        }

        mRecyclerView = rootView.findViewById(R.id.fragment_recyclerview);
        // Set empty view //
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(mEmptyStateTextView);

        mVideoList = new ArrayList<>();
        mVideoAdapter = new VideoAdapter(rootView.getContext(), mVideoList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mVideoAdapter);

        checkNetworkConnectivity();

        // If there is a network connection, fetch data
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            getLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
        } else {
            // Else display error and hide loading indicator
            View loadingIndicator = rootView.findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        return rootView;

    }

    /**
     *
     * @return the mNetworkInfo either null or with a network
     */
    private NetworkInfo checkNetworkConnectivity() {
        // Get a reference to the Connectivity Manager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        mNetworkInfo = connMgr.getActiveNetworkInfo();

        return mNetworkInfo;
    }


    @Override
    public Loader<List<Video>> onCreateLoader(int id, Bundle args) {

        String movieID = Integer.toString(mMovie.getMovieId());
        String videoUrl = DataKeys.MOVIE_BASE_URL
                + movieID
                + VIDEO_PATH
                + DataKeys.API_KEY;

        return new VideoLoader(getActivity(), videoUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Video>> loader, List<Video> data) {

        View loadingIndicator = getActivity().findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        checkNetworkConnectivity();
        if (mNetworkInfo == null) {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            mEmptyStateTextView.setText(R.string.no_videos);
        }

        mVideoAdapter.clearData(data);

        if (data != null && !data.isEmpty()) {
            mVideoAdapter.addData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Video>> loader) {

        mVideoAdapter.clearData(mVideoList);
    }
}
