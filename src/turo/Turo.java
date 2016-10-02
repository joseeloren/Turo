package turo;

import java.io.PrintStream;
import javax.swing.UIManager;

public class Turo {

    static final String LOOKANDFEEL = "System";

    public static void main(String[] args) throws Exception {
        initLookAndFeel();
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);

        MainFrame frame = new MainFrame();
    }

    private static void initLookAndFeel() {
        String lookAndFeel = null;

        if ("System" != null) {
            if ("System".equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();

            } else if ("System".equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();

            } else if ("System".equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

            } else if ("System".equals("GTK")) {
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: System");

                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);

                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (javax.swing.UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");

                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");

                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }
}
