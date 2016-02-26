package com.flipkart.studio34;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class AudioLaunchActivity extends AppCompatActivity {
    private RecyclerView recentSongsView;
    private RecyclerView trendingSongsView;
    private LinearLayoutManager trendinglinearLayoutManager;
    private LinearLayoutManager recentlinearLayoutManager;
    private List<Podcast> recentPodcasts;
    private List<Podcast> trendingPodcasts;
    String lastNTrendingurl = Constants.LOCALIP + "/studio34/lastNTrendingPods?count=10";
    String lastNPodsUrl = Constants.LOCALIP +  "/studio34/lastNPods";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recentSongsView = (RecyclerView) findViewById(R.id.recent_songs_id);
        recentlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        trendingSongsView = (RecyclerView) findViewById(R.id.trending_songs_id);
        trendinglinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        Type type = new TypeToken<PodCastList>() {
        }.getType();


        PodcastRequest<PodCastList> podcastRequest = new PodcastRequest<PodCastList>(lastNPodsUrl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainActivity", error + "");
                Toast.makeText(AudioLaunchActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        },new Response.Listener<PodCastList>(){
            @Override
            public void onResponse(PodCastList response) {
                Log.d("MainActivity",response + "");
                recentPodcasts = response.getPodcastList();
                SongsAdapter recentSongsAdapter = new SongsAdapter(recentPodcasts, AudioLaunchActivity.this);
                recentSongsView.setLayoutManager(recentlinearLayoutManager);
                recentSongsView.setAdapter(recentSongsAdapter);
                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_tiny);
                recentSongsView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            }
        },type);
        VolleyUtils.getInstance(this).addRequest(podcastRequest);
        PodcastRequest<PodCastList> podCastTrendingRequest = new PodcastRequest<PodCastList>(lastNTrendingurl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainActivity", error + "");
                Toast.makeText(AudioLaunchActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        },new Response.Listener<PodCastList>(){
            @Override
            public void onResponse(PodCastList response) {
                Log.d("MainActivity",response + "");
                trendingPodcasts = response.getPodcastList();
                SongsAdapter trendingSongsAdapter = new SongsAdapter(trendingPodcasts, AudioLaunchActivity.this);
                trendingSongsView.setLayoutManager(trendinglinearLayoutManager);
                trendingSongsView.setAdapter(trendingSongsAdapter);
                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_tiny);
                trendingSongsView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            }
        },type);
        VolleyUtils.getInstance(this).addRequest(podCastTrendingRequest);


        /*recentPodcasts.add("Episode1");
        recentPodcasts.add("Episode2");
        recentPodcasts.add("Episode3");
        recentPodcasts.add("Episode4");
        recentPodcasts.add("Episode4");
        recentPodcasts.add("Episode4");
        recentPodcasts.add("Episode4");
        recentPodcasts.add("Episode4");
        recentPodcasts.add("Episode4");*/
    }

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


}
