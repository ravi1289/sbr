package com.flipkart.studio34;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class Podcast implements Parcelable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    private String url;


    public Podcast(Parcel in){
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
    }

    public static final Creator CREATOR = new Creator() {
        public Podcast createFromParcel(Parcel in) {
            return new Podcast(in);
        }

        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };
}
