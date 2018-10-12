package br.com.sgv.view;

import br.com.sgv.model.TotalizeSale;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.service.LogService;
import br.com.sgv.service.TotalizationSaleService;
import br.com.sgv.service.TransactionSaleService;
import br.com.sgv.shared.Messages;
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
public class RegisterTotalization extends javax.swing.JDialog {

    private LogService logService = null;
    private DefaultTableModel table = null;
    private double valueTotal;
    
    public RegisterTotalization(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initScreen() {
        this.logService = new LogService(TotalizeSale.class.getName(), "RegisterTotalization");
        this.initDatePicker();
        
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private void initDatePicker() {
        dpkCloseDate.setFormats(new String[] {"dd/MM/yyyy"});
        dpkCloseDate.setDate(Date.from(Instant.now()));
        dpkCloseDate.setLinkDate(System.currentTimeMillis(), "Hoje é {0}");
    }
    
    private void totalizeSale() {
        if (validateFields()) {
            this.logService.logMessage("inicio do processo de totalizacao", "totalizeSale");
            Date dateSearch = dpkCloseDate.getDate();
            this.valueTotal = 0;
            String message = "";
            
            this.table = null;
            this.table = (DefaultTableModel)tableTotalize.getModel();
            this.table.setNumRows(0);
            
            ResponseModel<Boolean> response = new TotalizationSaleService(true).totalizerSaleDay(dateSearch);
            
            if (response.getModel()) {
                ResponseModel<List<TransactionSale>> list = new TransactionSaleService().getTransactionSaleByDay(dateSearch);
                List<TransactionSale> listTransactionSale = list.getModel();               
                
                listTransactionSale.stream().forEach(transaction -> {
                    this.valueTotal += transaction.getTotalValue();
                    
                    this.table.addRow(new Object[]{
                        transaction.getTransactionId(),
                        new SimpleDateFormat("dd/MM/yyyy").format(transaction.getRegisterDate()),
                        transaction.getPayType().getPayType(),
                        "R$ " + new DecimalFormat("#0.00").format(transaction.getTotalValue())
                    });
                });
                
                message = "Totalização Realizada com sucesso!";
            } else {
                message = response.getMensage();
            }
            
            String value = new DecimalFormat("#0.00").format(this.valueTotal);
            lblValeuTotalize.setText(value);
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    private boolean validateFields() {
        boolean isValid = true;
        Date now = Date.from(Instant.now());
        String message = "";
        
        if (dpkCloseDate.getDate().toString().isEmpty()) {
            message += "Data obrigatória!";
        } else if (dpkCloseDate.getDate().after(now)) {
            message += "Data informada não pode ser maior que a data atual.";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isValid = false;
        }
        
        return isValid;
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
        lblCloseDate = new javax.swing.JLabel();
        dpkCloseDate = new org.jdesktop.swingx.JXDatePicker();
        btnTotalize = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTotalize = new javax.swing.JTable();
        lblTotalTotalize = new javax.swing.JLabel();
        lblValeuTotalize = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Fechamento de Vendas Dia");

        lblCloseDate.setText("Data Fechamento..:");

        btnTotalize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Report.png"))); // NOI18N
        btnTotalize.setText("Totalizar");
        btnTotalize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalizeActionPerformed(evt);
            }
        });

        tableTotalize.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Transação", "Data Venda", "Tipo Pagamento", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tableTotalize);
        if (tableTotalize.getColumnModel().getColumnCount() > 0) {
            tableTotalize.getColumnModel().getColumn(0).setMinWidth(180);
            tableTotalize.getColumnModel().getColumn(0).setPreferredWidth(180);
            tableTotalize.getColumnModel().getColumn(0).setMaxWidth(180);
            tableTotalize.getColumnModel().getColumn(1).setMinWidth(150);
            tableTotalize.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableTotalize.getColumnModel().getColumn(1).setMaxWidth(150);
            tableTotalize.getColumnModel().getColumn(2).setMinWidth(150);
            tableTotalize.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableTotalize.getColumnModel().getColumn(2).setMaxWidth(150);
            tableTotalize.getColumnModel().getColumn(3).setMinWidth(100);
            tableTotalize.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableTotalize.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        lblTotalTotalize.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalTotalize.setText("Valor Total..: R$");

        lblValeuTotalize.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValeuTotalize.setText("0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(207, 207, 207)
                                .addComponent(lblTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCloseDate)
                                    .addComponent(dpkCloseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTotalize)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTotalTotalize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValeuTotalize)
                .addGap(100, 100, 100))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCloseDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpkCloseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTotalize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalTotalize)
                    .addComponent(lblValeuTotalize))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTotalizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalizeActionPerformed
        this.totalizeSale();
    }//GEN-LAST:event_btnTotalizeActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterTotalization dialog = new RegisterTotalization(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTotalize;
    private org.jdesktop.swingx.JXDatePicker dpkCloseDate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCloseDate;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalTotalize;
    private javax.swing.JLabel lblValeuTotalize;
    private javax.swing.JTable tableTotalize;
    // End of variables declaration//GEN-END:variables
}