package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.popularmovies.Data.QueryUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    private TextView mTestTextView;

    private final String MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestTextView = findViewById(R.id.test_textview);
        loadData();
    }

    /** Used for testing */
    private void loadData() {
        new FetchMovieTask().execute(MOVIE_URL);
    }

    /** Used for testing */
    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }

            String movieString = strings[0];
            List<Movie> movieList = QueryUtils.fetchMovieData(movieString);
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                for (int i = 0; i < movies.size(); i++) {
                    Movie currentMovie = movies.get(i);

                    String title = currentMovie.getTitle();
                    String overview = currentMovie.getOverview();
                    String releaseDate = currentMovie.getReleaseDate();
                    String userRating = currentMovie.getUserRating();

                    String movieData = title + "\n" +
                            overview + "\n" +
                            releaseDate + "\n" +
                            userRating + "\n";

                    mTestTextView.append(movieData + "\n\n\n");
                }
            }
        }
    }
}
