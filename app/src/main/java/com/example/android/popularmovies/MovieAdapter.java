package com.example.android.popularmovies;

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

import java.util.List;

import static com.example.android.popularmovies.Data.Keys.OVERVIEW;
import static com.example.android.popularmovies.Data.Keys.RELEASE_DATE;
import static com.example.android.popularmovies.Data.Keys.THUMBNAIL;
import static com.example.android.popularmovies.Data.Keys.TITLE;
import static com.example.android.popularmovies.Data.Keys.USER_RATING;

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
            thumbnail = itemView.findViewById(R.id.iv_thumbnail);
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
        Log.e("TEST", "IMAGE URL: " + currentMovie.getThumbnail());

        // Loads movie poster using Glide Library
        Glide.with(mContext)
                .load(currentMovie.getThumbnail())
                .into(holder.thumbnail);

        // Opens DetailActivity when clicked on movie thumbnail
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();

                // Passes all Movie details into the DetailActivity screen
                bundle.putString(TITLE , currentMovie.getTitle());
                bundle.putString(THUMBNAIL, currentMovie.getLargeThumbnail());
                bundle.putString(OVERVIEW, currentMovie.getOverview());
                bundle.putString(USER_RATING, currentMovie.getUserRating());
                bundle.putString(RELEASE_DATE, currentMovie.getReleaseDate());
                intent.putExtras(bundle);

                view.getContext().startActivity(intent);
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
