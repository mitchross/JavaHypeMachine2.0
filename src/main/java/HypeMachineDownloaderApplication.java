import UI.HypeMachineDownloaderGUI;

import javax.swing.*;

/**
 * Created by mitchross on 8/9/15.
 */
public class HypeMachineDownloaderApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Start the application
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame frame = new JFrame("HypeMachineDownloaderGUI");
                frame.setContentPane(new HypeMachineDownloaderGUI().mainForm);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });

    }
}
