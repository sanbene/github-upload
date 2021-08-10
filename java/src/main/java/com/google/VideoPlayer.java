package com.google;


import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo = null;
  private Boolean isPaused = false;
  private final ArrayList<VideoPlaylist> playlists = new ArrayList<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }
  /** Prints the number of videos in the library.*/
  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }
  /** Prints the information of all available videos in the library in lexical order.*/
  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    printVideoList(sortLexical(videoLibrary.getVideos()),false);
  }
  /** Plays the video that corresponds to the specific video ID if it exists and is not flagged.
   * If a video is currently being played, it is stopped and the requested video is played(if it exists and is not flagged).
   */
  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot play video: Video does not exist");
    }
    else if(video.getFlag()){
      System.out.println("Cannot play video: Video is currently flagged (reason: "+video.getFlaggedReason()+")");
    }
    else {
      if (currentVideo != null) {
        System.out.println("Stopping video: " + currentVideo.getTitle());
      }
      currentVideo = video;
      System.out.println("Playing video: " + currentVideo.getTitle());
      isPaused = false;
    }
  }
  /** If there is a video currently playing, it is stopped.*/
  public void stopVideo() {
    if(currentVideo == null){
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else{
      System.out.println("Stopping video: "+ currentVideo.getTitle());
      currentVideo = null;
      isPaused = false;
    }
  }
  /** Plays a random video from the video library (if there is a video currently playing, then that is stopped first).*/
  public void playRandomVideo() {
    if(videoLibrary.hasNoFlaggedVideos()) {
      if (currentVideo != null) {
        System.out.println("Stopping video: " + currentVideo.getTitle());
      }
      currentVideo = videoLibrary.getRandomVideo();
      System.out.println("Playing video: " + currentVideo.getTitle());
    }
    else{
      System.out.println("No videos available");
    }
  }
  /** Pauses the current video if a video is currently playing. */
  public void pauseVideo() {
    if(currentVideo == null){
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else if(isPaused){
      System.out.println("Video already paused: "+ currentVideo.getTitle());
    }
    else{
      System.out.println("Pausing video: " + currentVideo.getTitle());
      isPaused = true;
    }
  }
  /** Continues the current video if there is a current video that is paused. */
  public void continueVideo() {
    if(currentVideo == null){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else {
      if (isPaused) {
        isPaused = false;
        System.out.println("Continuing video: " + currentVideo.getTitle());
      }
      else {
        System.out.println("Cannot continue video: Video is not paused");
      }
    }
  }
  /** Prints the information of the video that is currently playing.*/
  public void showPlaying() {
    if(currentVideo != null){
      String info = currentVideo.getInfo("Currently playing: ");
      if(isPaused){
        info = info + " - PAUSED";
      }
      System.out.println(info);
    }
    else{
      System.out.println("No video is currently playing");
    }
  }
  /** Returns the playlist that matches the name that is passed as a parameter.*/
  private VideoPlaylist getPlaylist(String name){
    for(VideoPlaylist list: playlists){
      if(list.matches(name)){
        return list;
      }
    }
    return null;
  }
  /** Creates a playlist with the name that is passed as a parameter, if there is no other playlists with the same name.*/
  public void createPlaylist(String playlistName) {
    if(getPlaylist(playlistName) == null) {
      playlists.add(new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
    else{
      System.out.println("Cannot create playlist: A playlist with the same name already exists.");
    }
  }
  /** The specific video is added to the specific playlist if it exists.*/
  public void addVideoToPlaylist(String playlistName, String videoId) {
    VideoPlaylist playlist = getPlaylist(playlistName);
    Video video = videoLibrary.getVideo(videoId);
    if(playlist == null){
      System.out.println("Cannot add video to "+ playlistName +": Playlist does not exist");
    }
    else{
      playlist.addVideo(playlistName, video);
    }
  }
  /** Prints the playlist names in lexical order if at least one playlist exists. */
  public void showAllPlaylists() {
    if(playlists.isEmpty()){
      System.out.println("No playlists exist yet");
    }
    else{
      System.out.println("Showing all playlists:");
      String[] playlist_names = sortPlaylistNamesInLexical();
      for(int i=0;i<playlists.size();i++){
        System.out.println("  "+ playlist_names[i]);
      }
    }
  }
  /** Returns the sorted list of playlist names (lexical order)*/
  private String[] sortPlaylistNamesInLexical(){
    String[] playlist_names = new String[playlists.size()];
    for(int i=0; i<playlists.size();i++){
      playlist_names[i] = playlists.get(i).getName();
    }
    for(int i = 0; i < playlists.size()-1; ++i) {
      for (int j = i + 1; j < playlists.size(); ++j) {
        if (playlist_names[i].compareTo(playlist_names[j]) > 0) {
          String temp = playlist_names[i];
          playlist_names[i] = playlist_names[j];
          playlist_names[j] = temp;
        }
      }
    }
    return playlist_names;
  }
  /** if the specific playlist exists, then its playlist information is displayed. */
  public void showPlaylist(String playlistName) {
    VideoPlaylist playlist = getPlaylist(playlistName);
    if( playlist == null){
      System.out.println("Cannot show playlist "+ playlistName+": Playlist does not exist");
    }
    else {
      playlist.showPlaylist(playlistName);
    }
  }
  /** Removes the specific video from the specific playlist if both exists. */
  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist playlist = getPlaylist(playlistName);
    Video video = videoLibrary.getVideo(videoId);
    if( playlist == null){
      System.out.println("Cannot remove video from "+ playlistName+": Playlist does not exist");
    }
    else if( video == null){
      System.out.println("Cannot remove video from "+ playlistName+": Video does not exist");
    }
    else{
      playlist.removeVideo(playlistName, video);
    }
  }
  /** All videos from the specific playlist is removed if the playlist exists. */
  public void clearPlaylist(String playlistName) {
    VideoPlaylist playlist = getPlaylist(playlistName);
    if( playlist == null){
      System.out.println("Cannot clear playlist "+ playlistName+": Playlist does not exist");
    }
    else{
      playlist.clearPlaylist(playlistName);
    }
  }
  /** Deletes the specific playlist if it exists.*/
  public void deletePlaylist(String playlistName) {
    VideoPlaylist playlist = getPlaylist(playlistName);
    if( playlist == null){
      System.out.println("Cannot delete playlist "+ playlistName+": Playlist does not exist");
    }
    else{
      playlists.remove(getPlaylist(playlistName));
      System.out.println("Deleted playlist: "+playlistName);
    }
  }
  /** Returns the sorted list of videos (sorts in Lexical order) */
  private ArrayList<Video> sortLexical(ArrayList<Video> videos){
    for (int i = 0; i < videos.size() - 1; i++) {
      for (int j = i + 1; j < videos.size(); j++) {
        if (videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) > 0) {
          Video temp = videos.get(i);
          videos.set(i, videos.get(j));
          videos.set(j, temp);
        }
      }
    }
    return videos;
  }
  /** Searches for all videos that contain the search term in its name and processes the request.*/
  public void searchVideos(String searchTerm) {
    ArrayList<Video> matches = sortLexical(videoLibrary.searchVideos(searchTerm));
    processRequest(matches,searchTerm);
  }
  /** If there were search results(matches), they are displayed and the user gets to pick which one to play.
   * The video that is picked by the user is then played.
   */
  private void processRequest(ArrayList<Video> matches, String term) {
    if (matches.isEmpty()) {
      System.out.println("No search results for " + term);
    }
    else {
      System.out.println("Here are the results for " + term + ":");
      printVideoList(matches, true);
      Scanner sc = new Scanner(System.in);
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\nIf your answer is not a valid number, we will assume it's a no.");
      playRequestedVideo(sc.nextLine(), matches);
    }
  }
  /** The video choice that is picked by the user is identified, and then it is played */
  private void playRequestedVideo(String line, ArrayList<Video> videos){
      if(onlyNumbers(line)) {
        int request = Integer.parseInt(line);
        if (request >= 1 && request <= videos.size()) {
          playVideo(videos.get(request-1).getVideoId());
        }
      }
  }
  /** Prints the information of the videos in the list. The list is an ordered list if the passed parameter:"ordered" is True. */
  private void printVideoList(ArrayList<Video> videos, Boolean ordered){
    String info ="";
    for(int i=0; i<videos.size();i++) {
      Video video =videos.get(i);
      if (ordered) {
        info = i + 1 + ") ";
      }
      info += video.getInfo("");
      System.out.println(info);
    }
  }
  /** Checks if the line only has numbers.*/
  private Boolean onlyNumbers(String line){
    if(!(line.equals(""))) {
      char[] characters = line.toLowerCase().toCharArray();
      for (char c : characters) {
        if (c >= 'a' && c <= 'z') {
          return false;

        }
      }
    }
    return true;
  }
  /** Searches for all videos that has the specific video tag and processes the request.*/
  public void searchVideosWithTag(String videoTag) {
    ArrayList<Video> matches = sortLexical(videoLibrary.searchVideosWithTag(videoTag));
    processRequest(matches, videoTag);
  }
  /** Flags the specific video if it exists (no specific reason given).
   * If it is currently being played, then its is stopped first.
   */
  public void flagVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video ==currentVideo){
      stopVideo();
    }
    if(video!= null){
      video.flagVideo();
    }
    else {
      System.out.println("Cannot flag video: Video does not exist");
    }
  }
  /** Flags the specific video if it exists (reason is given)
   * If it is currently being played, then its is stopped first.
   */
  public void flagVideo(String videoId, String reason) {
    Video video = videoLibrary.getVideo(videoId);
    if(video==currentVideo){
      stopVideo();
    }
    if(video!= null){
      video.flagVideo(reason);
    }
    else {
      System.out.println("Cannot flag video: Video does not exist");
    }
  }
  /** Removes the flag from the specific video(if it exists) to allow it to be played later.*/
  public void allowVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
    else {
      video.allowVideo();
    }
  }
}