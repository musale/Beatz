package com.example.user.beatz_final.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.beatz_final.R;
import com.example.user.beatz_final.youtubetry.YoutubePlaylist;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouTube_Main_Fragment extends Fragment {

    static YouTube_Main_Fragment thisFragment;

    public static YouTube_Main_Fragment newInstance() {
        thisFragment = new YouTube_Main_Fragment();

        return thisFragment;
    }


    public YouTube_Main_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_you_tube__main_, container, false);
        Button tryLocalMusic = view.findViewById(R.id.tryLocalMusicId);
        tryLocalMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), YoutubePlaylist.class);
                startActivity(i);
            }
        });
        return view;
    }
}
