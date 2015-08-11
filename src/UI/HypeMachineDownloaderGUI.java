package UI;

import Data.SongsManager;
import Models.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mitchross on 8/9/15.
 */
public class HypeMachineDownloaderGUI {
    private JTextField urlField;
    private JButton saveButton;
    private JTextField saveDirectoryField;
    private JTextPane logPane;
    private JButton downloadButton;
    private JButton moreButton;
    private JButton refreshButton;
    public JPanel mainForm;

    public JList songsJlist;
    public DefaultListModel songListModel;

    private SongsManager songsManager = new SongsManager();


    public HypeMachineDownloaderGUI() {



        //Buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print( "save pressed!");
            }
        });
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print( "download pressed!");

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Song> songs = songsManager.getListOfSongs();
                    setJList( songs );

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    private void setJList( ArrayList<Song> songArrayList )
    {

        SongCollectionListModel songCollectionListModel = new SongCollectionListModel( songArrayList );

        songsJlist = new JList(songCollectionListModel);

        songsJlist.setCellRenderer( new SongCellRenderer() );

        songsJlist.setVisible(true);

        //Just a test
        saveDirectoryField.setText( songArrayList.get(0).artist);
    }


}
