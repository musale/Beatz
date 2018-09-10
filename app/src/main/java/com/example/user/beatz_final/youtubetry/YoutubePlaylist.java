package com.example.user.beatz_final.youtubetry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.beatz_final.Activities.YtubeFullScreen;
import com.example.user.beatz_final.MainActivity;
import com.example.user.beatz_final.R;
import com.example.user.beatz_final.Video;
import com.example.user.beatz_final.Adapters.VideoAdapter;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubePlaylist extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listVideo;
    private VideoAdapter videoadapter;
    private Video vObject;
    private ArrayList<Video> videoArrayList;
    String abc = "";
    String YPDEBUG = "YouTube Playlist Debug";
    String YPERROR = "YouTube Playlist Error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_playlist);

        String playlistItemsUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLVrWVCMVUP2wLcVW4IT4JwD-5XyY2Hu_w&key=AIzaSyBy32LH5Qt6ucVtMMfLDxLgD81aB89N210";
        listVideo = findViewById(R.id.list_item);
        videoArrayList = new ArrayList<Video>();


        VolleyUtils.makeJsonObjectRequest(getApplicationContext(), playlistItemsUrl, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e(YPERROR, message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(JSONObject response) {
                Log.d(YPDEBUG, response.toString());
                try {
                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String title = item.getString("title");
                        Video video = new Video(title, "thumbnail" + i, "id" + i);
                        videoArrayList.add(video);
                    }
                    videoadapter = new VideoAdapter(YoutubePlaylist.this, videoArrayList);
                    listVideo.setAdapter(videoadapter);

                    Toast.makeText(getApplicationContext(), String.format("Got some shit "), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Intent myintent = new Intent(YoutubePlaylist.this, MainActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myintent);
        finish();
        return;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        vObject = videoArrayList.get(position);
        String video = vObject.getVideoId();
        Intent intent = new Intent(YoutubePlaylist.this, YtubeFullScreen.class);
        intent.putExtra("video", video);
        intent.putExtra("abc", abc);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
