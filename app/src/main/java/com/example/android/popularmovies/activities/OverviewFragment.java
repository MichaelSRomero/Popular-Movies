package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;

public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }

    private TextView mOverview;
    public Movie mMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.overview_item, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(Movie.MOVIE_KEY);
        } else {
            Log.e("OVERVIEW", "Bundle = " + bundle);
        }

        mOverview = rootView.findViewById(R.id.tv_overview);
        if (mMovie != null) {
            mOverview.setText(mMovie.getOverview());
        } else {
            mOverview.setText("NULL");
        }

        return rootView;
        // Inflate "fragment_detail.xml"
    }
}
