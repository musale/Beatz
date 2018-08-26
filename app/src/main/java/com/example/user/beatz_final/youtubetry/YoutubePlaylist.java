package com.example.user.beatz_final.youtubetry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubePlaylist extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listVideo;
    private JsonParser parserVideo;
    private ProgressDialog progress;
    private VideoAdapter videoadapter;
    private Video vObject;
    private ArrayList<Video>videoArrayList;
    String abc=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_playlist);
        listVideo=findViewById(R.id.list_item);
        videoArrayList = new ArrayList<Video>();
        listVideo.setOnItemClickListener(this);
        parserVideo = new JsonParser();
        listVideo.setAdapter(videoadapter);
        abc = getIntent().getExtras().get("abc").toString();
        new Async().execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed(){
        Intent myintent = new Intent(YoutubePlaylist.this, MainActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myintent);
        finish();
        return;
    }

    public ArrayList<Video>parsingJson(String videoUrl){
        try{
            JSONObject json=parserVideo.getJsonFromYoutube(videoUrl);
            JSONArray jsonArray = new JSONArray(json.getString("items"));
            for(int i=0;i<jsonArray.length();i++){
                JSONObject thumbnail = jsonArray.getJSONObject(i);
                JSONObject snippet=thumbnail.getJSONObject("snippet");
                JSONObject defaulturl=snippet.getJSONObject("thumbnails");
                JSONObject url=defaulturl.getJSONObject("high");
                JSONObject resourceId=snippet.getJSONObject("resourceId");
                String videoId=resourceId.getString("videoId");
                String imageurl=url.getString("url");
                String title=snippet.getString("title");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return videoArrayList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        vObject = videoArrayList.get(position);
        String video=vObject.getVideoId();
        Intent intent = new Intent(YoutubePlaylist.this, YtubeFullScreen.class);
        intent.putExtra("video", video);
        intent.putExtra("abc", abc);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public class Async extends AsyncTask<String,String,String>{

        ArrayList<Video>videolist;

        @Override
        protected String doInBackground(String... strings) {
            try{
                if(abc.equals("local")){
                    videolist=parsingJson(parserVideo.local);
                }else{
                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){
                finish();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(YoutubePlaylist.this);
            progress.setMessage("Loading Data...");
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                // Video Adapter class is used to customise the listView
                videoadapter=new VideoAdapter(YoutubePlaylist.this, videolist, YoutubePlaylist.this.getContentResolver(),YoutubePlaylist.this.getResources());
                listVideo.setAdapter(videoadapter);
                listVideo.setFastScrollEnabled(true);
                progress.dismiss();
            }
            catch(Exception e){

            }
        }
    }
}
