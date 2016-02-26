package com.flipkart.studio34;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flipkart.studio34.utils.StaticUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {


    private static final String TAG = "SearchActivity";
    private PodcastAdapter podcastAdapter;
    private ListView podcastListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handleIntent(getIntent());
        podcastListView = (ListView) findViewById(R.id.search_results);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            String url = Constants.LOCALIP + "/studio34/podsByTag?tagName=" + query +
                    "&userId=" + StaticUtils.getInstance().getLoggedInUser().getExternalId();
            //use the query to search your data somehow
            JsonObjectRequest podcastRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String stringResponse = response.toString();
                            Gson gson = new Gson();
                            PodCastList podCastList = gson.fromJson(stringResponse, PodCastList.class);
                            podcastAdapter = new PodcastAdapter(SearchActivity.this, podCastList.getPodcastList());
                            podcastListView.setAdapter(podcastAdapter);
                            podcastAdapter.query();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Error in getting response");
                        }
                    });

            RequestQ.getInstance(this).addToRequestQueue(podcastRequest);
        }
    }

}
