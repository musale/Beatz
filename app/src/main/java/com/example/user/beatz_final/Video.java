package com.example.user.beatz_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Video {
    String videoId;
    String videoTitle;
    String videoThumbnail;

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public Video(String videoTitle, String videoThumbnail, String videoId) {
        super();
        this.videoTitle=videoTitle;
        this.videoThumbnail=videoThumbnail;
        this.videoId=videoId;
    }
    public Video(){
        super();
    }
    @Override
    public String toString(){
        return "Video [videoTitle=" + videoTitle + ",videoThumbnail=" + videoThumbnail + ",videoId=" +videoId + "]";
    }
}
