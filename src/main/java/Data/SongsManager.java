package Data;

import Models.Song;
import Models.Track;
import Models.TrackListings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.mpatric.mp3agic.*;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.deploy.net.HttpResponse;
import okio.BufferedSink;
import okio.Okio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit.http.Header;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


/**
 * Created by mitchross on 8/9/15.
 */
public class SongsManager
{
    public static String BASE_FILE_PATH = "";
    private String baseUrl;
    private String urlEncoded;


    public Vector<Song> getListOfSongs( String urlToGet) throws IOException
    {
        Vector<Song> songs = new Vector<Song>();

        this.baseUrl = urlToGet;


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url( buildUrlEncodedString() )
                .build();

        Response response = client.newCall(request).execute();

        String cookie = response.header("Set-Cookie");

        Document document = Jsoup.parse( response.body().string() );
        String trackListJSON = document.select("script#displayList-data").first().html();
        trackListJSON = trackListJSON.replace("\\", "");
        //debug
//        try (PrintStream out = new PrintStream(new FileOutputStream("songs.txt"))) {
//            out.print(trackListJSON);
//        }

        Gson gson = new GsonBuilder().create();
        JsonReader reader = new JsonReader(new StringReader(trackListJSON));
        reader.setLenient(true);


        TrackListings trackListings = gson.fromJson( reader, TrackListings.class);

        for( Track t : trackListings.tracks)
        {

            Song newSong = new Song(t.id, t.key, t.title, t.artist, cookie);

            songs.add(newSong);
        }


        return songs;
    }

    private String buildUrlEncodedString()
    {
       return urlEncoded = baseUrl +  "/" + 1 + "?ax=1&ts=" + ( new Date() ).getTime();
    }

    public void downloadSong ( Song song )
    {
        //We wil download it here
        OkHttpClient httpclient = new OkHttpClient();

        Request request = new Request.Builder().addHeader( "cookie" , song.cookie).url( song.downloadURL).build();

        try {
            Response response = httpclient.newCall( request ).execute();


            File downloadedFile = new File("songs/", song.title + ".mp3");

            byte[] responseData = response.body().bytes();

            FileOutputStream fos = new FileOutputStream( downloadedFile );

            fos.write( responseData );
            fos.close();

            try {
                Mp3File mp3file = new Mp3File( downloadedFile);

                ID3v1 id3v1Tag;
                if (mp3file.hasId3v1Tag()) {
                    id3v1Tag =  mp3file.getId3v1Tag();
                } else {
                    // mp3 does not have an ID3v1 tag, let's create one..
                    id3v1Tag = new ID3v1Tag();
                    mp3file.setId3v1Tag(id3v1Tag);
                    id3v1Tag.setArtist(song.artist);
                    id3v1Tag.setTitle(song.title);
                    try {
                        mp3file.save(song.title + ".mp3");
                    } catch (NotSupportedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
