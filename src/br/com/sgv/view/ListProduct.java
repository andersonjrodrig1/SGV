/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.view;

import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.model.Product;
import br.com.sgv.service.ProductService;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class ListProduct extends javax.swing.JDialog {

    /**
     * Creates new form ListProduct
     */
    
    private RegisterProduct registerProduct = null;
    private ResponseModel<List<Product>> listResponse = null;
    private List<Product> listProduct = null;
    
    public ListProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initScreen() {
        this.getProducts();
                
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private void getProducts() {
        this.listResponse = new ProductService().getProducts();
        this.listProduct = this.listResponse.getModel();
        
        this.setTableProduct();
    }
    
    private void getProductByNameOrKey() {
        String productKey = txtProductKey.getText().trim();
        String productName = txtProductName.getText();
        
        listResponse = new ProductService().getProductByNameOrKey(productKey, productName);
        this.listProduct = this.listResponse.getModel();
        
        this.setTableProduct();
    }
    
    private void removeProduct() {
        int row = tblProducts.getSelectedRow();
        
        if (row >= 0) {
            int option = JOptionPane.showConfirmDialog(null, Messages.remove_modal);
            
            if (option == OptionEnum.YES.value) {
                Object objColumn = tblProducts.getValueAt(row, 0);
                int idProduct = Integer.valueOf(objColumn.toString());
                
                Product productSelect = this.listProduct
                        .stream()
                        .filter(x -> x.getId() == idProduct)
                        .findAny()
                        .orElse(null);
                
                //TODO: verify if product has send

                ResponseModel<Boolean> response = new ProductService().removeProduct(productSelect);

                if (response.getModel() == true) {
                    this.listProduct = this.listProduct
                            .stream()
                            .filter(x -> x.getId() != productSelect.getId())
                            .collect(Collectors.toList());

                    this.setTableProduct();

                    JOptionPane.showMessageDialog(null, Messages.remove_sucess);
                } else {
                    JOptionPane.showMessageDialog(null, response.getMensage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, Messages.select_row);
        }
    }
    
    private void updateProduct() {
        int row = tblProducts.getSelectedRow();
        
        if (row >= 0) {
            Object objColumn = tblProducts.getValueAt(row, 0);
            int idProduct = Integer.valueOf(objColumn.toString());
            Product productSelect = this.listProduct
                        .stream()
                        .filter(x -> x.getId() == idProduct)
                        .findAny()
                        .orElse(null);
            this.dispose();
            this.registerProduct = new RegisterProduct(new SGV(), true);
            this.registerProduct.initScreenUpdate(productSelect, true, new ListProduct(null, true));
        } else {
            JOptionPane.showMessageDialog(null, Messages.select_row);
        }
    }
    
    private void setTableProduct() {
        if (this.listProduct != null && this.listProduct.size() > 0) {
            DefaultTableModel table = (DefaultTableModel)tblProducts.getModel();
            table.setNumRows(0);
            
            this.listProduct.stream().forEach(product -> {
                table.addRow(new Object[] {
                    product.getId(),
                    product.getProductKey(),
                    product.getProductName(),
                    "R$ " + product.getProductValue(),
                    product.getMeasureType().getMeasureType()
                });
            });            
        }
    }
    
    private void closeScreen() {
        this.listResponse = null;
        this.listProduct = null;
        this.registerProduct = null;
        
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
        tblProducts = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();
        lblProductKey = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        txtProductKey = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        btnChange = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Código", "Produto", "Valor", "Tipo Medida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProducts);
        if (tblProducts.getColumnModel().getColumnCount() > 0) {
            tblProducts.getColumnModel().getColumn(0).setMinWidth(40);
            tblProducts.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblProducts.getColumnModel().getColumn(0).setMaxWidth(40);
            tblProducts.getColumnModel().getColumn(1).setMinWidth(100);
            tblProducts.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblProducts.getColumnModel().getColumn(1).setMaxWidth(100);
            tblProducts.getColumnModel().getColumn(2).setMinWidth(210);
            tblProducts.getColumnModel().getColumn(2).setPreferredWidth(210);
            tblProducts.getColumnModel().getColumn(2).setMaxWidth(210);
            tblProducts.getColumnModel().getColumn(3).setMinWidth(100);
            tblProducts.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblProducts.getColumnModel().getColumn(3).setMaxWidth(100);
            tblProducts.getColumnModel().getColumn(4).setMinWidth(140);
            tblProducts.getColumnModel().getColumn(4).setPreferredWidth(140);
            tblProducts.getColumnModel().getColumn(4).setMaxWidth(140);
        }

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Produtos Cadastrados");

        lblProductKey.setText("Código Produto..:");

        lblProductName.setText("Nome Produto..:");

        txtProductKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductKeyKeyReleased(evt);
            }
        });

        txtProductName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductNameKeyReleased(evt);
            }
        });

        btnChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Modify.png"))); // NOI18N
        btnChange.setText("Alterar");
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Delete.png"))); // NOI18N
        btnRemove.setText("Excluir");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Exit.png"))); // NOI18N
        btnClose.setText("Fechar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Green pin.png"))); // NOI18N
        btnSelect.setText("Selecionar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(lblTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblProductKey)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtProductKey)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProductName)
                                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChange)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(lblProductKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChange)
                    .addComponent(btnClose)
                    .addComponent(btnRemove)
                    .addComponent(btnSelect))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.closeScreen();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtProductKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductKeyKeyReleased
        this.getProductByNameOrKey();
    }//GEN-LAST:event_txtProductKeyKeyReleased

    private void txtProductNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductNameKeyReleased
        this.getProductByNameOrKey();
    }//GEN-LAST:event_txtProductNameKeyReleased

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        this.removeProduct();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        this.updateProduct();
    }//GEN-LAST:event_btnChangeActionPerformed

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
            java.util.logging.Logger.getLogger(ListProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListProduct dialog = new ListProduct(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSelect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblProductKey;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtProductKey;
    private javax.swing.JTextField txtProductName;
    // End of variables declaration//GEN-END:variables
}
