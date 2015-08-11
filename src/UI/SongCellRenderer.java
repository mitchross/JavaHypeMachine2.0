package UI;

import Models.Song;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mitchross on 8/10/15.
 */
public class SongCellRenderer extends JLabel implements ListCellRenderer
{

    public SongCellRenderer()
    {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (value instanceof Song)
        {
            Song song = (Song) value;
            setBackground(isSelected ? Color.lightGray : Color.white);
           // setForeground(song.getHasDownloaded() ? Color.GREEN : Color.black);
            this.setText(song.artist + " - " + song.title);
        }
        return this;
    }

}