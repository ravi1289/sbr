package com.flipkart.studio34;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

/**
 * Created by ravi.krishnan on 25/02/16.
 */
public class AudioService extends Service implements MediaPlayer.OnCompletionListener{

    private String currentSongUrl;
    private boolean isResetDone=false;

    @Override
    public void onCreate() {
        super.onCreate();
        MediaPlayerInstance.initialise();
        MediaPlayerInstance.getMediaPlayer().setOnCompletionListener(this);
        MediaPlayerInstance.getMediaPlayer().setAudioStreamType(AudioManager.STREAM_MUSIC);
        MediaPlayerInstance.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                play();
                Log.d("MediaPlayer", mp.getDuration() +"");
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String url = intent.getStringExtra(Constants.URL_KEY);
        boolean fromSplash=intent
                .getBooleanExtra(Constants.FROM_SPLASH, false);
        if(!fromSplash) {
            if (url!=null &&
                    !url.equals(currentSongUrl)) {
                MediaPlayerInstance.getMediaPlayer().stop();
                MediaPlayerInstance.getMediaPlayer().reset();
                isResetDone = true;
                currentSongUrl = url;
                playSong(url);
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void onEvent(StartSongEvent startSongEvent){

        if( MediaPlayerInstance.getMediaPlayer().isPlaying()){
            pause();
        }else{
            play();
        }

    }

    private void play(){
        MediaPlayerInstance.getMediaPlayer().start();
        EventBus.getDefault().post(new CurrentSongStateEvent(Constants.PLAY));

    }

    private void pause(){
        MediaPlayerInstance.getMediaPlayer().pause();
        EventBus.getDefault().post(new CurrentSongStateEvent(Constants.PAUSE));

    }
    private void playSong(String url){
        if( MediaPlayerInstance.getMediaPlayer()!=null) {
            currentSongUrl = url;
            //MediaPlayerInstance.getMediaPlayer().reset();
            try {
                MediaPlayerInstance.getMediaPlayer().setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaPlayerInstance.getMediaPlayer().prepareAsync();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerInstance.releaseMediaPlayer();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
       // if(mp.getCurrentPosition()>= mp.getDuration()) {
        if(isResetDone){
            isResetDone = false;
        }else {
            EventBus.getDefault().post(new CurrentSongStateEvent(Constants.STOP));
            currentSongUrl = null;
        }
        //}
    }
}
