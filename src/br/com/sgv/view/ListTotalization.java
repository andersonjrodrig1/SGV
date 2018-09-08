package br.com.sgv.view;

import br.com.sgv.enumerator.ReportTypeEnum;
import br.com.sgv.model.ReportType;
import br.com.sgv.model.TotalizeSale;
import br.com.sgv.service.ReportTypeService;
import br.com.sgv.service.TotalizationSaleService;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Anderson Junior Rodrigues
 */
public class ListTotalization extends javax.swing.JDialog {

    private DefaultTableModel table = null;
    private double valeuTotal;
            
    public ListTotalization(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initScreen() {
        this.initDatePicker();
        this.initComboBoxReportType();
        
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    private void initDatePicker() {
        dpkDateInit.setFormats(new String[] {"dd/MM/yyyy"});
        dpkDateInit.setDate(Date.from(Instant.now()));
        dpkDateInit.setLinkDate(System.currentTimeMillis(), "Hoje é {0}");
        
        dpkDateFinal.setFormats(new String[] {"dd/MM/yyyy"});
        dpkDateFinal.setDate(Date.from(Instant.now()));
        dpkDateFinal.setLinkDate(System.currentTimeMillis(), "Hoje é {0}");
    }
    
    private void initComboBoxReportType() {
        ResponseModel<List<ReportType>> response = new ReportTypeService().getReportTypes();
        
        if (response.getModel() != null && response.getModel().size() > 0) {
            List<ReportType> listReport = response.getModel();
            
            listReport.stream().forEach(report -> {
                cbxCloseType.addItem(report);
            });
        } else {
            JOptionPane.showMessageDialog(null, response.getMensage());
        }
    }
    
    private void getTotalizationPeriodic() {
        if (validateFields()) {
            Date dateInitial = dpkDateInit.getDate();
            Date dateFinal = dpkDateFinal.getDate();
            
            ResponseModel<List<TotalizeSale>> response = new TotalizationSaleService().getTotalizationSaleByPeriodic(dateInitial, dateFinal);
            List<TotalizeSale> listTotalizeSales = response.getModel();
            
            this.valeuTotal = 0; 
            this.table = null;
            this.table = (DefaultTableModel)tableReport.getModel();
            this.table.setRowCount(0);
            
            if (listTotalizeSales != null && listTotalizeSales.size() > 0) {
                ReportType reportType = (ReportType)cbxCloseType.getSelectedItem();
                int reportTypeId = reportType.getId() == ReportTypeEnum.PAID.value ? ReportTypeEnum.PAID.value : ReportTypeEnum.SALE.value;
                
                listTotalizeSales = listTotalizeSales
                            .stream()
                            .filter(totalization -> totalization.getReportType().getId() == reportTypeId)
                            .collect(Collectors.toList());
                
                listTotalizeSales.stream().forEach(totalization -> {
                    this.valeuTotal += totalization.getTotalValue();
                    
                    this.table.addRow(new Object[] {
                        totalization.getDescrition(),
                        new SimpleDateFormat("dd/MM/yyyy").format(totalization.getSaleDate()),
                        new SimpleDateFormat("dd/MM/yyyy").format(totalization.getRegisterDate()),
                        "R$ " + new DecimalFormat("#0.00").format(totalization.getTotalValue())
                    });
                });
                
                String value = new DecimalFormat("#0.00").format(this.valeuTotal);
                lblValueTotal.setText(value);
            } else {
                String value = new DecimalFormat("#0.00").format(this.valeuTotal);
                lblValueTotal.setText(value);
                JOptionPane.showMessageDialog(null, Messages.report_found);
            }
        }
    }
    
    private boolean validateFields() {
        boolean isValidate = true;
        String message = "";
        
        if (dpkDateInit.getDate().toString().isEmpty()) {
            message += Messages.init_date_required + "\n";
        }
        
        if (dpkDateFinal.getDate().toString().isEmpty()) {
            message += Messages.final_date_required + "\n";
        }
        
        if (dpkDateFinal.getDate().before(dpkDateInit.getDate())) {
            message += Messages.validate_date + "\n";
        }
        
        long days = ((dpkDateFinal.getDate().getTime() - dpkDateInit.getDate().getTime()) + 3600000) / 86400000L;
        
        if ((int)days > 30) {
            message += Messages.peridic_invalid + "\n";
        }
        
        if (cbxCloseType.getSelectedItem().equals("Selecione") || cbxCloseType.getSelectedIndex() == 0) {
            message += Messages.report_required + "\n";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isValidate = false;
        }
        
        return isValidate;
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
        tableReport = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();
        lblDateInit = new javax.swing.JLabel();
        dpkDateInit = new org.jdesktop.swingx.JXDatePicker();
        lblDateFinal = new javax.swing.JLabel();
        dpkDateFinal = new org.jdesktop.swingx.JXDatePicker();
        lblCloseType = new javax.swing.JLabel();
        cbxCloseType = new javax.swing.JComboBox();
        btnCheckReport = new javax.swing.JButton();
        lblTextValue = new javax.swing.JLabel();
        lblValueTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");

        tableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descrição", "Data Venda", "Data Totalização", "Valor"
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
        jScrollPane1.setViewportView(tableReport);
        if (tableReport.getColumnModel().getColumnCount() > 0) {
            tableReport.getColumnModel().getColumn(0).setMinWidth(180);
            tableReport.getColumnModel().getColumn(0).setPreferredWidth(180);
            tableReport.getColumnModel().getColumn(0).setMaxWidth(180);
            tableReport.getColumnModel().getColumn(1).setMinWidth(150);
            tableReport.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableReport.getColumnModel().getColumn(1).setMaxWidth(150);
            tableReport.getColumnModel().getColumn(2).setMinWidth(150);
            tableReport.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableReport.getColumnModel().getColumn(2).setMaxWidth(150);
            tableReport.getColumnModel().getColumn(3).setMinWidth(150);
            tableReport.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableReport.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setText("Consulta de Fechamento de Vendas");

        lblDateInit.setText("Data Inicio");

        lblDateFinal.setText("Data Final");

        lblCloseType.setText("Tipo de Fechamento");

        cbxCloseType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        btnCheckReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Report.png"))); // NOI18N
        btnCheckReport.setText("Consultar");
        btnCheckReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckReportActionPerformed(evt);
            }
        });

        lblTextValue.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTextValue.setText("Valor Total..: R$");

        lblValueTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValueTotal.setText("0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpkDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDateInit))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpkDateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDateFinal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCloseType)
                                    .addComponent(cbxCloseType, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCheckReport)
                                .addGap(0, 22, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTitle)
                        .addGap(175, 175, 175))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTextValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValueTotal)
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDateFinal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDateInit)
                                .addComponent(lblCloseType)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dpkDateFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpkDateInit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCloseType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCheckReport)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextValue)
                    .addComponent(lblValueTotal))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckReportActionPerformed
        this.getTotalizationPeriodic();
    }//GEN-LAST:event_btnCheckReportActionPerformed

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
            java.util.logging.Logger.getLogger(ListTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListTotalization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListTotalization dialog = new ListTotalization(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCheckReport;
    private javax.swing.JComboBox cbxCloseType;
    private org.jdesktop.swingx.JXDatePicker dpkDateFinal;
    private org.jdesktop.swingx.JXDatePicker dpkDateInit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCloseType;
    private javax.swing.JLabel lblDateFinal;
    private javax.swing.JLabel lblDateInit;
    private javax.swing.JLabel lblTextValue;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblValueTotal;
    private javax.swing.JTable tableReport;
    // End of variables declaration//GEN-END:variables
}
