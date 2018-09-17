package com.example.user.beatz_final.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.beatz_final.Player;
import com.example.user.beatz_final.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerFragment extends Fragment{


    static  MusicPlayerFragment thisFragment;


    public static MusicPlayerFragment newInstance() {
    thisFragment = new MusicPlayerFragment();

    return thisFragment;
    }

    private static final int MY_PERMISSION_REQUEST =1;

    Cursor cursor;
    ArrayAdapter<String> adapter;
    android.widget.ListView lv;
    String[] items;
    View rootView;
    ArrayList<File> mySongs = new ArrayList<>();
    ArrayList<String> songs = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_music_player, container, false);

        lv = rootView.findViewById(R.id.lvPlaylist);
        /*if(Environment.getExternalStorageDirectory().exists()){
            //mySongs = findSongs(Environment.getRootDirectory());
            mySongs= findSongs(Environment.getExternalStorageDirectory());
        }else {

        }*/
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);
            }else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);
            }
        } else {
            findSongs();
        }


       /* items = new String[ mySongs.size() ];
        for(int i = 0; i<mySongs.size(); i++){
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "").replace(".mp4","");
        }*/

        ArrayAdapter<String> adp = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.song_layout,R.id.video_url, songs);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle m = new Bundle();
                Intent s = new Intent(getActivity(),Player.class);
                m.putStringArrayList("songlist",songs);
                m.putInt("pos",position);
                startActivity(s);
            }
        });

      return rootView;
    }


    //    fetches song items from the SD card and stores them in an arraylist
    public void findSongs() {
//        ArrayList<File> al = new ArrayList<File>();
//        File[] files = root.listFiles();
//        for(File singleFile : files){
//            if (singleFile.isDirectory() && !singleFile.isHidden()){
//                al.addAll(findSongs(singleFile));
//            }
//            else{
//                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".mp4")){
//                    al.add(singleFile);
//                }
//            }
//        }
//        return al;

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
//                uri             = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

        };

        cursor = getActivity().managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        while (cursor.moveToNext()) {
            songs.add(cursor.getString(0));
        }
    }
}
