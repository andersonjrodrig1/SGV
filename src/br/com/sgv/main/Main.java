package br.com.sgv.main;

import br.com.sgv.view.SGV;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * @author Anderson Junior Rodriugues
 */

public class Main {

    public static void main(String[] args) {
        
        try {
            SGV frmSgv = new SGV();
            frmSgv.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frmSgv.setLocationRelativeTo(null);
            frmSgv.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Fail of system initializer.", ex);
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
