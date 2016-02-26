package com.flipkart.studio34;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class MetaDataAdapter extends ArrayAdapter<MediaMetaData>{
    private LayoutInflater inflater;

    public MetaDataAdapter(Context context, List<MediaMetaData> mediaMetaDataList) {
        super(context, R.layout.meta_layout, mediaMetaDataList);
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MetaDataAdapterWrapper metaDataAdapterWrapper = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.meta_layout, null);
            metaDataAdapterWrapper = new MetaDataAdapterWrapper();
            metaDataAdapterWrapper.startTimeView = (TextView) convertView.findViewById(R.id.startTimeId);
            metaDataAdapterWrapper.dataView = (TextView) convertView.findViewById(R.id.dataId);
            convertView.setTag(metaDataAdapterWrapper);
        } else {
            metaDataAdapterWrapper = (MetaDataAdapterWrapper) convertView.getTag();
        }
        MediaMetaData mediaMetaData = getItem(position);
        metaDataAdapterWrapper.startTimeView.setText(mediaMetaData.getStartTime() + "");
        metaDataAdapterWrapper.dataView.setText(mediaMetaData.getData());
        return convertView;
    }

    private class MetaDataAdapterWrapper {
        public TextView startTimeView;
        public TextView dataView;
    }

}
