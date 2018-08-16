/**
 * Created by mitchross on 8/12/15.
 */
import Data.SongsManager;
import Models.Song;
import UI.SongCellRenderer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;


public class JListSimpleExample extends JFrame implements ThreadCompleteListener {







    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new JListSimpleExample();
    }

    // UI components declaration
    private JLabel itemListLabel;
    private JList itemsList;

    private JLabel anItemLabel;
    private JTextField anItemTextfield;

    private JButton refreshSongs;
    private JButton downloadButton;
    private JButton clearButton;
    // End of UI components declaration

    // User-defined variables declaration
    Vector<String> listModel = new Vector<String>();
    // End of User-defined variables declaration

    Vector<Song> listModel2 = new Vector<Song>();

    SongsManager songsManager;

    ExecutorService executorService = Executors.newFixedThreadPool( 4 );


    public JListSimpleExample()  {
        super("Creating a Simple JList");
        itemListLabel = new JLabel("List of items: ");
        itemsList = new JList();
        anItemLabel = new JLabel("Enter URL ");
        anItemTextfield = new JTextField(20);
        refreshSongs = new JButton("Refresh");
        downloadButton = new JButton("Download");
        clearButton = new JButton("Clear");

        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(anItemLabel);
        topPanel.add(anItemTextfield);
        topPanel.add(refreshSongs);

        JPanel eastPanel = new JPanel();
        BoxLayout verticalLayout = new BoxLayout(eastPanel, BoxLayout.Y_AXIS);
        eastPanel.setLayout(verticalLayout);
        eastPanel.add(downloadButton);
        eastPanel.add(clearButton);

        content.add(topPanel, BorderLayout.NORTH);
        content.add(itemsList, BorderLayout.CENTER);
        content.add(eastPanel, BorderLayout.EAST);


        songsManager = new SongsManager();

        initDisplay();


        refreshSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

               getListOfSongs();

            }
        });

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadSong();
            }
        });



        pack();
        setVisible(true);
    }

    private void initDisplay()
    {
        anItemTextfield.setText( "https://hypem.com/popular");

       getListOfSongs();


    }

    private void getListOfSongs()
    {
        try {
            if ( listModel2.size() > 0  )
            {
                listModel2.clear();
            }
            listModel2 = songsManager.getListOfSongs( anItemTextfield.getText().toString() );
            //itemsList.setListData(listModel);

            itemsList.setCellRenderer( new SongCellRenderer());
            itemsList.setListData(listModel2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadSong()
    {
        for ( int i=0; i < listModel2.size(); i++)
        {
            final int finalI = i;

            Runnable worker = new NotifyingThread( listModel2.get( finalI ));
            executorService.execute( worker );

        }

        executorService.shutdown();
        System.out.println(" Done!");

    }

    @Override
    public void notifyOfThreadComplete(Song song) {
        System.out.println(song.title + " Downloaded");
    }

    public  class NotifyingThread extends Thread implements Runnable {


        Song song;
        private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

        public NotifyingThread( Song song ) {
            this.song = song;
        }

        public final void addListener(final ThreadCompleteListener listener) {
            listeners.add(listener);
        }
        public final void removeListener(final ThreadCompleteListener listener) {
            listeners.remove(listener);
        }
        private final void notifyListeners() {
            for (ThreadCompleteListener listener : listeners) {
                listener.notifyOfThreadComplete(song);
            }
        }
        @Override
        public final void run() {
            try {
                doRun();
            } finally {
                notifyListeners();
            }
        }
        public void doRun()
        {
            songsManager.downloadSong( song );

        };
    }


}

