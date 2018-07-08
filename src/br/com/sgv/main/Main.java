package br.com.sgv.main;

import br.com.sgv.database.InitializerDb;
import br.com.sgv.shared.Messages;
import br.com.sgv.view.About;
import br.com.sgv.view.Login;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Anderson Junior Rodriugues
 */

public class Main {
    
    private static Login login;
    private static JDialog dialog;

    public static void main(String[] args) {
        initiliazerDb();
        modalPresentation();
        modalLogin();
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
            
            thread.sleep(3000);
            
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
    
    private static void initiliazerDb() {
        InitializerDb.initializerDatabase();
    }
}
