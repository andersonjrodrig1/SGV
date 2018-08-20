package br.com.sgv.main;

import br.com.sgv.database.InitializerDb;
import br.com.sgv.shared.Messages;
import br.com.sgv.view.Login;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Anderson Junior Rodrigues
 */

public class Main {
    
    private static Login login;
    private static JDialog dialog;

    public static void main(String[] args) {  
        initializerApp();
    }
    
    private static void initializerApp() {
        initiliazerDb();
        addThemeInterface();
        modalPresentation();
        modalLogin();
    }
    
    private static void initiliazerDb() {
        InitializerDb.initializerDatabase();
    }
    
    private static void addThemeInterface() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void modalPresentation() {
        dialog = new JDialog();
        Thread thread = new Thread();
        
        try{
            dialog.setTitle(Messages.title_presentation);
            JLabel image = new JLabel("", JLabel.CENTER);
            image.setIcon(new ImageIcon(".\\src\\br\\com\\sgv\\images\\images\\copos.png"));
            dialog.setLayout(new GridLayout());
            dialog.add(image);
            dialog.setSize(600, 350);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            
            thread.sleep(2000);
            
            dialog.dispose();
        } catch(Exception ie) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Fail of system initializer.", ie);
            System.out.println("Error: " + ie.getMessage());
            dialog.dispose();
        }
    }
    
    private static void modalLogin() {
        login = new Login(new JFrame(), true);
        login.initScreen();
    }
}
