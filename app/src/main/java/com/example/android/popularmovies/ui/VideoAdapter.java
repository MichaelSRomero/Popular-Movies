package com.example.android.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context mContext;
    private List<Video> mVideoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public ImageView videoThumbnail;
        public TextView videoTitle, videoWebsite, videoSize;

        public MyViewHolder(View itemView) {

            super(itemView);
            linearLayout = itemView.findViewById(R.id.video_linear_layout);
            videoThumbnail = itemView.findViewById(R.id.video_url_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoWebsite = itemView.findViewById(R.id.video_site);
            videoSize = itemView.findViewById(R.id.video_size);
        }
    }

    public VideoAdapter(Context context, List<Video> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Video currentVideo = mVideoList.get(position);
        String videoSizeString = Integer.toString(currentVideo.getVideoSize());

        holder.videoTitle.setText(currentVideo.getVideoName());
        holder.videoWebsite.setText(currentVideo.getVideoSite());
        holder.videoSize.setText(videoSizeString);

        // Loads movie poster using Glide Library
        Glide.with(mContext)
                .load(currentVideo.getThumbnail())
                .into(holder.videoThumbnail);

        // Opens up YouTube video link
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoUrl = currentVideo.getVideo();
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(videoUrl));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    /**
     * Clears the Arraylist and notifies the layout that data has been changed
     */
    public void clearData(List<Video> videos) {
        mVideoList.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds an Arraylist of Video object and notifies the layout that data has been changed
     */
    public void addData(List<Video> videos) {
        mVideoList.addAll(videos);
        notifyDataSetChanged();
    }
}
