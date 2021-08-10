package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private Boolean flag = false;
  private String flaggedReason ="Not supplied";

  public Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  public String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  public String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  public List<String> getTags() {
    return tags;
  }
  /** Checks whether the video is flagged(True) or not(False). */
  public Boolean getFlag(){
    return flag;
  }
  /** Returns the reason for being flagged */
  public String getFlaggedReason() {
    return flaggedReason;
  }
  /** returns the information of the video including flag details */
  public String getInfo(String line) {
    String info = "";
    if (!line.equals("")) {
      info += line;
    }
    info += title + " (" + videoId + ") [";
    List<String> a = tags;
    int size = a.size();
    if (size > 1) {
      for (int j = 0; j < size - 1; j++) {
        info += a.get(j) + " ";
      }
      info += a.get(size - 1) + "]";
    }
    else{
      info += "]";
    }
    if(flag){
      info += " - FLAGGED (reason: "+flaggedReason+")";
    }
    return info;
  }
  /** Flags video if it is not already flagged (no specific reason supplied) */
  public void flagVideo(){
    if(flag){
      System.out.println("Cannot flag video: Video is already flagged");
    }
    else {
      this.flag = true;
      System.out.println("Successfully flagged video: " + title + " (reason: Not supplied)");
    }
  }
  /** Flags video if it is not already flagged (reason is supplied) */
  public void flagVideo(String flaggedReason){
    if(flag){
      System.out.println("Cannot flag video: Video is already flagged");
    }
    else {
      flag = true;
      this.flaggedReason = flaggedReason;
      System.out.println("Successfully flagged video: " + title + " (reason: " + flaggedReason + ")");
    }
  }
  /** Removes flag from video (if video is flagged) to allow video being played later */
  public void allowVideo(){
    if(flag){
      flag = false;
      System.out.println("Successfully removed flag from video: "+ title);
    }
    else{
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }
  }

}
