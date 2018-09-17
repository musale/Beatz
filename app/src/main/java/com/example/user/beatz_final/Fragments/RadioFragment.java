package com.example.user.beatz_final.Fragments;


import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.beatz_final.R;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {

    private Button btn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    View rootView;


    static  RadioFragment thisFragment;

    public static RadioFragment newInsatnce() {
     thisFragment =new RadioFragment();

     return thisFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_radio, container, false);

        btn = (Button) rootView.findViewById(R.id.audioStreamBtn);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(getActivity());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playPause){
                    btn.setText("Pause Streaming");

                    if (initialStage){
                        new Player().execute("http://www.radio.or.ke/#capital-fm-98-4");
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }

                    playPause = true;
                } else {
                    btn.setText("Launch Streaming");
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    playPause = false;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        initialStage = true;
                        playPause = false;
                        btn.setText("Pause Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                Log.e("Error whilst streaming", e.getMessage());
                prepared = false;
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering....");
            progressDialog.show();
        }

    }
}
