package br.com.sgv.view;

import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.shared.Messages;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JOptionPane;

/**
 *
 * @author ander
 */
public class SGV extends javax.swing.JFrame {

    private Login login = null;
    private About about = null;
    private ListUser listUser = null;
    private RegisterUser registerUser = null;    
    
    public SGV() {
        initComponents();
    }
    
    public void initScreen() {     
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setLocation(0, 0);
        this.setSize((int)rect.getWidth(), (int)rect.getHeight());       
        this.setLocationRelativeTo(null);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
    
    private void exitSystem() {
        int response = JOptionPane.showConfirmDialog(null, Messages.logout_system);
        
        if (response == OptionEnum.YES.value) {
            this.dispose();
            
            this.login = new Login(this, true);
            this.login.initScreen();
        }
    }
    
    private void initRegisterUser() {
        this.registerUser = new RegisterUser(this, true);
        this.registerUser.initScreen();
    }
    
    private void initAbout() {
        this.about = new About(this, true);
        this.about.initScreen();
    }
    
    private void initListUser() {
        this.listUser = new ListUser(this, true);
        this.listUser.initScreen();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        nmRegister = new javax.swing.JMenu();
        nmRegisterProduct = new javax.swing.JMenuItem();
        nmRegisterUser = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        nmListProduct = new javax.swing.JMenuItem();
        nmListUser = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        nmAbout = new javax.swing.JMenu();
        nmExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        nmRegister.setText("Cadastro");

        nmRegisterProduct.setText("Cadastrar Produto");
        nmRegister.add(nmRegisterProduct);

        nmRegisterUser.setText("Cadastrar Usuário");
        nmRegisterUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmRegisterUserActionPerformed(evt);
            }
        });
        nmRegister.add(nmRegisterUser);

        jMenuBar1.add(nmRegister);

        jMenu2.setText("Ações");

        nmListProduct.setText("Listar Produtos");
        jMenu2.add(nmListProduct);

        nmListUser.setText("Listar Usuários");
        nmListUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmListUserActionPerformed(evt);
            }
        });
        jMenu2.add(nmListUser);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatório");
        jMenuBar1.add(jMenu3);

        nmAbout.setText("Sobre");
        nmAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nmAboutMouseClicked(evt);
            }
        });
        jMenuBar1.add(nmAbout);

        nmExit.setText("Sair");
        nmExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nmExitMouseClicked(evt);
            }
        });
        jMenuBar1.add(nmExit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nmExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nmExitMouseClicked
        this.exitSystem();
    }//GEN-LAST:event_nmExitMouseClicked

    private void nmRegisterUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmRegisterUserActionPerformed
        this.initRegisterUser();
    }//GEN-LAST:event_nmRegisterUserActionPerformed

    private void nmAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nmAboutMouseClicked
        this.initAbout();
    }//GEN-LAST:event_nmAboutMouseClicked

    private void nmListUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmListUserActionPerformed
        this.initListUser();
    }//GEN-LAST:event_nmListUserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SGV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu nmAbout;
    private javax.swing.JMenu nmExit;
    private javax.swing.JMenuItem nmListProduct;
    private javax.swing.JMenuItem nmListUser;
    private javax.swing.JMenu nmRegister;
    private javax.swing.JMenuItem nmRegisterProduct;
    private javax.swing.JMenuItem nmRegisterUser;
    // End of variables declaration//GEN-END:variables
}
