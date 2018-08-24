package br.com.sgv.view;

import br.com.sgv.enumerator.AcessScreenEnum;
import br.com.sgv.enumerator.CalcTypeEnum;
import br.com.sgv.enumerator.MeasureTypeEnum;
import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.enumerator.PayTypeEnum;
import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.MeasureType;
import br.com.sgv.model.PayType;
import br.com.sgv.model.Product;
import br.com.sgv.model.Sale;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import br.com.sgv.service.AcessPermissionService;
import br.com.sgv.service.ProductService;
import br.com.sgv.service.TransactionSaleService;
import br.com.sgv.service.UserTypeService;
import br.com.sgv.shared.FormatMoney;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Anderson Junior Rodrigues
 */
public class SGV extends javax.swing.JFrame {

    private DefaultTableModel table = null;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private TransactionSale transactionSale = null;
    private List<Sale> listItemsSale = null;
    private int screenType;
    
    private double discountValue = 0d;
    private double totalValue = 0d;
    private double valueWithDiscount = 0d;
    private double paidValue = 0d;
    private double changeValue = 0d;
    private int amountSale = 0;
    private double weightSale = 0d;
    
    private Login login = null;
    private About about = null;
    private ListUser listUser = null;
    private ListProduct listProduct = null;
    private ListMeasure listMeasure = null;
    private RegisterUser registerUser = null;
    private RegisterProduct registerProduct = null;
    private RegisterMeasure registerMeasure = null;
    private Checkout checkout = null;
    private Sale itemSale = null;
    
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
        initFieldsScreen();
        initTableItems();
    }
    
    public void initScreen() {     
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setLocation(0, 0);
        this.setSize((int)rect.getWidth(), (int)rect.getHeight());       
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void initFieldsScreen() {
        txtProductKey.setBackground(Color.WHITE);
        txtProductName.setBackground(Color.WHITE);
        txtNetValue.setBackground(Color.WHITE);
        txtAmount.setBackground(Color.WHITE);
        txtGrossValue.setBackground(Color.WHITE);
        txtDiscountValue.setBackground(Color.WHITE);
        txtTotalValue.setBackground(Color.WHITE);
        txtAmountPaid.setBackground(Color.WHITE);
        txtValueChange.setBackground(Color.WHITE);
    }
    
    private void initTableItems() {
        this.listItemsSale = new ArrayList<>();
        
        this.table = (DefaultTableModel)tableItems.getModel();
        this.table.setRowCount(0);
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
            this.listProduct.setFrame(this);
            this.listProduct.initScreen();
        } else {
            JOptionPane.showMessageDialog(null, Messages.negative_acess, "Acesso Negado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initListProduct(SGV frame) {
        this.listProduct = new ListProduct(frame, true);
        this.listProduct.setFrame(frame);
        this.listProduct.initScreen();
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
    
    private void getProductByKey() {
        String productKey = txtProductKey.getText().trim();        
        ResponseModel<Product> response = new ProductService().getProductByKey(productKey);
        Product item = response.getModel();
        
        this.setItemSale(item);
    }
    
    public void setItemSale(Product item) {
        if (item != null) {
            this.itemSale = new Sale();
            this.itemSale.setProduct(item);
            this.itemSale.setSaleTotal(item.getProductValue());
            
            String valueItem = this.df.format(this.itemSale.getProduct().getProductValue());
            valueItem = FormatMoney.formatMoney(valueItem);
            
            txtProductKey.setText(this.itemSale.getProduct().getProductKey());
            txtProductName.setText(this.itemSale.getProduct().getProductName());
            txtNetValue.setText("R$ " + valueItem);
            
            if (this.itemSale.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.WEIGHT.value) {
                txtGrossValue.setText("R$ 0,00");
                lblAmont.setText(Messages.text_weight);
            } else {
                this.itemSale.setAmount(1);
                double productValue = this.itemSale.getProduct().getProductValue() * this.itemSale.getAmount();
                this.itemSale.setSaleTotal(productValue);
                txtGrossValue.setText("R$ " + this.df.format(productValue));
                txtAmount.setText("1");
                lblAmont.setText(Messages.text_amount);
            }
            
            txtAmount.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, Messages.not_found);
            this.clearFields();            
        }
    }
    
    private void clearFields() {
        txtProductKey.setText("");
        txtProductName.setText("");
        txtNetValue.setText("");
        txtAmount.setText("");
        txtGrossValue.setText("");            
        txtProductKey.grabFocus();
    }
    
    private void setGrossValue(String value) {
        if (this.itemSale != null && !txtProductKey.getText().isEmpty()) {
            String grossValueString = "";
            double grossValue = 0;
            
            if (this.itemSale.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
                int amount = Integer.valueOf(value);
                this.itemSale.setAmount(amount);
                grossValue = this.itemSale.getProduct().getProductValue() * amount;
                grossValueString = df.format(grossValue);
            } else {
                value = FormatMoney.verifyAmountDecimal(value);
                txtAmount.setText(value);                
                value = value.replace(",", ".");
                double amount = Double.valueOf(value);
                this.itemSale.setAmount(amount);
                
                if (this.itemSale.getProduct().getMeasureType().getId() == MeasureTypeEnum.KILOGRAM.value || this.itemSale.getProduct().getMeasureType().getId() == MeasureTypeEnum.LITER.value) {
                    grossValue = this.itemSale.getProduct().getProductValue() * amount;
                } else if (this.itemSale.getProduct().getMeasureType().getId() == MeasureTypeEnum.GRASS.value || this.itemSale.getProduct().getMeasureType().getId() == MeasureTypeEnum.MILLILITER.value) {
                    grossValue = this.itemSale.getProduct().getProductValue() * (amount * 10);
                }
            }
            
            grossValueString = this.df.format(grossValue);
            this.itemSale.setSaleTotal(grossValue);
            txtGrossValue.setText("R$ " + grossValueString);
        } else {
            JOptionPane.showMessageDialog(null, Messages.select_product);
            txtAmount.setText("");
            txtProductKey.grabFocus();
        }
    }
    
    private void setDiscountValue(String value) {
        this.changeValue = 0d;
        this.discountValue = 0d;
        
        value = FormatMoney.formatMoney(value);
        txtDiscountValue.setText(value);
        value = value.replace(",", ".");        
        this.discountValue = Double.valueOf(value);
        
        if (this.discountValue > 0d){
            this.valueWithDiscount = this.totalValue - this.discountValue;
            
            if (this.paidValue > 0d) {
                this.changeValue = this.paidValue - this.valueWithDiscount;
                txtValueChange.setText(this.df.format(this.changeValue));
            }
        } else {
            this.valueWithDiscount = this.totalValue;
        }        
        
        String discount = this.df.format(this.valueWithDiscount);
        txtTotalValue.setText(discount);
    }
    
    private void setChangeValue(String value) {
        this.changeValue = 0d;
        
        value = FormatMoney.formatMoney(value);
        txtAmountPaid.setText(value);
        value = value.replace(",", ".");
        
        if (this.discountValue > 0d) {
            this.valueWithDiscount = this.totalValue - this.discountValue;
        } else {
            this.valueWithDiscount = this.totalValue;
        }
        
        this.paidValue = Double.valueOf(value);
        this.changeValue = this.paidValue - this.valueWithDiscount;
        txtValueChange.setText(this.df.format(this.changeValue));
    }
    
    private void reserveProductList() {
        if (verifyFieldsRequired()) {
            String amountString = "";
            this.totalValue = 0d;
            
            if (this.itemSale.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
                int amount = (int)this.itemSale.getAmount();
                amountString = String.valueOf(amount);
            } else {
                amountString = String.valueOf(this.itemSale.getAmount());
                amountString = FormatMoney.verifyDecimal(amountString, this.itemSale.getProduct().getMeasureType().getCalcType().getId());
            }
            
            this.table.addRow(new Object[] {
                this.itemSale.getProduct().getId(),
                this.itemSale.getProduct().getProductName(),
                "R$ " + this.df.format(this.itemSale.getProduct().getProductValue()),
                amountString,
                "R$ " + this.df.format(this.itemSale.getSaleTotal())
            });
            
            this.listItemsSale.add(this.itemSale);            
            this.listItemsSale.stream().forEach(item -> this.totalValue += item.getSaleTotal());
            
            if (this.paidValue > 0d || this.changeValue > 0d) {
                this.changeValue -= this.itemSale.getSaleTotal();
                txtValueChange.setText(this.df.format(this.changeValue));
            }
            
            txtTotalValue.setText(this.df.format(this.totalValue));
            this.clearFields();
        }
    }
    
    private void removeItem() {
        if (tableItems.getRowCount() > 0) {
            if (tableItems.getSelectedRow() >= 0) {
                int option = JOptionPane.showConfirmDialog(null, Messages.remove_modal);
                
                if (option == OptionEnum.YES.value) {
                    int row = tableItems.getSelectedRow();
                    Object object = tableItems.getValueAt(row, 0);
                    long id = (long)object;
                    
                    Sale item = this.listItemsSale
                            .stream()
                            .filter(x -> x.getProduct().getId() == id)
                            .findAny()
                            .orElse(null);
                    
                    this.listItemsSale = this.listItemsSale
                            .stream()
                            .filter(x -> x.getProduct().getId() != id)
                            .collect(Collectors.toList());
                    
                    double valueItem = item.getProduct().getProductValue() * item.getAmount();
                    this.totalValue = this.totalValue - valueItem;
                    
                    if (this.valueWithDiscount > 0d) {
                        this.valueWithDiscount = this.valueWithDiscount - valueItem;
                        txtTotalValue.setText(this.df.format(this.valueWithDiscount));
                    } else
                        txtTotalValue.setText(this.df.format(this.totalValue));
                    
                    if (this.paidValue > 0d) {
                        this.changeValue = this.paidValue - this.valueWithDiscount;
                        txtValueChange.setText(this.df.format(this.changeValue));
                    }
                    
                    if (this.listItemsSale.size() <= 0) {
                        this.paidValue = 0d;
                        this.changeValue = 0d;
                        txtAmountPaid.setText("0,00");
                        txtValueChange.setText("0,00");
                    }
                    
                    this.setTableList();
                }
            } else {
                JOptionPane.showMessageDialog(null, Messages.table_item_no_select);
            }
        } else {
            JOptionPane.showMessageDialog(null, Messages.table_void);
        }
    }
    
    private void setTableList() {
        this.table.setNumRows(0);
        
        listItemsSale.stream().forEach(item -> {
            String amountString = "";
            
            if (item.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
            int amount = (int)item.getAmount();
            amountString = String.valueOf(amount);
            } else {
                amountString = String.valueOf(item.getAmount());
                amountString = FormatMoney.verifyDecimal(amountString, item.getProduct().getMeasureType().getCalcType().getId());
            }
            
            this.table.addRow(new Object[] {
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                "R$ " + this.df.format(item.getProduct().getProductValue()),
                amountString,
                "R$ " + this.df.format(item.getSaleTotal())
            });
        });
    }
    
    private void finallySale() {
        if (verifyFieldsRequiredSale()) {
            int option = JOptionPane.showConfirmDialog(null, Messages.confirm_sale);
            
            if (option == OptionEnum.YES.value) {
                PayType payType = new PayType();
                this.amountSale = 0;
                this.weightSale = 0;
                
                this.listItemsSale.stream().forEach(item -> {
                    if (item.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
                        this.amountSale += (int)item.getAmount();
                    } else {
                        this.weightSale += item.getAmount();
                    }
                });
                
                if (rdbMoney.isSelected()) {
                    payType.setId(PayTypeEnum.MONEY.value);
                    payType.setPayType("Dinheiro");
                } else {
                    payType.setId(PayTypeEnum.CARD.value);
                    payType.setPayType("Cartão");
                }
                
                this.transactionSale = new TransactionSale();
                this.transactionSale.setAmount(this.amountSale);
                this.transactionSale.setWeight(this.weightSale);
                this.transactionSale.setPaidValue(this.paidValue);
                this.transactionSale.setTotalValue(this.totalValue);
                this.transactionSale.setValueWithDiscount(this.valueWithDiscount);
                this.transactionSale.setDiscountValue(this.discountValue);
                this.transactionSale.setPayType(payType);
                
                ResponseModel<Boolean> response = new TransactionSaleService().saveTransactionSale(transactionSale, listItemsSale);
                
                if (response.getModel()) {
                    JOptionPane.showMessageDialog(null, Messages.success_sale);
                    this.finalizerTransactionScreen();
                } else {
                    JOptionPane.showMessageDialog(null, response.getMensage());
                }
            }
        }
    }
    
    private boolean verifyFieldsRequiredSale() {
        boolean isVerify = true;
        String message = "";
        
        if (this.table.getRowCount() <= 0) {
            message += Messages.select_product + "\n";
        }
        
        if (!rdbMoney.isSelected() && !rdbCard.isSelected()) {
            message += Messages.select_option_paid + "\n";
        }
        
        if (this.paidValue <= 0) {
            message += Messages.value_paid_client;
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isVerify = false;
        }
        
        return isVerify;
    }
    
    private boolean verifyFieldsRequired() {
        boolean isVerify = true;
        String message = "";
        
        if (txtProductKey.getText().isEmpty()) {
            message += Messages.key_register_product + "\n";
        }
        
        if (txtAmount.getText().isEmpty() || txtAmount.getText().equals("0,000")) {
            message += Messages.amount_required + "\n";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isVerify = false;
        }
        
        return isVerify;
    }
    
    private void cancelSale() {
        if (table.getRowCount() > 0) {
            int option = JOptionPane.showConfirmDialog(null, Messages.cancel_sale);

            if (option == OptionEnum.YES.value) {
                this.finalizerTransactionScreen();
            }
        }
    }
    
    private void finalizerTransactionScreen() {
        this.clearFields();
        this.clearTableSale();
        this.clearTypePaid();
        this.clearDataPaySale();
    }
    
    private void clearTableSale() {
        this.table.setNumRows(0);
    }
    
    private void clearTypePaid() {
       this.rdbMoney.setSelected(false);
       this.rdbCard.setSelected(false); 
    }
    
    private void clearDataPaySale() {
        this.totalValue = 0d;
        this.paidValue = 0d;
        this.changeValue = 0d;
        this.discountValue = 0d;
        this.valueWithDiscount = 0d;
        this.amountSale = 0;
        this.weightSale = 0d;
        
        txtDiscountValue.setText("0,00");
        txtTotalValue.setText("0,00");
        txtAmountPaid.setText("0,00");
        txtValueChange.setText("0,00");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        btnClear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblDiscountValue = new javax.swing.JLabel();
        txtDiscountValue = new javax.swing.JTextField();
        txtTotalValue = new javax.swing.JTextField();
        lblTotalValue = new javax.swing.JLabel();
        lblAmountPaid = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        lblValueChange = new javax.swing.JLabel();
        txtValueChange = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        rdbMoney = new javax.swing.JRadioButton();
        rdbCard = new javax.swing.JRadioButton();
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
        getContentPane().setLayout(new java.awt.GridBagLayout());

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

        txtProductKey.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProductKey.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProductKeyFocusLost(evt);
            }
        });
        txtProductKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductKeyKeyReleased(evt);
            }
        });

        lblProductName.setText("Produto..:");

        txtProductName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProductName.setEnabled(false);

        lblNetValue.setText("Valor Líquido..:");

        txtNetValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNetValue.setEnabled(false);

        txtAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountKeyReleased(evt);
            }
        });

        txtGrossValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGrossValue.setEnabled(false);

        lblAmont.setText("Quantidade..:");

        lblGrossValue.setText("Valor Total..:");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Add.png"))); // NOI18N
        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/View.png"))); // NOI18N
        btnSearch.setText("Pesquisar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExclude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Delete.png"))); // NOI18N
        btnExclude.setText("Excluir");
        btnExclude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcludeActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Trash.png"))); // NOI18N
        btnClear.setText("Limpar");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

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
                            .addComponent(txtGrossValue)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExclude)
                        .addGap(0, 178, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductKey)
                    .addComponent(lblProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtProductKey, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(txtProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNetValue)
                    .addComponent(lblAmont)
                    .addComponent(lblGrossValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGrossValue, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExclude)
                    .addComponent(btnAdd)
                    .addComponent(btnSearch)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 178;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(69, 284, 0, 284);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Dados Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiscountValue.setText("Valor Desconto..:");

        txtDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtDiscountValue.setText("0,00");
        txtDiscountValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountValueKeyReleased(evt);
            }
        });

        txtTotalValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtTotalValue.setText("0,00");
        txtTotalValue.setEnabled(false);

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalValue.setText("Valor Total..:");

        lblAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAmountPaid.setText("Valor Pago..:");

        txtAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtAmountPaid.setText("0,00");
        txtAmountPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountPaidKeyReleased(evt);
            }
        });

        lblValueChange.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblValueChange.setText("Valor Troco..:");

        txtValueChange.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtValueChange.setText("0,00");
        txtValueChange.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDiscountValue)
                    .addComponent(txtDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalValue)
                    .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAmountPaid)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValueChange, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValueChange))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
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
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(lblValueChange)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtValueChange, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 284);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Tipo de Pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        rdbMoney.setText("Pagamento Dinheiro");
        rdbMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMoneyActionPerformed(evt);
            }
        });

        rdbCard.setText("Pagamento Cartão");
        rdbCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbMoney)
                    .addComponent(rdbCard))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbMoney)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbCard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 33;
        gridBagConstraints.ipady = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 284, 0, 0);
        getContentPane().add(jPanel3, gridBagConstraints);

        btnFinalizeSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnFinalizeSale.setText("Finalizar Venda");
        btnFinalizeSale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFinalizeSaleMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 85, 87, 0);
        getContentPane().add(btnFinalizeSale, gridBagConstraints);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 87, 0);
        getContentPane().add(btnCancel, gridBagConstraints);

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

    private void txtProductKeyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductKeyFocusLost
        if (!txtProductKey.getText().trim().isEmpty()) {
            this.getProductByKey();
        }
    }//GEN-LAST:event_txtProductKeyFocusLost

    private void txtProductKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductKeyKeyReleased
        if (!txtProductKey.getText().trim().isEmpty()) {
            String text = txtProductKey.getText().toUpperCase();
            txtProductKey.setText(text);
        }
    }//GEN-LAST:event_txtProductKeyKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        this.initListProduct(this);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyReleased
        if (txtProductKey.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, Messages.required_product_key);
            txtAmount.setText("");
        } else if (!txtAmount.getText().isEmpty()) {        
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, Messages.verif_value_field);
                String text = txtAmount.getText().substring(0, txtAmount.getText().length() -1);
                txtAmount.setText(text);
            } else {
                this.setGrossValue(txtAmount.getText());
            }
        }
    }//GEN-LAST:event_txtAmountKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        this.reserveProductList();
    }//GEN-LAST:event_btnAddActionPerformed

    private void rdbMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMoneyActionPerformed
        if (rdbCard.isSelected()) {
            rdbCard.setSelected(false);
        }
    }//GEN-LAST:event_rdbMoneyActionPerformed

    private void rdbCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbCardActionPerformed
        if (rdbMoney.isSelected()) {
            rdbMoney.setSelected(false);
        }
    }//GEN-LAST:event_rdbCardActionPerformed

    private void txtDiscountValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountValueKeyReleased
        if (!txtDiscountValue.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, Messages.verif_value_field);
                String text = txtDiscountValue.getText().substring(0, txtDiscountValue.getText().length() -1);
                txtAmount.setText(text);
            } else {
                this.setDiscountValue(txtDiscountValue.getText());
            }
        }
    }//GEN-LAST:event_txtDiscountValueKeyReleased

    private void txtAmountPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountPaidKeyReleased
        if (!txtAmountPaid.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, Messages.verif_value_field);
                String text = txtAmountPaid.getText().substring(0, txtAmountPaid.getText().length() -1);
                txtAmountPaid.setText(text);
            } else {
                this.setChangeValue(txtAmountPaid.getText());
            }
        }
    }//GEN-LAST:event_txtAmountPaidKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.clearFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcludeActionPerformed
        this.removeItem();
    }//GEN-LAST:event_btnExcludeActionPerformed

    private void btnFinalizeSaleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFinalizeSaleMouseClicked
        this.finallySale();
    }//GEN-LAST:event_btnFinalizeSaleMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.cancelSale();
    }//GEN-LAST:event_btnCancelMouseClicked

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
    private javax.swing.JButton btnClear;
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
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JRadioButton rdbCard;
    private javax.swing.JRadioButton rdbMoney;
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
