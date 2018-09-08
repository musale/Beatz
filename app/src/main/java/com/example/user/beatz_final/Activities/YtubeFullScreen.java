package com.example.user.beatz_final.Activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.user.beatz_final.R;
import com.example.user.beatz_final.youtubetry.DeveloperKey;
import com.example.user.beatz_final.youtubetry.YoutubePlaylist;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class YtubeFullScreen extends YoutubeFailureRecoveryActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, YouTubePlayer.OnFullscreenListener{

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT: ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    private LinearLayout baseLayout;
    private YouTubePlayerView playerView;
    private YouTubePlayer player;
//    private Button fullscreenButton;
    private View otherViews;
    private boolean fullscreen;

    private String videoid;
    String abc=null;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ytube_full_screen);
        baseLayout = findViewById(R.id.layout);
        playerView = (YouTubePlayerView)findViewById(R.id.player);


        otherViews = findViewById(R.id.other_viewa);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        Intent intent=getIntent();
        videoid=intent.getStringExtra("video");
        abc=intent.getStringExtra("abc");
        doLayout();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onBackPressed() {
        Intent myintent = new Intent(YtubeFullScreen.this, YoutubePlaylist.class);
        myintent.putExtra("abc", abc);
        startActivity(myintent);
    }


    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onClick(View v) {
        //player.set Fullscreen(!fullscreen)
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        int controlFlags = player.getFullscreenControlFlags();
        if(isChecked){
            setRequestedOrientation(PORTRAIT_ORIENTATION);
            controlFlags|= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;}
            else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            controlFlags|= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;}
            player.setFullscreenControlFlags(controlFlags);
        }
    private void doLayout(){
         WindowManager.LayoutParams playerParams = (WindowManager.LayoutParams)playerView.getLayoutParams();
         if(fullscreen){
             playerParams.width = WindowManager.LayoutParams.MATCH_PARENT;
             playerParams.height = WindowManager.LayoutParams.MATCH_PARENT;

             otherViews.setVisibility(View.GONE);}
             else{
             otherViews.setVisibility(View.VISIBLE);
             ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();
             if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                 playerParams.width = otherViewsParams.width = 0;
                 playerParams.height = WRAP_CONTENT;
                 otherViewsParams.height = MATCH_PARENT;
                 playerParams.horizontalWeight = 1;
                 baseLayout.setOrientation(LinearLayout.HORIZONTAL);}
                 else{
                 playerParams.width = otherViewsParams.width = MATCH_PARENT;
                 playerParams.height = WRAP_CONTENT;
                 playerParams.horizontalWeight = 0;
                 otherViewsParams.height = 0;
                 baseLayout.setOrientation(LinearLayout.VERTICAL);
             }
             }
         }

         @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        doLayout();
         }


    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;

        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);
        if(!wasRestored){
            player.cueVideo(videoid);
        }
    }
}
