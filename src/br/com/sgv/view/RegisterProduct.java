package br.com.sgv.view;

import br.com.sgv.enumerator.CalcTypeEnum;
import br.com.sgv.model.MeasureType;
import br.com.sgv.model.Product;
import br.com.sgv.service.LogService;
import br.com.sgv.service.MeasureTypeService;
import br.com.sgv.service.ProductService;
import br.com.sgv.shared.FormatMoney;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author Anderson Junior Rodrigues
 */
public class RegisterProduct extends javax.swing.JDialog {

    private ListProduct jDialog = null;
    private ResponseModel<Boolean> responseProduct = null;
    private ResponseModel<List<MeasureType>> response = null;
    private List<MeasureType> listMeasureType = null;
    private boolean isUpdateProduct = false;
    private long idProductUpdate = 0;
    private LogService logService = null;
    
    public RegisterProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.getMeasureType();
    }
    
    public void initScreen() {
        this.logService = new LogService(Product.class.getName(), "RegisterProduct");
        
        if (this.listMeasureType == null) {
            this.getMeasureType();
        }
                
        this.setSize(600, 350);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    public void initScreenUpdate(Product product, boolean isUpdate, ListProduct jDialog) {
        this.logService = new LogService(Product.class.getName(), "RegisterProduct");
        this.logService.logMessage("atualização de produto cancelada", "initScreenUpdate");
        String value = FormatMoney.verifyDecimal(String.valueOf(product.getProductValue()), CalcTypeEnum.UNITY.value);
        value = FormatMoney.formatMoney(value);
        
        int index = this.listMeasureType.indexOf(product.getMeasureType());
        
        if (index < 0) {
            MeasureType measureType = this.listMeasureType
                    .stream()
                    .filter(x -> x.getId() == product.getMeasureType().getId())
                    .findAny()
                    .orElse(null);
            
            index = this.listMeasureType.indexOf(measureType);
        }
        
        this.lblTitle.setText(Messages.title_update_product);
        this.btnRegister.setText(Messages.btn_update_product);
        this.txtKey.setText(product.getProductKey());
        this.txtName.setText(product.getProductName());
        this.frmValue.setText(value);
        this.cbxMeasureType.setSelectedIndex(++index);
        this.idProductUpdate = product.getId();
        
        this.isUpdateProduct = isUpdate;
        this.jDialog = jDialog;
        
        this.initScreen();
    }
    
    private void getMeasureType() {
        this.response = new MeasureTypeService().getMeasureType();
        this.listMeasureType = this.response.getModel();
        
        if (this.listMeasureType != null && this.listMeasureType.size() > 0) {
            this.listMeasureType.stream().forEach(type -> { 
                cbxMeasureType.addItem(type);
            });
        }
    }
    
    private void registerProduct() {
        if (verifyFields()) {
            String productKey = txtKey.getText().trim();
            String productName = txtName.getText();
            String productValue = frmValue.getText().replaceAll("\\.", "").replace(",", ".");
            MeasureType measureType = (MeasureType)cbxMeasureType.getSelectedItem();
            
            if (this.isUpdateProduct) {
                this.responseProduct = new ProductService().updateProduct(this.idProductUpdate, productKey, productName, productValue, measureType);                
            } else {
                this.responseProduct = new ProductService().saveProduct(productKey, productName, productValue, measureType);
            }
            
            if (responseProduct.getModel()) {
                if (this.isUpdateProduct) {
                    this.isUpdateProduct = false;
                    this.idProductUpdate = 0;
                    JOptionPane.showMessageDialog(null, Messages.update_sucess);
                    this.closeScreen();
                    this.jDialog.initScreen();
                } else {
                    JOptionPane.showMessageDialog(null, Messages.save_success);
                    this.clearFields();
                }
            } else {
                JOptionPane.showMessageDialog(null, response.getMensage());
            }
        }
    }
    
    private boolean verifyFields() {
        String message = "";
        boolean isVerify = true;
        
        
        if (txtKey.getText().trim().isEmpty()) {
            message += Messages.key_register_product + "\n";
        }
        
        if (txtName.getText().isEmpty()) {
            message += Messages.name_register_product + "\n";
        }
        
        if (frmValue.getText().isEmpty() || frmValue.getText().equals("0,00")) {
            message += Messages.value_register_product + "\n";
        }
        
        if (cbxMeasureType.getSelectedItem().equals("Selecione") || cbxMeasureType.getSelectedIndex() == 0) {
            message += Messages.measure_register_product + "\n";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isVerify = false;
        }
        
        return isVerify;
    }
    
    private void setMaskMoney() {
        String valueDigit = frmValue.getText();
        String valueFormat = FormatMoney.formatMoney(valueDigit);
        
        frmValue.setText(valueFormat);
    }
    
    private void clearFields() {
        txtKey.setText("");
        txtName.setText("");
        frmValue.setText("0,00");
        cbxMeasureType.setSelectedIndex(0);
        
        txtKey.grabFocus();
    }
    
    private void closeScreen() {
        this.closeSystem();
        this.dispose();
    }
    
    private void closeSystem() {
        this.response = null;
        this.listMeasureType = null;
        this.responseProduct = null;
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
        lblKey = new javax.swing.JLabel();
        txtKey = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblValue = new javax.swing.JLabel();
        lblMeasure = new javax.swing.JLabel();
        cbxMeasureType = new javax.swing.JComboBox();
        btnRegister = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        frmValue = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Cadastro de Produto");

        lblKey.setText("Código do Produto..:");

        txtKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKeyKeyReleased(evt);
            }
        });

        lblName.setText("Nome do Produto..:");

        lblValue.setText("Valor do Produto..:");

        lblMeasure.setText("Tipo de Medida..:");

        cbxMeasureType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        btnRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Create.png"))); // NOI18N
        btnRegister.setText("Cadastrar");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        btnRegister.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRegisterKeyPressed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        btnCancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelKeyPressed(evt);
            }
        });

        frmValue.setText("0,00");
        frmValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                frmValueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(223, 223, 223))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblValue)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(146, 146, 146)
                                .addComponent(btnRegister))
                            .addComponent(frmValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMeasure)
                            .addComponent(btnCancel)
                            .addComponent(cbxMeasureType, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblName)
                    .addComponent(lblKey)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addComponent(lblKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValue)
                    .addComponent(lblMeasure))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMeasureType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frmValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnRegister))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        this.registerProduct();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void frmValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_frmValueKeyReleased
        if (!frmValue.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, Messages.verif_value_field);
                frmValue.setText("0,00");
            } else {
                this.setMaskMoney();
            }
        }
    }//GEN-LAST:event_frmValueKeyReleased

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.closeScreen();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.closeScreen();
        }
    }//GEN-LAST:event_btnCancelKeyPressed

    private void btnRegisterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRegisterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.registerProduct();
        }
    }//GEN-LAST:event_btnRegisterKeyPressed

    private void txtKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeyKeyReleased
        String text = txtKey.getText().toUpperCase();
        txtKey.setText(text);
    }//GEN-LAST:event_txtKeyKeyReleased

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
            java.util.logging.Logger.getLogger(RegisterProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterProduct dialog = new RegisterProduct(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox cbxMeasureType;
    private javax.swing.JFormattedTextField frmValue;
    private javax.swing.JLabel lblKey;
    private javax.swing.JLabel lblMeasure;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtKey;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
