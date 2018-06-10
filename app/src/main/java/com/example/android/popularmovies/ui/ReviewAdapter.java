package com.example.android.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> mReviewList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView reviewUser, reviewContent;

        public MyViewHolder(View itemView) {

            super(itemView);
            reviewUser = itemView.findViewById(R.id.review_username);
            reviewContent = itemView.findViewById(R.id.review_content);
        }
    }

    public ReviewAdapter(Context context, List<Review> reviewList) {
        mContext = context;
        mReviewList = reviewList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Review currentReview = mReviewList.get(position);

        holder.reviewUser.setText(currentReview.getAuthor());
        holder.reviewContent.setText(currentReview.geContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public void clearData(List<Review> reviews) {
        mReviewList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Review> reviews) {
        mReviewList.addAll(reviews);
        notifyDataSetChanged();
    }
}
