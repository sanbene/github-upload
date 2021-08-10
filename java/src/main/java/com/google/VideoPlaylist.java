package com.google;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String name;
    private ArrayList<Video> videos = new ArrayList<Video>();

    public VideoPlaylist(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Boolean matches(String name){
        return (this.name.toLowerCase(Locale.ROOT)).equals(name.toLowerCase());
    }
    /** adds the video to the playlist if it exists and is not flagged */
    public void addVideo(String name, Video video){
        if(video == null){
            System.out.println("Cannot add video to "+ name +": Video does not exist");
        }
        else if(video.getFlag()){
            System.out.println("Cannot add video to my_playlist: Video is currently flagged (reason: "+video.getFlaggedReason()+")");
        }
        else if(contains(video)){
            System.out.println("Cannot add video to "+ name +": Video already added");
        }
        else{
            videos.add(video);
            System.out.println("Added video to "+ name +": "+ video.getTitle());
        }
    }
    /** checks whether the video is in the playlist*/
    private Boolean contains(Video video){
        return videos.contains(video);
    }
    /** removes the video if video is contained in the playlist*/
    public void removeVideo(String playlistName, Video video){
        if(videos.contains(video)){
            ArrayList<Video> newlist = new ArrayList<Video>();
            for(Video v:videos){
                if(v!=video) {
                    newlist.add(v);
                }
            }
            videos.clear();
            videos.addAll(newlist);
            System.out.println("Removed video from "+playlistName+": "+video.getTitle());
        }
        else{
            System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
        }
    }
    /** removes all videos from the playlist */
    public void clearPlaylist(String name){
        videos.clear();
        System.out.println("Successfully removed all videos from "+name);
    }
    /** prints out information of all the videos in the playlist including flag details*/
    public void showPlaylist(String name){
        System.out.println("Showing playlist: "+name);
        if(videos.isEmpty()){
            System.out.println("No videos here yet");
        }
        else{
            for(int i=0;i<videos.size();i++){
                Video currentVideo = videos.get(i);
                String info ="";
                info = currentVideo.getTitle() +" (" +currentVideo.getVideoId() + ") [";
                List<String> a = currentVideo.getTags();
                int size = a.size();
                if(size>1) {
                    for (int j = 0; j < size - 1; j++) {
                        info += a.get(j) + " ";
                    }
                    info += a.get(size - 1) + "]";
                }
                else{
                    info += "]";
                }
                if(currentVideo.getFlag()){
                    info += " - FLAGGED (reason: "+currentVideo.getFlaggedReason()+")";
                }
                System.out.println(info);
            }
        }
    }
}
