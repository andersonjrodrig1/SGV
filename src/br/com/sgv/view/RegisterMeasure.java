package br.com.sgv.view;

import br.com.sgv.model.CalcType;
import br.com.sgv.service.CalcTypeService;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author Anderson Junior Rodrigues
 */
public class RegisterMeasure extends javax.swing.JDialog {

    /**
     * Creates new form RegisterMeasure
     */
    
    private ResponseModel<List<CalcType>> response = null;
    private List<CalcType> listCalcType = null;
    
    public RegisterMeasure(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.getCalcType();
    }
    
    public void initScreen() {
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private boolean verifyFields() {
        String message = "";
        boolean isVerify = true;
        
        if (txtNameMeasure.getText().isEmpty()) {
            message += Messages.name_register_measure + "\n";
        }
        
        if (cbxCalc.getSelectedIndex() == 0 || cbxCalc.getSelectedItem().equals("Selecione")) {
            message += Messages.calc_register_measure + "\n";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isVerify = false;
        }
        
        return isVerify;
    }
    
    private void getCalcType() {
        this.response = new CalcTypeService().getCalcType();
        this.listCalcType = this.response.getModel();
        
        if (this.listCalcType != null && this.listCalcType.size() > 0) {
            this.listCalcType.stream().forEach(calc -> {
                cbxCalc.addItem(calc);
            });
        }
    }
    
    private void closeScreen() {
        this.response = null;
        this.listCalcType = null;
        
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblNameMeasure = new javax.swing.JLabel();
        txtNameMeasure = new javax.swing.JTextField();
        lblTypeCalc = new javax.swing.JLabel();
        cbxCalc = new javax.swing.JComboBox();
        btnRegister = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblInitials = new javax.swing.JLabel();
        txtInitials = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Cadastro de Unidade de Medida");

        lblNameMeasure.setText("Unidade de Medida..:");

        lblTypeCalc.setText("Tipo de Calculo..:");

        cbxCalc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        btnRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnRegister.setText("Cadastrar");

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblInitials.setText("Sigla");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNameMeasure)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNameMeasure)
                                .addGap(159, 159, 159)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInitials)
                            .addComponent(txtInitials, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTypeCalc)
                            .addComponent(cbxCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(btnRegister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTitle)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNameMeasure)
                    .addComponent(lblTypeCalc)
                    .addComponent(lblInitials))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCalc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnCancel))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.closeScreen();
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterMeasure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterMeasure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterMeasure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterMeasure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterMeasure dialog = new RegisterMeasure(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox cbxCalc;
    private javax.swing.JLabel lblInitials;
    private javax.swing.JLabel lblNameMeasure;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTypeCalc;
    private javax.swing.JTextField txtInitials;
    private javax.swing.JTextField txtNameMeasure;
    // End of variables declaration//GEN-END:variables
}