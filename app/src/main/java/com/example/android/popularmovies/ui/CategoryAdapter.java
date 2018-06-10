package com.example.android.popularmovies.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.OverviewFragment;
import com.example.android.popularmovies.activities.ReviewFragment;
import com.example.android.popularmovies.activities.VideoFragment;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OverviewFragment();
        } else if (position == 1){
            return new VideoFragment();
        } else {
            return new ReviewFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.label_overview);
        } else if (position == 1){
            return mContext.getString(R.string.label_video);
        } else {
            return mContext.getString(R.string.label_review);
        }
    }
}
