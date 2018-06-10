package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.model.Movie;

public class MovieViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieViewModel(AppDatabase database, int movieId) {
        movie = database.favoriteDao().loadMovieById(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
