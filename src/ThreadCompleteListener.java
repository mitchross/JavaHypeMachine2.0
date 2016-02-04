import Models.Song;

/**
 * Created by mitchross on 9/13/15.
 */
public interface ThreadCompleteListener {
    void notifyOfThreadComplete(final Song song);
}