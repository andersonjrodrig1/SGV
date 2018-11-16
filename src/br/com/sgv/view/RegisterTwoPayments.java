package br.com.sgv.view;

import br.com.sgv.shared.FormatMoney;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * @author Anderson Junior Rodrigues
 */
public class RegisterTwoPayments extends javax.swing.JDialog {

    private SGV sgv = null;
    private double paymentMoney = 0;
    private double paymentCard = 0;
    
    public RegisterTwoPayments(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.sgv = (SGV) parent;
    }
    
    public void initScreen() {
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        
        this.setColorFields();
    }
    
    private void setValuePaymentsMoney(String value) {
        value = FormatMoney.formatMoney(value);
        txtMoney.setText(value);
        value = value.replace(",", ".");
        this.paymentMoney = Double.valueOf(value);
    }
    
    private void setValuePaymentsCard(String value) {
        value = FormatMoney.formatMoney(value);
        txtCard.setText(value);
        value = value.replace(",", ".");
        this.paymentCard = Double.valueOf(value);
    }
    
    private void closeScreen() {
        this.sgv.resetTypePayments();
        this.sgv = null;
        this.dispose();
    }
    
    private void setColorFields() {
        txtPurchaseValue.setBackground(Color.WHITE);
        txtPaymentValue.setBackground(Color.WHITE);
        txtChangeValue.setBackground(Color.WHITE);
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
        lblMoney = new javax.swing.JLabel();
        txtMoney = new javax.swing.JTextField();
        lblCard = new javax.swing.JLabel();
        txtCard = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        lblPurchaseValue = new javax.swing.JLabel();
        txtPurchaseValue = new javax.swing.JTextField();
        txtPaymentValue = new javax.swing.JTextField();
        lblPaymentValue = new javax.swing.JLabel();
        txtChangeValue = new javax.swing.JTextField();
        lblChangeValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Pagamento em Dinheiro e Cartão");

        lblMoney.setText("Valor em Dinheiro..:");

        txtMoney.setText("0,00");
        txtMoney.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoneyKeyReleased(evt);
            }
        });

        lblCard.setText("Valor no Cartão..:");
        lblCard.setToolTipText("");

        txtCard.setText("0,00");
        txtCard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCardKeyReleased(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnSave.setText("Salvar");

        lblPurchaseValue.setText("Valor Compra..:");

        txtPurchaseValue.setEditable(false);
        txtPurchaseValue.setText("0,00");

        txtPaymentValue.setEditable(false);
        txtPaymentValue.setText("0,00");

        lblPaymentValue.setText("Valor Pago..:");

        txtChangeValue.setEditable(false);
        txtChangeValue.setText("0,00");

        lblChangeValue.setText("Troco..:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMoney)
                            .addComponent(txtMoney)
                            .addComponent(lblCard)
                            .addComponent(txtCard, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPurchaseValue)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtPurchaseValue))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPaymentValue)
                                    .addComponent(txtPaymentValue, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtChangeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblChangeValue))))))
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addComponent(lblCard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMoney)
                .addGap(4, 4, 4)
                .addComponent(txtMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPurchaseValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPurchaseValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblChangeValue)
                            .addComponent(lblPaymentValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChangeValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPaymentValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.closeScreen();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtMoneyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoneyKeyReleased
        if (!txtMoney.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, "Valor informado inválido.\nDigite apenas números.");
                String text = txtMoney.getText().substring(0, txtMoney.getText().length() -1);
                txtMoney.setText(text);
            } else {
                this.setValuePaymentsMoney(txtMoney.getText());
            }
        }
    }//GEN-LAST:event_txtMoneyKeyReleased

    private void txtCardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCardKeyReleased
        if (!txtCard.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, "Valor informado inválido.\nDigite apenas números.");
                String text = txtCard.getText().substring(0, txtCard.getText().length() -1);
                txtCard.setText(text);
            } else {
                this.setValuePaymentsCard(txtCard.getText());
            }
        }
    }//GEN-LAST:event_txtCardKeyReleased

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
            java.util.logging.Logger.getLogger(RegisterTwoPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterTwoPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterTwoPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterTwoPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterTwoPayments dialog = new RegisterTwoPayments(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel lblCard;
    private javax.swing.JLabel lblChangeValue;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblPaymentValue;
    private javax.swing.JLabel lblPurchaseValue;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtCard;
    private javax.swing.JTextField txtChangeValue;
    private javax.swing.JTextField txtMoney;
    private javax.swing.JTextField txtPaymentValue;
    private javax.swing.JTextField txtPurchaseValue;
    // End of variables declaration//GEN-END:variables
}
