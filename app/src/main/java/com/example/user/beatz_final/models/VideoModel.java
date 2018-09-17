package com.example.user.beatz_final.models;

public class VideoModel {
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

    public VideoModel(String videoTitle, String videoThumbnail, String videoId) {
        this.videoTitle=videoTitle;
        this.videoThumbnail=videoThumbnail;
        this.videoId=videoId;
    }
    @Override
    public String toString(){
        return String.format("VideoModel [videoTitle=%s,videoThumbnail=%s,videoId=%s]", videoTitle, videoThumbnail, videoId);
    }
}
