package com.example.android.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.DetailActivity;
import com.example.android.popularmovies.activities.OverviewFragment;
import com.example.android.popularmovies.activities.ReviewFragment;
import com.example.android.popularmovies.activities.VideoFragment;
import com.example.android.popularmovies.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, userRating;
        public ImageView thumbnail;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            userRating = itemView.findViewById(R.id.tv_rating);
            thumbnail = itemView.findViewById(R.id.iv_backdrop);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

    public MovieAdapter(Context context, List<Movie> movieList) {

        this.mContext = context;
        this.mMovieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Movie currentMovie = mMovieList.get(position);

        holder.title.setText(currentMovie.getTitle());
        holder.userRating.setText(currentMovie.getUserRating());

        // Loads movie poster using Glide Library
        Glide.with(mContext)
                .load(currentMovie.getThumbnailPath())
                .into(holder.thumbnail);

        // Opens DetailActivity when clicked on movie thumbnail
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Passes Movie data into DetailActivity
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(Movie.MOVIE_KEY, currentMovie);

                view.getContext().startActivity(intent);

                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putParcelable(Movie.MOVIE_KEY, currentMovie);
                Log.v("MovieAdapter", "MOVIE ID = " + currentMovie.getId());

                // Passes "overview" into the OverviewFragment class
                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.setArguments(fragmentBundle);

                // Passes Movie data into the VideoFragment class
                VideoFragment videoFragment = new VideoFragment();
                videoFragment.setArguments(fragmentBundle);

                // Passes Movie data into the ReviewFragment class
                ReviewFragment reviewFragment = new ReviewFragment();
                reviewFragment.setArguments(fragmentBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    /**
     * Clears the Arraylist and notifies the layout that data has been changed
     */
    public void clearData(List<Movie> movies) {
        mMovieList.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds an Arraylist of Movie object and notifies the layout that data has been changed
     */
    public void addData(List<Movie> movies) {
        mMovieList.addAll(movies);
        notifyDataSetChanged();
    }
}
