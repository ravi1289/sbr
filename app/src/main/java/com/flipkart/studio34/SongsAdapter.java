package com.flipkart.studio34;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    private List<Podcast> podcasts;
    private Context context;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.PODCAST_KEY,podcasts.get(position));
            Intent intent = new Intent();
            intent.setClass(context,MainActivity.class);
            intent.putExtras(bundle );
            context.startActivity(intent);
        }
    };

    public SongsAdapter(List<Podcast> songs, Context context) {
        this.podcasts = songs;
        this.context = context;
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item,parent,false);
        return new SongsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        Podcast podcast = podcasts.get(position);
        holder.textView.setText(podcast.getName());
        holder.imageView.setImageResource(R.drawable.studio_34_bg);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return podcasts.size();
    }

    class SongsViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        SongsViewHolder(View v){
            super(v);
            textView = (TextView) v.findViewById(R.id.song_name_id);
            imageView = (ImageView) v.findViewById(R.id.song_thumb);
            imageView.setOnClickListener(onClickListener);
        }
    }
}
