package com.example.android.popularmovies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.AppExecutors;
import com.example.android.popularmovies.database.MovieViewModel;
import com.example.android.popularmovies.database.MovieViewModelFactory;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.ui.CategoryAdapter;

public class DetailActivity extends AppCompatActivity {

    public Movie mMovie;
    public String mTitle, mThumbnail, mUserRating, mReleaseDate, mBackdropPath;
    public TextView mTitleView, mRatingView, mReleaseDateView;
    public ImageView mThumbnailView, mBackdropView;

    // Constant used to check if Movie has been added to "favorites" database
    private static final int DEFAULT_MOVIE_ID = 0;

    // Member variable for the Database
    private AppDatabase mDb;
    private Boolean isFavorited = true;

    public android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getsInstance(getApplicationContext());

        toolbar = findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Enables back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager);
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Receive bundle from MainActivity
        // If bundle is not empty, then store all values within their respective String objects
        Bundle receiveBundle = getIntent().getExtras();
        if (!receiveBundle.isEmpty()) {
            mMovie = receiveBundle.getParcelable(Movie.MOVIE_KEY);
            mTitle = mMovie.getTitle();
            mThumbnail = mMovie.getThumbnailPath();
            mUserRating = mMovie.getUserRating();
            mReleaseDate = mMovie.getReleaseDate();
            mBackdropPath = mMovie.getBackdropPath();
        }

        mTitleView = findViewById(R.id.tv_title);
        mRatingView = findViewById(R.id.tv_rating);
        mReleaseDateView = findViewById(R.id.tv_release_date);
        mBackdropView = findViewById(R.id.iv_backdrop);
        mThumbnailView = findViewById(R.id.iv_poster_thumbnail);

        checkIsInDatabase();
        displayDetails();
        setUpAppBarLayout();
    }

    /**
     * Helper method to display details
     */
    public void displayDetails() {
        mTitleView.setText(mTitle);
        mRatingView.setText(mUserRating + getString(R.string.detail_10_stars));
        mReleaseDateView.setText(mReleaseDate);

        // Loads movie poster using Glide Library
        Glide.with(this)
                .load(mBackdropPath)
                .into(mBackdropView);
        Glide.with(this)
                .load(mThumbnail)
                .into(mThumbnailView);
    }

    /**
     * Displays and/or hides the Title of movie on AppbarLayout
     */
    private void setUpAppBarLayout() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setTitle(mTitle);
                    isShow = false;
                } else if (!isShow) {
                    getSupportActionBar().setTitle(" ");
                    isShow = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        // If movie ID doesn't match,
        // then it is inside the "favorites" database.
        // So we set the proper icon and variable "isFavorited" to false.
        if (mMovie.getId() != DEFAULT_MOVIE_ID) {
            MenuItem mFavoriteItem = menu.getItem(0);
            mFavoriteItem.setIcon(getResources().getDrawable(R.drawable.ic_favorited));
            isFavorited = false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentItem = item.getItemId();
        switch (currentItem) {
            case R.id.action_favorite:
                switchFavoriteIcon(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addToFavorites() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().insertMovie(mMovie);
            }
        });
    }

    public void removeFromFavorites() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().deleteMovie(mMovie);
            }
        });
    }

    public void switchFavoriteIcon(MenuItem item) {

        if (isFavorited) {
            Toast.makeText(this, R.string.detail_activity_added_favorite, Toast.LENGTH_SHORT).show();
            item.setIcon(getResources().getDrawable(R.drawable.ic_favorited));
            addToFavorites();
            isFavorited = false;
        } else {
            Toast.makeText(this, R.string.detail_activity_removed_favorite, Toast.LENGTH_SHORT).show();
            item.setIcon(getResources().getDrawable(R.drawable.ic_unfavorite));
            removeFromFavorites();
            isFavorited = true;
        }
    }

    /**
     * Queries database to check if movie exist.
     * If it exists, then it loads all movie info inside a global variable "mMovie".
     * Then "mMovie" can be used to set favorite icon properly.
     */
    public void checkIsInDatabase() {
        if (mMovie.getId() == DEFAULT_MOVIE_ID) {
            MovieViewModelFactory factory = new MovieViewModelFactory(mDb, mMovie.getMovieId());
            final MovieViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(MovieViewModel.class);
            viewModel.getMovie().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(@Nullable Movie movie) {
                    viewModel.getMovie().removeObserver(this);
                    if (movie != null) {
                        mMovie = movie;
                    }
                }
            });
        }
    }
}
