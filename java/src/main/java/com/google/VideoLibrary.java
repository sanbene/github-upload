package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Random;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  public VideoLibrary() {
    this.videos = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }
  /** Get all videos from library*/
  public ArrayList<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  /** Get a video by id. Returns null if the video is not found.*/
  public Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }
  /** Checks whether the library has any flagged videos.*/
  public Boolean hasNoFlaggedVideos(){
    for(Video video: getVideos()){
      if(!video.getFlag()){
        return true;
      }
    }
    return false;
  }
  /** Get a random video from the library*/
  public Video getRandomVideo(){
    Random rand = new Random();
    int random = rand.nextInt(getVideos().size() - 1);
    Video video = getVideos().get(random);
    while (video.getFlag()) {
      random =rand.nextInt(getVideos().size() - 1);
      video = getVideos().get(random);
    }
    return video;
  }
  /** Returns all videos in the library that contains the search term */
  public ArrayList<Video> searchVideos(String searchTerm){
    ArrayList<Video> matches = new ArrayList<Video>();
    ArrayList<Video> allVideos = getVideos();
    if(searchTerm != null){
      for (int i = 0; i < allVideos.size(); i++) {
        Video video = allVideos.get(i);
        String name = video.getTitle();
        if (name.toLowerCase().contains(searchTerm.toLowerCase()) && !video.getFlag()) {
          matches.add(video);
        }
      }
    }
    return matches;
  }
  /** returns all videos in the library that has the specific video tag*/
  public ArrayList<Video> searchVideosWithTag(String videoTag) {
    videoTag = videoTag.toLowerCase();
    ArrayList<Video> allVideos = getVideos();
    ArrayList<Video> matches = new ArrayList<Video>();
    for (int i = 0; i < allVideos.size(); i++) {
      Video video = allVideos.get(i);
      if (!video.getFlag()) {
        List<String> tag = video.getTags();
        for (int j = 0; j < tag.size(); j++) {
          if (videoTag.equals(tag.get(j))) {
            matches.add(video);
          }
        }
      }
    }
    return matches;
  }
}
