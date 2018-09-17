package com.example.user.beatz_final.youtubetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.beatz_final.Activities.YtubeFullScreen;
import com.example.user.beatz_final.Adapters.YoutubeVideoAdapter;
import com.example.user.beatz_final.MainActivity;
import com.example.user.beatz_final.R;
import com.example.user.beatz_final.models.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubePlaylist extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView youtubeItemList;
    String abc = "";
    String YPDEBUG = "YouTube Playlist Debug";
    String YPERROR = "YouTube Playlist Error";
    private YoutubeVideoAdapter videoAdapter;
    private VideoModel video;
    private ArrayList<VideoModel> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_playlist);

        // TODO: store this is shared preferences after hitting the YTube API
        String playlistItemsUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLVrWVCMVUP2wLcVW4IT4JwD-5XyY2Hu_w&key=AIzaSyBy32LH5Qt6ucVtMMfLDxLgD81aB89N210";
        youtubeItemList = findViewById(R.id.youtubeItems);
        videoList = new ArrayList<>();


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
                        JSONObject snippet = item.getJSONObject("snippet");
                        JSONObject resourceId = snippet.getJSONObject("resourceId");

                        String title = snippet.getString("title");
                        String id = resourceId.getString("videoId");
                        VideoModel videoModel;
                        // TODO: add thumbnails
                        videoModel = new VideoModel(title, "thumbnail" + i, id);
                        videoList.add(videoModel);
                    }
                    videoAdapter = new YoutubeVideoAdapter(videoList, getApplicationContext());
                    youtubeItemList.setAdapter(videoAdapter);

                    Toast.makeText(getApplicationContext(), String.format("Got some music "), Toast.LENGTH_LONG).show();
                    // TODO: add onclick listeners to the youtube items
                    // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
                    // has similar implementation to what is done above
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
        video = videoList.get(position);
        String video = this.video.getVideoId();
        Intent intent = new Intent(YoutubePlaylist.this, YtubeFullScreen.class);
        intent.putExtra("video", video);
        intent.putExtra("abc", abc);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
