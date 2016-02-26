package com.flipkart.studio34;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sandesh.kumar on 26/02/16.
 */
public class PodcastAdapter extends BaseAdapter {

    List<Podcast> podcasts;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PodcastAdapterWrapper pod = (PodcastAdapterWrapper)v.getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.PODCAST_KEY,podcasts.get(pod.pos));
            Intent intent = new Intent();
            intent.setClass(context,MainActivity.class);
            intent.putExtras(bundle );
            context.startActivity(intent);
        }
    };;

    public PodcastAdapter(Context context, List<Podcast> podcasts) {
        this.podcasts = podcasts;
        this.context = context;
        inflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return podcasts.size();
    }

    @Override
    public Podcast getItem(int position) {
        return podcasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PodcastAdapterWrapper podcastAdapterWrapper;
        if(convertView == null) {
            podcastAdapterWrapper = new PodcastAdapterWrapper();
            convertView = inflater.inflate(R.layout.content_search_item, null);
            convertView.setOnClickListener(onClickListener);
            podcastAdapterWrapper.titleTextView = (TextView) convertView.findViewById(R.id.search_podcast_title);
            podcastAdapterWrapper.pos = position;
            convertView.setTag(podcastAdapterWrapper);
        } else {
            podcastAdapterWrapper = (PodcastAdapterWrapper) convertView.getTag();
        }
        podcastAdapterWrapper.titleTextView.setText(getItem(position).getName());
        return convertView;
    }

    public void query() {
        notifyDataSetChanged();
    }

    private class PodcastAdapterWrapper {
        int pos;
        public TextView titleTextView;
    }
}
