package UI;

import Models.Song;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mitchross on 8/10/15.
 */
public class SongCollectionListModel extends AbstractListModel {

    ArrayList<Song> songArrayList;

    public SongCollectionListModel( ArrayList<Song> songArrayList)
    {
        this.songArrayList = songArrayList;
    }

    @Override
    public int getSize() {
        return songArrayList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return songArrayList.get( index );
    }


}
