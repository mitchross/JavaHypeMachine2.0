package Models;

import java.util.Observable;

/**
 * Created by mitchross on 8/9/15.
 */
public class Song {

    public String title;
    public String key = "";
    public String id = "";
    public String artist = "";
    public String downloadURL = "";
    public String cookie;

    public static String BASE_FILE_PATH = "";

    private Boolean hasDownloaded;


    public Song(String currentID, String currentKey, String currentTitle, String currentArtist, String cookie) {
        this.title = currentTitle;
        this.id = currentID;
        this.key = currentKey;
        this.artist = currentArtist;
        this.hasDownloaded = false;
        this.downloadURL = "http://hypem.com/serve/play/" +this.id + "/" + this.key + ".mp3";
        this.cookie = cookie;
    }

}
