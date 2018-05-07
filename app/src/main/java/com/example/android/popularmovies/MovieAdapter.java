package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, userRating;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            userRating = itemView.findViewById(R.id.tv_rating);
            thumbnail = itemView.findViewById(R.id.iv_thumbnail);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Movie currentMovie = mMovieList.get(position);

        holder.title.setText(currentMovie.getTitle());
        holder.userRating.setText(currentMovie.getUserRating());
        Log.e("TEST", "IMAGE URL: " + currentMovie.getThumbnail());

        // Loads movie poster using Glide Library
        Glide.with(mContext)
                .load(currentMovie.getThumbnail())
                .into(holder.thumbnail);
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
