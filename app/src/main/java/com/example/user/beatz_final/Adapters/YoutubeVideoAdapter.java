package com.example.user.beatz_final.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.beatz_final.R;
import com.example.user.beatz_final.models.VideoModel;

import java.util.ArrayList;

public class YoutubeVideoAdapter extends ArrayAdapter<VideoModel> implements View.OnClickListener{

    private ArrayList<VideoModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView videoId;
        TextView videoTitle;
        ImageView videoThumbnail;
    }

    public YoutubeVideoAdapter(ArrayList<VideoModel> data, Context context) {
        super(context, R.layout.single_video_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        VideoModel video=(VideoModel) object;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        VideoModel video = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_video_layout, parent, false);
            viewHolder.videoId = convertView.findViewById((R.id.videoId));
            viewHolder.videoTitle = convertView.findViewById(R.id.videoTitle);
            viewHolder.videoThumbnail = convertView.findViewById(R.id.videoThumbnail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.videoId.setText(video.getVideoId());
        viewHolder.videoTitle.setText(video.getVideoTitle());
//        viewHolder.videoThumbnail.setImageIcon();
        return convertView;
    }
}
