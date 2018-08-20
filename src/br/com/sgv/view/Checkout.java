/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.view;

import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.service.CheckoutService;
import br.com.sgv.shared.FormatMoney;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class Checkout extends javax.swing.JDialog {

    /**
     * Creates new form Checkout
     */
    
    private List<br.com.sgv.model.Checkout> listCheckout = null;
    
    public Checkout(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initScreen() {
        this.initDatePicker();
        this.getRegisterCheckoutMonths();
        
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private void registerCheckout() {
        if (verifyFields()) {
            String checkoutName = txtCheckoutName.getText();
            String checkoutValue = txtCheckoutValue.getText().replaceAll("\\.", "").replace(",", ".");
            Date checkoutDate = dpkCheckoutDate.getDate();
            
            ResponseModel<Boolean> response = new CheckoutService().saveCheckout(checkoutName, checkoutValue, checkoutDate);
            
            if (response.getModel()) {
                JOptionPane.showMessageDialog(null, Messages.save_success);
                this.getRegisterCheckoutMonths();
                this.cleanFields();
            } else {
                JOptionPane.showMessageDialog(null, response.getMensage());
            }
        }
    }
    
    private void getRegisterCheckoutMonths() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date initialDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date finalDate = calendar.getTime();
        
        ResponseModel<List<br.com.sgv.model.Checkout>> response = new CheckoutService().getCheckoutToMonth(initialDate, finalDate);
        this.listCheckout = response.getModel();
        
        this.setTableCheckout();
    }
    
    private void removeCheckout() {
        int row = tblCheckout.getSelectedRow();
        
        if (row >= 0) {
            Object objColumn = tblCheckout.getValueAt(row, 0);
            int idCheckout = Integer.valueOf(objColumn.toString());
            
            ResponseModel<br.com.sgv.model.Checkout> responseCheckout = new CheckoutService().getCheckoutById(idCheckout);
            br.com.sgv.model.Checkout checkout = responseCheckout.getModel();
            
            if (checkout == null) {
                JOptionPane.showMessageDialog(null, responseCheckout.getMensage());
            } else {
                if (checkout.getStatusRegister().getId() == StatusRegisterEnum.TOTALIZED.value) {
                    JOptionPane.showMessageDialog(null, Messages.checkout_no_remove);
                } else {
                    int option = JOptionPane.showConfirmDialog(null, Messages.remove_modal);
                    
                    if (option == OptionEnum.YES.value) {
                        ResponseModel<Boolean> responseStatus = new CheckoutService().removeCheckout(checkout);
                        boolean status = responseStatus.getModel();

                        if (status) {
                            JOptionPane.showMessageDialog(null, Messages.remove_sucess);
                            this.getRegisterCheckoutMonths();
                        } else {
                            JOptionPane.showMessageDialog(null, responseStatus.getMensage());
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, Messages.select_row);
        }
    }
    
    private void setTableCheckout() {
        String valueString = "0,00";
        double valueTotal = 0d;
        
        if (this.listCheckout.size() > 0) {
            DefaultTableModel table = (DefaultTableModel)tblCheckout.getModel();
            table.setRowCount(0);
            
            this.listCheckout.stream().forEach(checkout -> {
                table.addRow(new Object[] {
                    checkout.getId(),
                    checkout.getCheckoutName(),
                    "R$ " + checkout.getCheckoutValue(),
                    new SimpleDateFormat("dd/MM/yyyy").format(checkout.getCheckoutDate())
                });
            });
            
            for (br.com.sgv.model.Checkout c : this.listCheckout) {
                valueTotal += c.getCheckoutValue();
            }
            
            if (valueTotal > 0) {
                DecimalFormat df = new DecimalFormat("#.00");
                String value = df.format(valueTotal);
                
                valueString = FormatMoney.formatMoney(value);
            }
        }
        
        lblValueTotal.setText(valueString);
    }
    
    private boolean verifyFields() {
        boolean isVerify = true;
        String message = "";
        
        if (txtCheckoutName.getText().isEmpty()) {
            message += Messages.checkout_name + "\n";
        }
        
        if (txtCheckoutValue.getText().isEmpty() || txtCheckoutValue.getText().equals("0,00")) {
            message += Messages.checkout_value + "\n";
        }
        
        if (!message.isEmpty()) {
            isVerify = false;
            JOptionPane.showMessageDialog(null, message);
        }
        
        return isVerify;
    }
    
    private void setMaskMoney() {
        String valueDigit = txtCheckoutValue.getText();
        String valueFormat = FormatMoney.formatMoney(valueDigit);
        
        txtCheckoutValue.setText(valueFormat);
    }
    
    private void cleanFields() {
        txtCheckoutName.setText("");
        txtCheckoutValue.setText("0,00");
        dpkCheckoutDate.setDate(Date.from(Instant.now()));
        
        txtCheckoutName.grabFocus();
    }
    
    private void initDatePicker() {
        dpkCheckoutDate.setFormats(new String[] {"dd/MM/yyyy"});
        dpkCheckoutDate.setDate(Date.from(Instant.now()));
        dpkCheckoutDate.setLinkDate(System.currentTimeMillis(), "Hoje é {0}");
    }
    
    private void closeScreen() {
        this.listCheckout = null;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCheckout = new javax.swing.JTable();
        btnExclude = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        lblCheckoutName = new javax.swing.JLabel();
        lblCheckoutValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCheckoutName = new javax.swing.JTextField();
        txtCheckoutValue = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        dpkCheckoutDate = new org.jdesktop.swingx.JXDatePicker();
        lblTotal = new javax.swing.JLabel();
        lblValueTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        tblCheckout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descrição", "Valor", "Data da Saída"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCheckout);
        if (tblCheckout.getColumnModel().getColumnCount() > 0) {
            tblCheckout.getColumnModel().getColumn(0).setMinWidth(50);
            tblCheckout.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblCheckout.getColumnModel().getColumn(0).setMaxWidth(50);
            tblCheckout.getColumnModel().getColumn(1).setMinWidth(310);
            tblCheckout.getColumnModel().getColumn(1).setPreferredWidth(310);
            tblCheckout.getColumnModel().getColumn(1).setMaxWidth(310);
            tblCheckout.getColumnModel().getColumn(2).setMinWidth(100);
            tblCheckout.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblCheckout.getColumnModel().getColumn(2).setMaxWidth(100);
            tblCheckout.getColumnModel().getColumn(3).setMinWidth(150);
            tblCheckout.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblCheckout.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        btnExclude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Delete.png"))); // NOI18N
        btnExclude.setText("Excluir");
        btnExclude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcludeActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Exit.png"))); // NOI18N
        btnClose.setText("Fechar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Registro de Saída");

        lblCheckoutName.setText("Descrição Saída..:");

        lblCheckoutValue.setText("Total Saída..:");

        jLabel1.setText("Data Saída..:");

        txtCheckoutValue.setText("0,00");
        txtCheckoutValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCheckoutValueKeyReleased(evt);
            }
        });

        btnRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnRegister.setText("Registrar");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("Total de Saída: R$");

        lblValueTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValueTotal.setText("0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(235, 235, 235))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCheckoutName)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtCheckoutName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCheckoutValue, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCheckoutValue))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(dpkCheckoutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblValueTotal)
                        .addGap(69, 69, 69))))
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(btnRegister)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExclude)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCheckoutName)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCheckoutValue)
                        .addComponent(jLabel1)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpkCheckoutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCheckoutName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCheckoutValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblValueTotal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExclude)
                    .addComponent(btnRegister)
                    .addComponent(btnClose))
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.closeScreen();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        this.registerCheckout();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void txtCheckoutValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCheckoutValueKeyReleased
        if (!txtCheckoutValue.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, Messages.verif_value_field);
                txtCheckoutValue.setText("0,00");
            } else {
                this.setMaskMoney();
            }
        }
    }//GEN-LAST:event_txtCheckoutValueKeyReleased

    private void btnExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcludeActionPerformed
        this.removeCheckout();
    }//GEN-LAST:event_btnExcludeActionPerformed

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
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Checkout dialog = new Checkout(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnExclude;
    private javax.swing.JButton btnRegister;
    private org.jdesktop.swingx.JXDatePicker dpkCheckoutDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCheckoutName;
    private javax.swing.JLabel lblCheckoutValue;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblValueTotal;
    private javax.swing.JTable tblCheckout;
    private javax.swing.JTextField txtCheckoutName;
    private javax.swing.JTextField txtCheckoutValue;
    // End of variables declaration//GEN-END:variables
}
