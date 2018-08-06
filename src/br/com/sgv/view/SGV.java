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
    private ListProduct listProduct = null;
    private ListMeasure listMeasure = null;
    private RegisterUser registerUser = null;
    private RegisterProduct registerProduct = null;
    private RegisterMeasure registerMeasure = null;
    private Checkout checkout = null;
    
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
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
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
            this.listProduct = null;
            this.listMeasure = null;
            this.registerUser = null;
            this.user = null;
            this.userType = null;
            this.listAcessPermission = null;
            this.registerMeasure = null;
            this.registerProduct = null;
            this.checkout = null;
            
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
    
    private void initListMeasure() {
        this.screenType = AcessScreenEnum.VIEW_MEASURE.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.listMeasure = new ListMeasure(this, true);
            this.listMeasure.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initListProduct() {
        this.screenType = AcessScreenEnum.VIEW_PRODUCT.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.listProduct = new ListProduct(this, true);
            this.listProduct.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initCheckout() {
        this.screenType = AcessScreenEnum.CHECK_OUT.value;
        this.acessPermission = this.verifyPermissionAcess(this.screenType);
        
        if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
            this.checkout = new Checkout(this, true);
            this.checkout.initScreen();
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableItems = new javax.swing.JTable();
        lblProductKey = new javax.swing.JLabel();
        txtProductKey = new javax.swing.JTextField();
        lblProductName = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        lblNetValue = new javax.swing.JLabel();
        txtNetValue = new javax.swing.JTextField();
        txtAmount = new javax.swing.JTextField();
        txtGrossValue = new javax.swing.JTextField();
        lblAmont = new javax.swing.JLabel();
        lblGrossValue = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnExclude = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblDiscountValue = new javax.swing.JLabel();
        txtDiscountValue = new javax.swing.JTextField();
        txtTotalValue = new javax.swing.JTextField();
        lblTotalValue = new javax.swing.JLabel();
        lblAmountPaid = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        lblValueChange = new javax.swing.JLabel();
        txtValueChange = new javax.swing.JTextField();
        btnFinalizeSale = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
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
        setTitle("Sistema de Gerenciamento de Vendas");
        setBackground(new java.awt.Color(153, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Item Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tableItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome Produto", "Valor Produto", "Quantidade", "Valor Bruto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tableItems);
        if (tableItems.getColumnModel().getColumnCount() > 0) {
            tableItems.getColumnModel().getColumn(0).setMinWidth(50);
            tableItems.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableItems.getColumnModel().getColumn(0).setMaxWidth(50);
            tableItems.getColumnModel().getColumn(1).setMinWidth(317);
            tableItems.getColumnModel().getColumn(1).setPreferredWidth(317);
            tableItems.getColumnModel().getColumn(1).setMaxWidth(317);
            tableItems.getColumnModel().getColumn(2).setMinWidth(150);
            tableItems.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableItems.getColumnModel().getColumn(2).setMaxWidth(150);
            tableItems.getColumnModel().getColumn(3).setMinWidth(100);
            tableItems.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableItems.getColumnModel().getColumn(3).setMaxWidth(100);
            tableItems.getColumnModel().getColumn(4).setMinWidth(150);
            tableItems.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableItems.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        lblProductKey.setText("Código Produto..:");

        lblProductName.setText("Produto..:");

        lblNetValue.setText("Valor Líquido..:");

        lblAmont.setText("Quantidade..:");

        lblGrossValue.setText("Valor Bruto..:");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Add.png"))); // NOI18N
        btnAdd.setText("Adicionar");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/View.png"))); // NOI18N
        btnSearch.setText("Pesquisar");

        btnExclude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Delete.png"))); // NOI18N
        btnExclude.setText("Excluir");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductKey, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductKey))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblProductName)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtProductName)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNetValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAmont))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGrossValue)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtGrossValue))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExclude)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductKey)
                    .addComponent(lblProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNetValue)
                    .addComponent(lblAmont)
                    .addComponent(lblGrossValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNetValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrossValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnExclude)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Dados Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiscountValue.setText("Valor Desconto..:");

        txtDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txtTotalValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalValue.setText("Valor Total..:");

        lblAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAmountPaid.setText("Valor Pago..:");

        txtAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblValueChange.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblValueChange.setText("Valor Troco..:");

        txtValueChange.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnFinalizeSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnFinalizeSale.setText("Finalizar Venda");

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDiscountValue)
                    .addComponent(txtDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalValue)
                    .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAmountPaid)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblValueChange)
                        .addGap(0, 106, Short.MAX_VALUE))
                    .addComponent(txtValueChange))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(btnFinalizeSale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(lblTotalValue)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(lblDiscountValue)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(lblAmountPaid)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(lblValueChange)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtValueChange, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizeSale)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        nmRegister.setText("Cadastro");

        nmRegisterProduct.setText("Cadastrar Produto");
        nmRegisterProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmRegisterProductActionPerformed(evt);
            }
        });
        nmRegister.add(nmRegisterProduct);

        mnRegisterMeasure.setText("Cadastrar Volume");
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
        nmListProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmListProductActionPerformed(evt);
            }
        });
        jMenu2.add(nmListProduct);

        mnMeasure.setText("Listar Volume");
        mnMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMeasureActionPerformed(evt);
            }
        });
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
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
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
            .addGroup(layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void mnMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMeasureActionPerformed
        this.initListMeasure();
    }//GEN-LAST:event_mnMeasureActionPerformed

    private void nmListProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmListProductActionPerformed
        this.initListProduct();
    }//GEN-LAST:event_nmListProductActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.initCheckout();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnExclude;
    private javax.swing.JButton btnFinalizeSale;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblAmont;
    private javax.swing.JLabel lblAmountPaid;
    private javax.swing.JLabel lblDiscountValue;
    private javax.swing.JLabel lblGrossValue;
    private javax.swing.JLabel lblNetValue;
    private javax.swing.JLabel lblProductKey;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblValueChange;
    private javax.swing.JMenuItem mnMeasure;
    private javax.swing.JMenuItem mnRegisterMeasure;
    private javax.swing.JMenu nmAbout;
    private javax.swing.JMenu nmExit;
    private javax.swing.JMenuItem nmListProduct;
    private javax.swing.JMenuItem nmListUser;
    private javax.swing.JMenu nmRegister;
    private javax.swing.JMenuItem nmRegisterProduct;
    private javax.swing.JMenuItem nmRegisterUser;
    private javax.swing.JTable tableItems;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtAmountPaid;
    private javax.swing.JTextField txtDiscountValue;
    private javax.swing.JTextField txtGrossValue;
    private javax.swing.JTextField txtNetValue;
    private javax.swing.JTextField txtProductKey;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtTotalValue;
    private javax.swing.JTextField txtValueChange;
    // End of variables declaration//GEN-END:variables
}
