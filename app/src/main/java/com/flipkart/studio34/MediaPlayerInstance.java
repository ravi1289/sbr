package com.flipkart.studio34;

import android.media.MediaPlayer;

/**
 * Created by ravi.krishnan on 25/02/16.
 */
public class MediaPlayerInstance {
    private static MediaPlayer mediaPlayer;
    public static void initialise(){
        mediaPlayer = new MediaPlayer();
    }

    public static void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer=null;
    }
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
