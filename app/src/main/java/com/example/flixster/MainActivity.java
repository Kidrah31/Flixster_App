package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL= "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG= "Main Activity";
    List<Movie> movies;
    RecyclerView rvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMovies=findViewById(R.id.rvMovies);
        movies=new ArrayList<>();
        //Create the Adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        //Set the adapter on the recycler view

        rvMovies.setAdapter(movieAdapter);
        //Set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));



        AsyncHttpClient client= new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {

                Log.d(TAG,"onSuccess");
                JSONObject jsonObject= json.jsonObject;
                try {
                    JSONArray results= jsonObject.getJSONArray("results");
                   // Log.i(TAG,"Results:" + results.toString());
                   movies.addAll(Movie.fromJsonArray(results));
                   movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies:" + movies.toString());
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception",e);

                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });

    }
}