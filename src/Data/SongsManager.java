package Data;

import Models.Song;
import Models.Track;
import Models.TrackListings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit.http.Header;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mitchross on 8/9/15.
 */
public class SongsManager
{


    public ArrayList<Song> getListOfSongs() throws IOException
    {
        ArrayList<Song> songs = new ArrayList<Song>();


        //TODO REMOVE THIS TEST VARIABLE and Set hardcoded 1 variable to page number
        String baseUrl = "http://hypem.com/popular";
        String urlEncoded = baseUrl +  "/" + 1 + "?ax=1&ts=" + (new Date()).getTime();


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlEncoded)
                .build();

        Response response = client.newCall(request).execute();

        String cookie = response.header("Set-Cookie");

        Document document = Jsoup.parse( response.body().string() );
        String trackListJSON = document.select("script#displayList-data").first().html();
        trackListJSON = trackListJSON.replace("\\", "");
        Gson gson = new GsonBuilder().create();

        TrackListings trackListings = gson.fromJson( trackListJSON, TrackListings.class);

        for( Track t : trackListings.tracks)
        {

            Song newSong = new Song(t.id, t.key, t.title, t.artist, cookie);

            songs.add(newSong);
        }


        return songs;
    }


}
