package com.flipkart.studio34;

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

    List<String> podcasts;
    private LayoutInflater inflater;

    public PodcastAdapter(List<String> podcasts) {
        this.podcasts = podcasts;
    }
    @Override
    public int getCount() {
        return podcasts.size();
    }

    @Override
    public Object getItem(int position) {
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
            podcastAdapterWrapper.titleTextView = (TextView) convertView.findViewById(R.id.search_podcast_title);
            convertView.setTag(podcastAdapterWrapper);
        } else {
            podcastAdapterWrapper = (PodcastAdapterWrapper) convertView.getTag();
        }
        podcastAdapterWrapper.titleTextView.setText((String) getItem(position));
        return convertView;
    }

    private class PodcastAdapterWrapper {
        public TextView titleTextView;
    }
}
