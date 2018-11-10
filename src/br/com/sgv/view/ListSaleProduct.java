package br.com.sgv.view;

import br.com.sgv.enumerator.CalcTypeEnum;
import br.com.sgv.model.Product;
import br.com.sgv.model.Sale;
import br.com.sgv.service.LogService;
import br.com.sgv.service.ProductService;
import br.com.sgv.service.SaleService;
import br.com.sgv.shared.ResponseModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Anderson Junior Rodrigues
 */
public class ListSaleProduct extends javax.swing.JDialog {

    private LogService logService = null;
    private DefaultTableModel table = null;
    private double valueTotal = 0;
    private double quantityTotal = 0;
    
    public ListSaleProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initScreen() {
        this.logService = new LogService(Product.class.getName(), "ListSaleProduct");
        this.initDatePicker();
        this.initComboboxProduct();
        
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private void initDatePicker() {
        dpkSearch.setFormats(new String[] {"dd/MM/yyyy"});
        dpkSearch.setDate(Date.from(Instant.now()));
        dpkSearch.setLinkDate(System.currentTimeMillis(), "Hoje é {0}");
    }
    
    private void initComboboxProduct() {
        try {
            this.logService.logMessage("busca da lista de produtos", "initComboboxProduct");
            ResponseModel<List<Product>> response = new ProductService().getProducts();
            List<Product> listProduct = response.getModel();

            listProduct.stream().forEach(product -> cbxProduct.addItem(product));
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initComboboxProduct");
            JOptionPane.showMessageDialog(null, "Falha ao buscar os produtos");
        }
    }
    
    private void getSaleByDayAndProductId() {
        try {
            if (verifyFields()) {
                Date dateSearch = dpkSearch.getDate();
                Product product = (Product) cbxProduct.getSelectedItem();
                
                this.logService.logMessage("busca de venda por data e produto", "getSaleByDayAndProductId");
                
                ResponseModel<List<Sale>> response = new SaleService().getSalesByPeriodicAndProductId(dateSearch, product.getId());
                List<Sale> listSale = response.getModel();
                
                this.logService.logMessage("atualizando tabela de dados", "getSaleByDayAndProductId");
                
                DefaultTableModel table = (DefaultTableModel) tableProduct.getModel();
                table.setNumRows(0);
                this.valueTotal = 0;
                this.quantityTotal = 0;
                
                if (listSale != null && listSale.size() > 0) {
                    listSale.stream().forEach(sale -> {
                        this.valueTotal += sale.getSaleTotal();
                        this.quantityTotal += sale.getAmount();

                        table.addRow(new Object[] {
                            sale.getProduct().getProductName(),
                            new SimpleDateFormat("HH:mm:ss").format(sale.getSaleDate()),
                            "R$ " + new DecimalFormat("#0.00").format(sale.getProduct().getProductValue()),
                            sale.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value ? (int)sale.getAmount() : new DecimalFormat("#0.000").format(sale.getAmount()),
                            "R$ " + new DecimalFormat("#0.00").format(sale.getSaleTotal())
                        });
                    });
                    
                    if (listSale.get(0).getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
                        lblQuantityValue.setText(String.valueOf((int)this.quantityTotal));
                    } else {
                        lblQuantityValue.setText(new DecimalFormat("#0.000").format(this.quantityTotal));
                    }
                    
                    lblValueTotal.setText("R$ " + new DecimalFormat("#0.00").format(this.valueTotal));
                    
                } else {
                    lblQuantityValue.setText("0");
                    lblValueTotal.setText("R$ " + new DecimalFormat("#0.00").format(this.valueTotal));
                    JOptionPane.showMessageDialog(null, "Nenhuma venda encontrada para o produto informado!");
                }
                
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "getSaleByDayAndProductId");
            JOptionPane.showMessageDialog(null, "Falha ao buscar os dados.");
        }
    }
    
    private boolean verifyFields() {
        boolean isVerify = true;
        String message = "";
        
        if (cbxProduct.getSelectedItem().equals("Selecione") || cbxProduct.getSelectedIndex() == 0) {
            message += "Produto é obrigatório!\n";
        }
        
        if (!message.isEmpty()){
            isVerify = false;
            JOptionPane.showMessageDialog(null, message);
        }
        
        return isVerify;
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
        dpkSearch = new org.jdesktop.swingx.JXDatePicker();
        cbxProduct = new javax.swing.JComboBox();
        btnSearch = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        lblProduto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        lblValue = new javax.swing.JLabel();
        lblValueTotal = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblQuantityValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Consulta de Venda por Produto");

        cbxProduct.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Report.png"))); // NOI18N
        btnSearch.setText("Consultar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblSearch.setText("Data Pesquisa");

        lblProduto.setText("Produto");

        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Hora Venda", "Valor Produto", "Quantidade", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tableProduct);
        if (tableProduct.getColumnModel().getColumnCount() > 0) {
            tableProduct.getColumnModel().getColumn(0).setResizable(false);
            tableProduct.getColumnModel().getColumn(0).setPreferredWidth(220);
            tableProduct.getColumnModel().getColumn(1).setResizable(false);
            tableProduct.getColumnModel().getColumn(1).setPreferredWidth(70);
            tableProduct.getColumnModel().getColumn(2).setResizable(false);
            tableProduct.getColumnModel().getColumn(2).setPreferredWidth(70);
            tableProduct.getColumnModel().getColumn(3).setResizable(false);
            tableProduct.getColumnModel().getColumn(3).setPreferredWidth(70);
            tableProduct.getColumnModel().getColumn(4).setResizable(false);
            tableProduct.getColumnModel().getColumn(4).setPreferredWidth(70);
        }

        lblValue.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValue.setText("Valor Total..:");

        lblValueTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValueTotal.setText("R$ 0,00");

        lblQuantity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblQuantity.setText("Quantidade Total..:");

        lblQuantityValue.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblQuantityValue.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpkSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSearch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProduto)
                                    .addComponent(cbxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)
                                .addGap(0, 25, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(lblTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(294, 294, 294)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblQuantity)
                                    .addComponent(lblValue))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblQuantityValue)
                                    .addComponent(lblValueTotal))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpkSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(lblQuantityValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValue)
                    .addComponent(lblValueTotal))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        this.getSaleByDayAndProductId();
    }//GEN-LAST:event_btnSearchActionPerformed

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
            java.util.logging.Logger.getLogger(ListSaleProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListSaleProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListSaleProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListSaleProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListSaleProduct dialog = new ListSaleProduct(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cbxProduct;
    private org.jdesktop.swingx.JXDatePicker dpkSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblQuantityValue;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblValue;
    private javax.swing.JLabel lblValueTotal;
    private javax.swing.JTable tableProduct;
    // End of variables declaration//GEN-END:variables
}
