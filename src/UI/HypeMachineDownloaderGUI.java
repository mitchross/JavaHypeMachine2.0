package UI;

import Data.SongsManager;
import Models.Song;

import javax.swing.*;
import java.awt.*;
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
    DefaultListModel  model = new DefaultListModel();


    public JList songsJlist = new JList(model);
    public DefaultListModel songListModel;

    private SongsManager songsManager = new SongsManager();


    public HypeMachineDownloaderGUI() {

        for (int i = 0; i < 15; i++) {
            model.addElement("Element " + i);
        }

        songsJlist.updateUI();


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

            }
        });
    }




    int counter = 15;

    private void setJList( ArrayList<Song> songArrayList )
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });






//        //We have "songs" at this point
//        SongCollectionListModel songCollectionListModel = new SongCollectionListModel( songArrayList );
//
//        songsJlist = new JList(songCollectionListModel);
//
//        songsJlist.setCellRenderer( new SongCellRenderer() );
//
//        songsJlist.setVisible(true);
//
//         mainForm.invalidate();
//
//        //Just a test
//        saveDirectoryField.setText( songArrayList.get(0).artist);
    }





}
