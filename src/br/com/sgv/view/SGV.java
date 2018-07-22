package br.com.sgv.view;

import br.com.sgv.enumerator.AcessScreenEnum;
import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import br.com.sgv.service.AcessPermissionService;
import br.com.sgv.service.UserTypeService;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author Anderson Junior Rodrigues
 */
public class SGV extends javax.swing.JFrame {

    private int screenType;
    
    private Login login = null;
    private About about = null;
    private ListUser listUser = null;
    private RegisterUser registerUser = null;
    private RegisterProduct registerProduct = null;
    private RegisterMeasure registerMeasure = null;
    
    private User user = null;
    private UserType userType = null;
    private AcessPermission acessPermission = null;
    private List<AcessPermission> listAcessPermission = null;
    private ResponseModel<List<AcessPermission>> responseAcessPermission = null;
    private ResponseModel<UserType> responseUserType = null;
    
    public SGV() { }
    
    public SGV(User user) {
        this.user = user;
        this.getAcessUserLogin();
        
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
    
    private void getAcessUserLogin() {
        if (this.user.getUserType() != null && this.user.getUserType().getId() > 0) {
            this.responseUserType = new UserTypeService().findUserTypeById(this.user.getUserType().getId());
            this.userType = this.responseUserType.getModel();
            this.responseAcessPermission = new AcessPermissionService().findListAcessPermissonByUserType(this.userType.getId());
            this.listAcessPermission = this.responseAcessPermission.getModel();
        } else {
            JOptionPane.showMessageDialog(null, Messages.data_inconsistent);
            return;
        }        
    }
    
    private void exitSystem() {
        int response = JOptionPane.showConfirmDialog(null, Messages.logout_system);
        
        if (response == OptionEnum.YES.value) {
            this.about = null;
            this.listUser = null;
            this.registerUser = null;
            this.user = null;
            this.userType = null;
            this.listAcessPermission = null;
            this.registerMeasure = null;
            this.registerProduct = null;
            this.dispose();
            
            this.login = new Login(this, true);
            this.login.initScreen();
        }
    }
    
    private void initRegisterUser() {
        this.screenType = AcessScreenEnum.REGISTER_USER.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.registerUser = new RegisterUser(this, true);
            this.registerUser.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initRegisterProduct() {
        this.screenType = AcessScreenEnum.REGISTER_PRODUCT.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.registerProduct = new RegisterProduct(this, true);
            this.registerProduct.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initAbout() {
        this.screenType = AcessScreenEnum.VIEW_ABOUT.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.about = new About(this, true);
            this.about.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initListUser() {
        this.screenType = AcessScreenEnum.VIEW_USER.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.listUser = new ListUser(this, true);
            this.listUser.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initRegisterMeasure() {
        this.screenType = AcessScreenEnum.REGISTER_MEASURE.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.registerMeasure = new RegisterMeasure(this, true);
            this.registerMeasure.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private AcessPermission verifyPermissionAcess(int screenType) {
        AcessPermission acessPermission = this.listAcessPermission
                .stream()
                .filter(x -> x.getAcessScreen().getId() == screenType)
                .findAny()
                .orElse(null);        
        
        return acessPermission;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        nmRegister = new javax.swing.JMenu();
        nmRegisterProduct = new javax.swing.JMenuItem();
        mnRegisterMeasure = new javax.swing.JMenuItem();
        nmRegisterUser = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        nmListProduct = new javax.swing.JMenuItem();
        mnMeasure = new javax.swing.JMenuItem();
        nmListUser = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        nmAbout = new javax.swing.JMenu();
        nmExit = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        nmRegister.setText("Cadastro");

        nmRegisterProduct.setText("Cadastrar Produto");
        nmRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmRegisterProductActionPerformed(evt);
            }
        });
        nmRegister.add(nmRegisterProduct);

        mnRegisterMeasure.setText("Cadastrar Unidade de Medida");
        mnRegisterMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRegisterMeasureActionPerformed(evt);
            }
        });
        nmRegister.add(mnRegisterMeasure);

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

        mnMeasure.setText("Listar Unidades de Medida");
        jMenu2.add(mnMeasure);

        nmListUser.setText("Listar Usuários");
        nmListUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmListUserActionPerformed(evt);
            }
        });
        jMenu2.add(nmListUser);
        jMenu2.add(jSeparator2);

        jMenuItem2.setText("Registrar Saída");
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatório");

        jMenuItem3.setText("Gerar Relatório");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Consultar Relatórios");
        jMenu3.add(jMenuItem4);

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
            .addGap(0, 501, Short.MAX_VALUE)
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

    private void nmRegisterProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmRegisterProductActionPerformed
        this.initRegisterProduct();
    }//GEN-LAST:event_nmRegisterProductActionPerformed

    private void mnRegisterMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRegisterMeasureActionPerformed
        this.initRegisterMeasure();
    }//GEN-LAST:event_mnRegisterMeasureActionPerformed

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
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem mnMeasure;
    private javax.swing.JMenuItem mnRegisterMeasure;
    private javax.swing.JMenu nmAbout;
    private javax.swing.JMenu nmExit;
    private javax.swing.JMenuItem nmListProduct;
    private javax.swing.JMenuItem nmListUser;
    private javax.swing.JMenu nmRegister;
    private javax.swing.JMenuItem nmRegisterProduct;
    private javax.swing.JMenuItem nmRegisterUser;
    // End of variables declaration//GEN-END:variables
}
