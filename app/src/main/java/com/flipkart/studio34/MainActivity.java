package com.flipkart.studio34;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private ImageView playButton;
    private View playLayout;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private Podcast podcast;
    private LinearLayout rootLayout;
    private MetaDataAdapter metaDataAdapter;
    private ListView metadataListView;
    private Handler mHandler = new Handler();
    private String url = "http://10.47.2.2/studio34/13Jan2016.mp3";
    private String podcastBaseInfoUrl = Constants.LOCALIP + "/studio34/podcastInfo";

    private String getpodcastInfoUrl(Podcast podcast , String userId){
        String url = podcastBaseInfoUrl + "?" + "podCastId=" +
                podcast.getId() + "&userId=" + userId;
        return url;
    }

    private AdapterView.OnItemClickListener onItemClickedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            long time = metaDataAdapter.getItem(position).getStartTime() * 1000;
            MediaPlayerInstance.getMediaPlayer().seekTo(
                    (int)time);
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playLayout  = (View) findViewById(R.id.play_layout);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        metadataListView = (ListView) findViewById(R.id.metadataListView);
        playButton = (ImageView) findViewById(R.id.btnPlay);
        playButton.setOnClickListener(playButtonClickListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        songProgressBar.setProgress(0);
        songProgressBar.setMax(100);
        Bundle bundle =getIntent().getExtras();
        if(bundle!=null) {
            podcast = bundle.getParcelable(Constants.PODCAST_KEY);
        }
        rootLayout = (LinearLayout)  findViewById(R.id.root_layout);
        songProgressBar.setOnSeekBarChangeListener(this);
        if(podcast!=null) {
            String podcastUrl = getpodcastInfoUrl(podcast, "abc");
            PodcastRequest<PodcastInfo> podcastInfoRequest = new PodcastRequest<PodcastInfo>(podcastUrl, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MainActivity", error + "");
                    Toast.makeText(MainActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                }
            },new Response.Listener<PodcastInfo>(){
                @Override
                public void onResponse(PodcastInfo response) {
                    Log.d("MainActivity",response + "");
                    Media media = response.getMedia();
                    List<MediaMetaData> mediaMetaDataList= media.
                            getMediaMetaDataList();
                    metaDataAdapter = new MetaDataAdapter(MainActivity.this
                            ,mediaMetaDataList);
                    metadataListView.setOnItemClickListener(onItemClickedListener);
                    metadataListView.setAdapter(metaDataAdapter);
                    metaDataAdapter.notifyDataSetChanged();
                    /*recentPodcasts = response.getPodcastList();
                    SongsAdapter recentSongsAdapter = new SongsAdapter(recentPodcasts, AudioLaunchActivity.this);
                    recentSongsView.setLayoutManager(recentlinearLayoutManager);
                    recentSongsView.setAdapter(recentSongsAdapter);
                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_tiny);
                    recentSongsView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));*/
                }
            },new TypeToken<PodcastInfo>(){}.getType());
            VolleyUtils.getInstance(this).addRequest(podcastInfoRequest);
        }
        /*mediaPlayer= new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playLayout.setVisibility(View.VISIBLE);
            }
        });// might take long! (for buffering, etc)*/
        Intent intent = new Intent(this, AudioService.class);
        if(podcast!=null) {
            intent.putExtra(Constants.URL_KEY, podcast.getUrl());
        }
        intent.putExtra(Constants.FROM_SPLASH,false);
        startService(intent);
        if( MediaPlayerInstance.getMediaPlayer()!=null){
            if( MediaPlayerInstance.getMediaPlayer().isPlaying()) {
                updateProgressBar();
                playButton.setImageResource(R.drawable.ic_pause_circle_filled_red_100_36dp);
            }
        }

      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void initialiseViews(List<MediaMetaData> mediaMetaDataList){
        for(MediaMetaData mediaMetaData: mediaMetaDataList) {
            /*View view = getLayoutInflater().inflate(R.layout.meta_layout, rootLayout, false);
            TextView startTimeId= (TextView)view.findViewById(R.id.startTimeId);
            TextView data = (TextView) view.findViewById(R.id.dataId);
            startTimeId.setText(mediaMetaData.getStartTime() + "");
            data.setText(mediaMetaData.getData() + "");
            rootLayout.addView(view);*/

            /*LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.meta_layout, null);
            rootLayout.addView(view);*/
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setText("Added tv");
            rootLayout.addView(tv);
        }
        this.setContentView(rootLayout);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    View.OnClickListener playButtonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            /*if(mediaPlayer.isPlaying()) {
                ((ImageView) v).setImageResource(R.drawable.ic_pause_circle_filled_red_100_36dp);
                mediaPlayer.pause();
            }else{
                ((ImageView) v).setImageResource(R.drawable.ic_play_circle_filled_red_100_36dp);
                mediaPlayer.start();
            }*/
            EventBus.getDefault().post(new StartSongEvent(url));
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(CurrentSongStateEvent currentSongEvent){
        switch (currentSongEvent.getSongstate()){
            case Constants.PLAY:
                updateProgressBar();
                playButton.setImageResource(R.drawable.ic_pause_circle_filled_red_100_36dp);
                break;
            default:
                playButton.setImageResource(R.drawable.ic_play_circle_filled_red_100_36dp);

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = MediaPlayerInstance.getMediaPlayer().getDuration();
        int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        MediaPlayerInstance.getMediaPlayer().seekTo(currentPosition);

        // update timer progress again

        updateProgressBar();
    }

    public void updateProgressBar() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = MediaPlayerInstance.getMediaPlayer().getDuration();
            long currentDuration = MediaPlayerInstance.getMediaPlayer().getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+Utilities.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+Utilities.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(Utilities.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };
}
