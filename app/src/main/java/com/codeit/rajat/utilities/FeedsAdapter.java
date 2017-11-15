package com.codeit.rajat.utilities;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.codeit.rajat.objects.Feeds;
import com.codeit.rajat.vidbuddy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajat on 15/11/17.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private List<Feeds> feedsList;
    private Context context;

    public FeedsAdapter(List<Feeds> feedsList, Context context) {
        this.feedsList = feedsList;
        this.context = context;
    }

    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feeds_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.ViewHolder holder, int position) {
        Feeds feeds = feedsList.get(position);
        Picasso.with(context).load(feeds.getPhotoUri()).into(holder.userImg);
        holder.userName.setText(feeds.getUserName());
        holder.postTime.setText(feeds.getPostTime());
        holder.videoView.setVideoURI(feeds.getVideoUri());
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView userImg;
        public TextView userName;
        public TextView postTime;
        public VideoView videoView;


        public ViewHolder(View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.posted_by_photo);
            userName = itemView.findViewById(R.id.posted_by_name);
            postTime = itemView.findViewById(R.id.post_time);
            videoView = itemView.findViewById(R.id.feeds_video_view);
        }
    }
}
