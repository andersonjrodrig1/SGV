package br.com.sgv.main;

import br.com.sgv.database.InitializerDb;
import br.com.sgv.view.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Anderson Junior Rodrigues
 */
public class Main {
    
    private static Login login;

    public static void main(String[] args) { 
        initializerApp();
    }
    
    private static void initializerApp() {
        initiliazerDb();
        addThemeInterface();
        modalLogin();
    }
    
    private static void initiliazerDb() {
        InitializerDb.initializerDatabase();
    }
    
    private static void addThemeInterface() {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void modalLogin() {
        login = new Login(new JFrame(), true);
        login.initScreen();
    }
}
