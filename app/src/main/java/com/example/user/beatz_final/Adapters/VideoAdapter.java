package com.example.user.beatz_final.Adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.user.beatz_final.R;
import com.example.user.beatz_final.Video;
import com.example.user.beatz_final.loadimage.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class VideoAdapter extends ArrayAdapter<Video> implements SectionIndexer {

    @SuppressWarnings("unused")
    private final Context context;
    private final ArrayList<Video> itemsArrayList;
    ContentResolver resolver;
    Resources resource;
    HashMap<String, Integer> alphaIndexer;
    String[]sections;
    private ImageLoader imgLoader;

    public VideoAdapter(Context context, ArrayList<Video> itemsArrayList, ContentResolver resolver, Resources resource){
        super(context, R.layout.textvideo, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.resolver = resolver;
        this.resource = resource;

        alphaIndexer = new HashMap<String, Integer>();
        int size = itemsArrayList.size();

        for(int x=0;x<size;x++){
            Video s = itemsArrayList.get(x);
            // get the first letter of the store

            String ch =s.getVideoTitle().substring(0,1);

            // convert to uppercase otherwise lowercase a-z will be sorted after A-Z
            ch = ch.toUpperCase();
            // put only if the key does not exist

            if(!alphaIndexer.containsKey(ch))
                alphaIndexer.put(ch, x);
        }

        imgLoader = new ImageLoader(context);
        Set<String> sectionLetters = alphaIndexer.keySet();

        // create a list from the set to sort

        ArrayList<String> sectionList = new ArrayList<String>(
                sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sections = sectionList.toArray(sections);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RelativeLayout alertView;

        //Get the current alert object
        //alerts al = getItem(position)

        // inflate the View
        if(convertView==null){
            alertView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(R.layout.textvideo, alertView, true);
        }else{
            alertView = (RelativeLayout) convertView;
        }
        ImageView image = (ImageView)alertView.findViewById(R.id.tumbhid);
        TextView labelView = (TextView)alertView.findViewById(R.id.label);
        Picasso.with(context).load(itemsArrayList.get(position).getVideoThumbnail()).placeholder(R.drawable.song_icon).error(R.drawable.song_icon).into(image);
        labelView.setText(itemsArrayList.get(position).getVideoTitle());

        return alertView;
    }

    @Override
    public int getPositionForSection(int section){
        // TODO Auto-generated method stub
        return alphaIndexer.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int position){
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object[]getSections(){
        // TODO Auto-generated method stub
        return sections;
    }


}
