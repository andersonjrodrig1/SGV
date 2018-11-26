package br.com.sgv.view;

import br.com.sgv.enumerator.AcessScreenEnum;
import br.com.sgv.enumerator.CalcTypeEnum;
import br.com.sgv.enumerator.ImportArchiveEnum;
import br.com.sgv.enumerator.MeasureTypeEnum;
import br.com.sgv.enumerator.OptionEnum;
import br.com.sgv.enumerator.PayTypeEnum;
import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.PayType;
import br.com.sgv.model.Product;
import br.com.sgv.model.Sale;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import br.com.sgv.service.AcessPermissionService;
import br.com.sgv.service.LogService;
import br.com.sgv.service.ProductService;
import br.com.sgv.service.SaleService;
import br.com.sgv.service.TransactionSaleService;
import br.com.sgv.service.UserTypeService;
import br.com.sgv.shared.FormatMoney;
import br.com.sgv.shared.ResponseModel;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author Anderson Junior Rodrigues
 */
public class SGV extends javax.swing.JFrame {

    private LogService logService = null;
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
    private double valueCard = 0;
    private double valueMoney = 0;
    
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
    private ListSaleDay listSaleDay = null;
    private RegisterTotalization registerTotalisation = null;
    private ListTotalization listTotalisation = null;
    private ListSaleProduct listSaleProduct = null;
    private RegisterTwoPayments registerTwoPayments = null;
    
    private User user = null;
    private UserType userType = null;
    private AcessPermission acessPermission = null;
    private List<AcessPermission> listAcessPermission = null;
    private ResponseModel<List<AcessPermission>> responseAcessPermission = null;
    private ResponseModel<UserType> responseUserType = null;
    
    public SGV() { }
    
    public SGV(User user) {
        this.logService = new LogService(Sale.class.getName(), "Login");
        this.user = user;
        this.getAcessUserLogin();
        
        initComponents();
        initFieldsScreen();
        initTableItems();
    }
    
    public void initScreen() {
        this.logService = new LogService(Sale.class.getName(), "Login");
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
        this.logService.logMessage("limpando dados da tabela", "initTableItems");
        this.listItemsSale = new ArrayList<>();
        
        this.table = (DefaultTableModel)tableItems.getModel();
        this.table.setRowCount(0);
    }
    
    private void getAcessUserLogin() {
        try {
            if (this.user.getUserType() != null && this.user.getUserType().getId() > 0) {
                this.logService.logMessage("verificando permissões de acesso", "getAcessUserLogin");
                this.responseUserType = new UserTypeService().findUserTypeById(this.user.getUserType().getId());
                this.userType = this.responseUserType.getModel();
                this.logService.logMessage("busca de lista de permissoes de acesso", "getAcessUserLogin");
                this.responseAcessPermission = new AcessPermissionService().findListAcessPermissonByUserType(this.user.getUserType().getId());
                this.listAcessPermission = this.responseAcessPermission.getModel();
            } else {
                this.logService.logMessage("Falha ao logar no sistema", "getAcessUserLogin");
                JOptionPane.showMessageDialog(null, "Dados inconsistentes, \nnão foi possível fazer o login.");
                return;
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "getAcessUserLogin");
            JOptionPane.showMessageDialog(null, "Falha ao logar no sistema.");
        }
    }
    
    private void exitSystem() {
        int response = JOptionPane.showConfirmDialog(null, "Deseja sair do sistema?");
        
        if (response == OptionEnum.YES.value) {
            this.logService.logMessage("saindo do sistema", "exitSystem");
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
            this.logService = null;
            
            this.dispose();
            
            this.login = new Login(this, true);
            this.login.initScreen();
        }
    }
    
    private void initRegisterUser() {
        try {
            this.screenType = AcessScreenEnum.REGISTER_USER.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initRegisterUser");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initRegisterUser");
                this.registerUser = new RegisterUser(this, true);
                this.registerUser.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initRegisterUser");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initRegisterUser");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela.");
        }
    }
    
    private void initRegisterProduct() {
        try {
            this.screenType = AcessScreenEnum.REGISTER_PRODUCT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initRegisterProduct");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initRegisterProduct");
                this.registerProduct = new RegisterProduct(this, true);
                this.registerProduct.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initRegisterProduct");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "initRegisterProduct");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initAbout() {
        try {
            this.screenType = AcessScreenEnum.VIEW_ABOUT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initAbout");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initAbout");
                this.about = new About(this, true);
                this.about.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initAbout");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initAbout");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListUser() {
        try {
            this.screenType = AcessScreenEnum.VIEW_USER.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initListUser");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initListUser");
                this.listUser = new ListUser(this, true);
                this.listUser.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initListUser");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initListUser");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initRegisterMeasure() {
        try {
            this.screenType = AcessScreenEnum.REGISTER_MEASURE.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initRegisterMeasure");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initRegisterMeasure");
                this.registerMeasure = new RegisterMeasure(this, true);
                this.registerMeasure.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initRegisterMeasure");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initRegisterMeasure");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListMeasure() {
        try {
            this.screenType = AcessScreenEnum.VIEW_MEASURE.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initListMeasure");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initListMeasure");
                this.listMeasure = new ListMeasure(this, true);
                this.listMeasure.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initListMeasure");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initListMeasure");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListProduct() {
        try {
            this.screenType = AcessScreenEnum.VIEW_PRODUCT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);
            this.logService.logMessage("verificando acesso a tela " + this.acessPermission.getAcessScreen().getScreenName(), "initListProduct");

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.logService.logMessage("permissão de acesso a tela", "initListProduct");
                this.listProduct = new ListProduct(this, true);
                this.listProduct.setFrame(this);
                this.listProduct.initScreen();
            } else {
                this.logService.logMessage("acesso negado", "initListProduct");
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initListProduct");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListProduct(SGV frame) {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initListProduct");
            this.listProduct = new ListProduct(frame, true);
            this.listProduct.setFrame(frame);
            this.listProduct.initScreen();
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "initListProduct");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initCheckout() {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initCheckout");
            this.screenType = AcessScreenEnum.CHECK_OUT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.checkout = new Checkout(this, true);
                this.checkout.initScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initCheckout");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initSaleDay() {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initSaleDay");
            this.screenType = AcessScreenEnum.VIEW_SALE_DAY.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.listSaleDay = new ListSaleDay(this, true);
                this.listSaleDay.initScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initSaleDay");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initRegisterTotalization() {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initRegisterTotalization");
            this.screenType = AcessScreenEnum.REGISTER_REPORT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.registerTotalisation = new RegisterTotalization(this, true);
                this.registerTotalisation.initScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initRegisterTotalization");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListTotalization() {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initListTotalization");
            this.screenType = AcessScreenEnum.VIEW_REPORT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.listTotalisation = new ListTotalization(this, true);
                this.listTotalisation.initScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initListTotalization");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    private void initListSaleProduct() {
        try {
            this.logService.logMessage("permissão de acesso a tela", "initListSaleProduct");
            this.screenType = AcessScreenEnum.VIEW_SALE_PRODUCT.value;
            this.acessPermission = this.verifyPermissionAcess(this.screenType);

            if (this.acessPermission != null && this.acessPermission.isHasAcessPermission()) {
                this.listSaleProduct = new ListSaleProduct(this, true);
                this.listSaleProduct.initScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não possui permissão de acesso à tela", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "initListSaleProduct");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
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
        try {
            this.logService.logMessage("inicio da busca de produto", "getProductByKey");
            String productKey = txtProductKey.getText().trim();        
            ResponseModel<Product> response = new ProductService().getProductByKey(productKey);
            Product item = response.getModel();

            this.setItemSale(item);
        } catch (Exception ex){
            this.logService.logMessage(ex.toString(), "getProductByKey");
            JOptionPane.showMessageDialog(null, "Falha ao acessar a tela");
        }
    }
    
    public void setItemSale(Product item) {
        if (item != null) {
            this.logService.logMessage("atualizando tabela de produtos", "setItemSale");
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
                lblAmont.setText("Peso..:");
            } else {
                this.itemSale.setAmount(1);
                double productValue = this.itemSale.getProduct().getProductValue() * this.itemSale.getAmount();
                this.itemSale.setSaleTotal(productValue);
                txtGrossValue.setText("R$ " + this.df.format(productValue));
                txtAmount.setText("1");
                lblAmont.setText("Quantidade..:");
            }
            
            txtAmount.grabFocus();
        } else {
            this.logService.logMessage("produto não encontrado", "setItemSale");
            JOptionPane.showMessageDialog(null, "Item não encontrado!");
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
            this.logService.logMessage("calculando valor de compra do produto", "setGrossValue");
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
            JOptionPane.showMessageDialog(null, "Favor selecione um item na lista de produtos.");
            txtAmount.setText("");
            txtProductKey.grabFocus();
        }
    }
    
    private void setDiscountValue(String value) {
        if (!rdbCard.isSelected() && !rdbMoney.isSelected() && !rdbTwoPayment.isSelected()) {
            txtDiscountValue.setText("0,00");
            JOptionPane.showMessageDialog(null, "Selecione uma forma de pagamento!");            
        } else {
            this.logService.logMessage("calculando valor de compra com desconto informado", "setDiscountValue");
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

                    if  (this.changeValue < 0) {
                        txtValueChange.setBackground(Color.PINK);
                    } else {
                        txtValueChange.setBackground(Color.WHITE);
                    }
                }
            } else {
                this.valueWithDiscount = this.totalValue;
            }        

            String discount = this.df.format(this.valueWithDiscount);
            txtTotalValue.setText(discount);
        }
    }
    
    private void setChangeValue(String value) {
        if (!rdbCard.isSelected() && !rdbMoney.isSelected() && !rdbTwoPayment.isSelected()) {
            txtAmountPaid.setText("0,00");
            JOptionPane.showMessageDialog(null, "Selecione uma forma de pagamento!");            
        } else {
            this.logService.logMessage("calculando valor total da compra", "setChangeValue");
            
            this.changeValue = 0d;
            this.totalValue = Double.valueOf(new DecimalFormat("#0.00").format(this.totalValue).replace(",", "."));
            this.discountValue = Double.valueOf(new DecimalFormat("#0.00").format(this.discountValue).replace(",", "."));

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

            if  (Math.ceil(this.changeValue) < 0) {
                txtValueChange.setBackground(Color.PINK);
            } else {
                txtValueChange.setBackground(Color.WHITE);
            }

            txtValueChange.setText(this.df.format(this.changeValue));
        }
    }
    
    private void reserveProductList() {
        if (verifyFieldsRequired()) {
            this.reserveProduct();
        }
    }
    
    private void reserveProduct() {
        this.logService.logMessage("adicionando produto na cesta de compra", "reserveProductList");
        String amountString = "";
        this.totalValue = 0d;

        if (this.itemSale.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
            int amount = (int)this.itemSale.getAmount();
            amountString = String.valueOf(amount);
        } else {
            amountString = String.valueOf(this.itemSale.getAmount());
            amountString = FormatMoney.verifyDecimal(amountString, this.itemSale.getProduct().getMeasureType().getCalcType().getId());
        }

        this.logService.logMessage("atualizando cesta de compra", "reserveProductList");

        this.table.addRow(new Object[] {
            this.itemSale.getProduct().getId(),
            this.itemSale.getProduct().getProductName(),
            "R$ " + this.df.format(this.itemSale.getProduct().getProductValue()),
            amountString,
            "R$ " + this.df.format(this.itemSale.getSaleTotal())
        });

        this.listItemsSale.add(this.itemSale);            
        this.listItemsSale.stream().forEach(item -> this.totalValue += item.getSaleTotal());

        this.logService.logMessage("atualizando valor da compra", "reserveProductList");

        if (this.paidValue > 0d || this.changeValue > 0d) {
            this.changeValue -= this.itemSale.getSaleTotal();
            txtValueChange.setText(this.df.format(this.changeValue));
        }

        txtTotalValue.setText(this.df.format(this.totalValue));
        this.clearFields();
    }
    
    private void removeItem() {
        if (tableItems.getRowCount() > 0) {
            if (tableItems.getSelectedRow() >= 0) {
                int option = JOptionPane.showConfirmDialog(null, "Deseja excluir o item selecionado?");
                
                if (option == OptionEnum.YES.value) {
                    int row = tableItems.getSelectedRow();
                    Object object = tableItems.getValueAt(row, 0);
                    long id = (long)object;
                    
                    this.logService.logMessage("verificando dados do produto", "removeItem");
                    Sale item = this.listItemsSale
                            .stream()
                            .filter(x -> x.getProduct().getId() == id)
                            .findAny()
                            .orElse(null);
                    
                    this.logService.logMessage("removendo produto da cesta de compra", "removeItem");
                    this.listItemsSale = this.listItemsSale
                            .stream()
                            .filter(x -> x.getProduct().getId() != id)
                            .collect(Collectors.toList());
                    
                    this.logService.logMessage("calculando valor da compra", "removeItem");
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
                JOptionPane.showMessageDialog(null, "Selecione um item da lista de produtos para executar está ação.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens na cesta de produtos.");
        }
    }
    
    private void setTableList() {
        this.logService.logMessage("atualizando lista de produtos na tabela", "setTableList");
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
            int option = JOptionPane.showConfirmDialog(null, "Deseja confirmar a venda?");
            
            if (option == OptionEnum.YES.value) {
                this.logService.logMessage("iniciando finalização da compra", "finallySale");
                List<TransactionSale> list = new ArrayList<>();
                PayType payType = new PayType();
                this.amountSale = 0;
                this.weightSale = 0;
                
                this.logService.logMessage("calculando valor da compra", "finallySale");
                this.listItemsSale.stream().forEach(item -> {
                    if (item.getProduct().getMeasureType().getCalcType().getId() == CalcTypeEnum.UNITY.value) {
                        this.amountSale += (int)item.getAmount();
                    } else {
                        this.weightSale += item.getAmount();
                    }
                });
                
                this.logService.logMessage("verificando tipo de pagamento selecionado", "finallySale");
                if (rdbMoney.isSelected()) {
                    payType.setId(PayTypeEnum.MONEY.value);
                    payType.setPayType("Dinheiro");
                } else if (rdbCard.isSelected()) {
                    payType.setId(PayTypeEnum.CARD.value);
                    payType.setPayType("Cartão");
                } else if (rdbTwoPayment.isSelected()) {
                    payType.setId(PayTypeEnum.TWO_PAYMENTS.value);
                    payType.setPayType("Dinheiro e Cartão");
                }
                
                if (payType.getId() == PayTypeEnum.TWO_PAYMENTS.value) {
                    this.transactionSale = new TransactionSale();
                    this.transactionSale.setTotalValue(this.valueCard);
                    this.transactionSale.setAmount(0);
                    this.transactionSale.setWeight(0);
                    this.transactionSale.setPaidValue(0);
                    this.transactionSale.setValueWithDiscount(0);
                    this.transactionSale.setDiscountValue(0);
                    this.transactionSale.setPayType(payType);
                    
                    list.add(this.transactionSale);
                    
                    this.transactionSale = new TransactionSale();
                    this.transactionSale.setTotalValue(this.valueMoney);
                    this.transactionSale.setAmount(0);
                    this.transactionSale.setWeight(0);
                    this.transactionSale.setPaidValue(0);
                    this.transactionSale.setValueWithDiscount(0);
                    this.transactionSale.setDiscountValue(0);
                    this.transactionSale.setPayType(payType);
                    
                    list.add(this.transactionSale);
                } else {
                    this.logService.logMessage("construindo objeto de transação", "finallySale");
                    this.transactionSale = new TransactionSale();
                    this.transactionSale.setAmount(this.amountSale);
                    this.transactionSale.setWeight(this.weightSale);
                    this.transactionSale.setPaidValue(this.paidValue);
                    this.transactionSale.setTotalValue(this.totalValue);
                    this.transactionSale.setValueWithDiscount(this.valueWithDiscount);
                    this.transactionSale.setDiscountValue(this.discountValue);
                    this.transactionSale.setPayType(payType);
                    
                    list.add(this.transactionSale);
                }
                
                ResponseModel<Boolean> response = new TransactionSaleService(true).saveTransactionSale(list, this.listItemsSale);
                
                if (response.getModel()) {
                    JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");
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
            message += "Favor selecione um item na lista de produtos.\n";
        }
        
        if (!rdbMoney.isSelected() && !rdbCard.isSelected() && !rdbTwoPayment.isSelected()) {
            message += "Selecione uma opção para pagamento.\n";
        }
        
        if (this.paidValue <= 0) {
            message += "Informe o valor pago pelo cliente.";
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
            message += "Código Produto obrigatório.\n";
        }
        
        if (txtAmount.getText().isEmpty() || txtAmount.getText().equals("0,000")) {
            message += "Quantidade obrigatório.\n";
        }
        
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message);
            isVerify = false;
        }
        
        return isVerify;
    }
    
    private void cancelSale() {
        if (this.table.getRowCount() > 0) {
            int option = JOptionPane.showConfirmDialog(null, "Deseja cancelar a(s) venda(s) em aberto?");

            if (option == OptionEnum.YES.value) {
                this.finalizerTransactionScreen();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens na cesta para serem cancelados.");
        }
    }
    
    protected void resetTypePayments() {
        rdbCard.setSelected(false);
        rdbMoney.setSelected(false);
        rdbTwoPayment.setSelected(false);
        txtProductKey.grabFocus();
    }
    
    protected void setValueTwoPayments(double valueCard, double valueMoney, double amountPaid, double changeValue) {
        this.valueCard = valueCard;
        this.valueMoney = valueMoney;
        this.paidValue = amountPaid;
        this.changeValue = changeValue;
        
        txtAmountPaid.setText(new DecimalFormat("#0.00").format(this.paidValue));
        txtValueChange.setText(new DecimalFormat("#0.00").format(this.changeValue));
        btnFinalizeSale.grabFocus();
    }
    
    private void selectMoney() {
        if (this.table.getRowCount() > 0) {
            if (rdbCard.isSelected()) {
                rdbCard.setSelected(false);
            }
            
            if (rdbTwoPayment.isSelected()){
                rdbTwoPayment.setSelected(false);
            }
            
            this.valueCard = 0;
            this.valueMoney = 0;
            this.paidValue = 0;
            this.changeValue = 0;

            rdbMoney.setSelected(true);
            txtAmountPaid.setText("0,00");
            txtValueChange.setText("0,00");
            txtAmountPaid.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens na cesta de produtos.");
            this.rdbMoney.setSelected(false);
            txtProductKey.grabFocus();
        }
    }
    
    private void selectCard() {
        if (this.table.getRowCount() > 0) {
            if (rdbMoney.isSelected()) {
                rdbMoney.setSelected(false);
            }
            
            if (rdbTwoPayment.isSelected()){
                rdbTwoPayment.setSelected(false);
            }
            
            this.valueCard = 0;
            this.valueMoney = 0;
            this.changeValue = 0;

            rdbCard.setSelected(true);
            txtValueChange.setText("0,00");
            String value = txtTotalValue.getText();
            this.setChangeValue(value);
            btnFinalizeSale.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens na cesta de produtos.");
            this.rdbCard.setSelected(false); 
            txtProductKey.grabFocus();
        }
    }
    
    private void selectTwoPayments() {
        if (this.table.getRowCount() > 0) {
            if (rdbCard.isSelected()) {
                rdbCard.setSelected(false);
            }
            
            if (rdbMoney.isSelected()) {
                rdbMoney.setSelected(false);
            }
            
            rdbTwoPayment.setSelected(true);
            txtAmountPaid.setText("0,00");
            txtValueChange.setText("0,00");
            txtValueChange.setBackground(Color.WHITE);
            this.registerTwoPayments = new RegisterTwoPayments(this, true);
            this.registerTwoPayments.initScreen(this.totalValue);
        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens na cesta de produtos.");
            this.rdbTwoPayment.setSelected(false); 
            txtProductKey.grabFocus();
        }
    }
    
    private void ImportExcel() {
        JFileChooser fileChooser = this.setFileChooser("Excel", "xls", "xlsx");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            String filename = fileChooser.getSelectedFile().getPath();
            
            
            
            JOptionPane.showMessageDialog(null, "Planilha importada com sucesso!.");
        }
        else if (result == JFileChooser.CANCEL_OPTION)
        {   
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado.");
        }
        else if (result == JFileChooser.ERROR_OPTION)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao importar a planinha.");  
        }
    }
    
    private void importArchive() {
        JFileChooser fileChooser = this.setFileChooser("Texto", "txt");
        int result = fileChooser.showOpenDialog(null);        
        
        if (result == JFileChooser.APPROVE_OPTION)
        {
            String pathName = fileChooser.getSelectedFile().getPath();
            ResponseModel<List<Sale>> response = new SaleService().getSalesByImportation(pathName, ImportArchiveEnum.TEXT);
            List<Sale> sales = response.getModel();
            
            this.setSalesListImport(sales);
            
            JOptionPane.showMessageDialog(null, "Arquivo importado com sucesso!.");
        }
        else if (result == JFileChooser.CANCEL_OPTION)
        {   
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado.");
        }
        else if (result == JFileChooser.ERROR_OPTION)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao importar o arquivo.");  
        }
    }
    
    private JFileChooser setFileChooser(String fileName, String... extension){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileName, extension);
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        return fileChooser;
    }
    
    private void setSalesListImport(List<Sale> sales) {
        ResponseModel<List<Product>> response = new ProductService().getProducts();
        List<Product> products = response.getModel();
        
        sales.stream().forEach(sale -> {
            this.itemSale = null;
            Product product = products
                    .stream()
                    .filter(x -> x.getProductKey().equals(sale.getProduct().getProductKey()))
                    .findFirst()
                    .orElse(null);
            
            double totalValue = sale.getAmount() * product.getProductValue();
            
            sale.setProduct(product);
            sale.setSaleTotal(totalValue);
            
            this.itemSale = sale;
            this.reserveProduct();
        });
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
       this.rdbTwoPayment.setSelected(false);
    }
    
    private void clearDataPaySale() {
        this.table.setRowCount(0);
        this.listItemsSale = new ArrayList<>();
        this.totalValue = 0d;
        this.paidValue = 0d;
        this.changeValue = 0d;
        this.discountValue = 0d;
        this.valueWithDiscount = 0d;
        this.amountSale = 0;
        this.weightSale = 0d;
        this.valueCard = 0;
        this.valueMoney = 0;
        
        txtDiscountValue.setText("0,00");
        txtTotalValue.setText("0,00");
        txtAmountPaid.setText("0,00");
        txtValueChange.setText("0,00");
        txtValueChange.setBackground(Color.WHITE);
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
        rdbMoney = new javax.swing.JRadioButton();
        rdbCard = new javax.swing.JRadioButton();
        lblDiscountValue = new javax.swing.JLabel();
        txtDiscountValue = new javax.swing.JTextField();
        lblTotalValue = new javax.swing.JLabel();
        txtTotalValue = new javax.swing.JTextField();
        lblAmountPaid = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        lblValueChange = new javax.swing.JLabel();
        txtValueChange = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnFinalizeSale = new javax.swing.JButton();
        rdbTwoPayment = new javax.swing.JRadioButton();
        btnImportExcel = new javax.swing.JButton();
        btnImportArchive = new javax.swing.JButton();
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
        mnSaleDay = new javax.swing.JMenu();
        mnRegisterTotalization = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnListTotalization = new javax.swing.JMenuItem();
        mnListSaleDay = new javax.swing.JMenuItem();
        mnListSaleProduct = new javax.swing.JMenuItem();
        nmAbout = new javax.swing.JMenu();
        nmExit = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Gerenciamento de Vendas");
        setBackground(new java.awt.Color(153, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Tela de Vendas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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
            tableItems.getColumnModel().getColumn(1).setMinWidth(320);
            tableItems.getColumnModel().getColumn(1).setPreferredWidth(320);
            tableItems.getColumnModel().getColumn(1).setMaxWidth(320);
            tableItems.getColumnModel().getColumn(2).setMinWidth(150);
            tableItems.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableItems.getColumnModel().getColumn(2).setMaxWidth(150);
            tableItems.getColumnModel().getColumn(3).setMinWidth(100);
            tableItems.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableItems.getColumnModel().getColumn(3).setMaxWidth(100);
            tableItems.getColumnModel().getColumn(4).setMinWidth(200);
            tableItems.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableItems.getColumnModel().getColumn(4).setMaxWidth(200);
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

        txtProductName.setEditable(false);
        txtProductName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblNetValue.setText("Valor Líquido..:");

        txtNetValue.setEditable(false);
        txtNetValue.setBackground(java.awt.SystemColor.text);
        txtNetValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountKeyReleased(evt);
            }
        });

        txtGrossValue.setEditable(false);
        txtGrossValue.setBackground(java.awt.SystemColor.text);
        txtGrossValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

        lblDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiscountValue.setText("Valor Desconto..:");

        txtDiscountValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtDiscountValue.setText("0,00");
        txtDiscountValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountValueKeyReleased(evt);
            }
        });

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalValue.setText("Valor Total..:");

        txtTotalValue.setEditable(false);
        txtTotalValue.setBackground(java.awt.SystemColor.text);
        txtTotalValue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtTotalValue.setText("0,00");

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

        txtValueChange.setEditable(false);
        txtValueChange.setBackground(java.awt.SystemColor.text);
        txtValueChange.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtValueChange.setText("0,00");

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Erase.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        btnFinalizeSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Apply.png"))); // NOI18N
        btnFinalizeSale.setText("Finalizar Venda");
        btnFinalizeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizeSaleActionPerformed(evt);
            }
        });

        rdbTwoPayment.setText("Dinheiro e Cartão");
        rdbTwoPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTwoPaymentActionPerformed(evt);
            }
        });

        btnImportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Table.png"))); // NOI18N
        btnImportExcel.setText("Importar Excel");
        btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportExcelActionPerformed(evt);
            }
        });

        btnImportArchive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgv/images/png/Upload.png"))); // NOI18N
        btnImportArchive.setText("Importar Arquivo");
        btnImportArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportArchiveActionPerformed(evt);
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
                        .addGap(25, 25, 25)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExclude)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImportExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImportArchive)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbCard)
                    .addComponent(rdbMoney)
                    .addComponent(rdbTwoPayment))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnFinalizeSale)
                        .addGap(10, 10, 10)
                        .addComponent(btnCancel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDiscountValue)
                            .addComponent(txtDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalValue)
                            .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAmountPaid)
                            .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblValueChange)
                            .addComponent(txtValueChange, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(67, Short.MAX_VALUE))
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
                    .addComponent(btnSearch)
                    .addComponent(btnClear)
                    .addComponent(btnAdd)
                    .addComponent(btnImportExcel)
                    .addComponent(btnImportArchive))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDiscountValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDiscountValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(rdbMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbCard)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbTwoPayment))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblTotalValue)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblAmountPaid)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblValueChange)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValueChange, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFinalizeSale)
                            .addComponent(btnCancel))))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.ipady = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(62, 282, 85, 280);
        getContentPane().add(jPanel1, gridBagConstraints);

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

        mnSaleDay.setText("Relatório");

        mnRegisterTotalization.setText("Gerar Relatório");
        mnRegisterTotalization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRegisterTotalizationActionPerformed(evt);
            }
        });
        mnSaleDay.add(mnRegisterTotalization);
        mnSaleDay.add(jSeparator3);

        mnListTotalization.setText("Consultar Relatórios");
        mnListTotalization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListTotalizationActionPerformed(evt);
            }
        });
        mnSaleDay.add(mnListTotalization);

        mnListSaleDay.setText("Consultar Venda por Dia");
        mnListSaleDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListSaleDayActionPerformed(evt);
            }
        });
        mnSaleDay.add(mnListSaleDay);

        mnListSaleProduct.setText("Consultar Venda por Produto");
        mnListSaleProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListSaleProductActionPerformed(evt);
            }
        });
        mnSaleDay.add(mnListSaleProduct);

        jMenuBar1.add(mnSaleDay);

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
            JOptionPane.showMessageDialog(null, "Necessário informar código do produto.");
            txtAmount.setText("");
        } else if (!txtAmount.getText().isEmpty()) {        
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, "Valor informado inválido.\nDigite apenas números.");
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

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.clearFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcludeActionPerformed
        this.removeItem();
    }//GEN-LAST:event_btnExcludeActionPerformed

    private void mnListSaleDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListSaleDayActionPerformed
        this.initSaleDay();
    }//GEN-LAST:event_mnListSaleDayActionPerformed

    private void mnRegisterTotalizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRegisterTotalizationActionPerformed
        this.initRegisterTotalization();
    }//GEN-LAST:event_mnRegisterTotalizationActionPerformed

    private void mnListTotalizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListTotalizationActionPerformed
        this.initListTotalization();
    }//GEN-LAST:event_mnListTotalizationActionPerformed

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.cancelSale();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnFinalizeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizeSaleActionPerformed
        this.finallySale();
    }//GEN-LAST:event_btnFinalizeSaleActionPerformed

    private void txtAmountPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountPaidKeyReleased
        if (!txtAmountPaid.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, "Valor informado inválido.\nDigite apenas números.");
                String text = txtAmountPaid.getText().substring(0, txtAmountPaid.getText().length() -1);
                txtAmountPaid.setText(text);
            } else {
                this.setChangeValue(txtAmountPaid.getText());
            }
        }
    }//GEN-LAST:event_txtAmountPaidKeyReleased

    private void txtDiscountValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountValueKeyReleased
        if (!txtDiscountValue.getText().isEmpty()) {
            if (!FormatMoney.verifyCodeChar(evt)) {
                JOptionPane.showMessageDialog(null, "Valor informado inválido.\nDigite apenas números.");
                String text = txtDiscountValue.getText().substring(0, txtDiscountValue.getText().length() -1);
                txtAmount.setText(text);
            } else {
                this.setDiscountValue(txtDiscountValue.getText());
            }
        }
    }//GEN-LAST:event_txtDiscountValueKeyReleased

    private void rdbCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbCardActionPerformed
        this.selectCard();
    }//GEN-LAST:event_rdbCardActionPerformed

    private void rdbMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMoneyActionPerformed
        this.selectMoney();
    }//GEN-LAST:event_rdbMoneyActionPerformed

    private void mnListSaleProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListSaleProductActionPerformed
        this.initListSaleProduct();
    }//GEN-LAST:event_mnListSaleProductActionPerformed

    private void rdbTwoPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTwoPaymentActionPerformed
        this.selectTwoPayments();
    }//GEN-LAST:event_rdbTwoPaymentActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        this.ImportExcel();
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnImportArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportArchiveActionPerformed
        this.importArchive();
    }//GEN-LAST:event_btnImportArchiveActionPerformed

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
    private javax.swing.JButton btnImportArchive;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lblAmont;
    private javax.swing.JLabel lblAmountPaid;
    private javax.swing.JLabel lblDiscountValue;
    private javax.swing.JLabel lblGrossValue;
    private javax.swing.JLabel lblNetValue;
    private javax.swing.JLabel lblProductKey;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblValueChange;
    private javax.swing.JMenuItem mnListSaleDay;
    private javax.swing.JMenuItem mnListSaleProduct;
    private javax.swing.JMenuItem mnListTotalization;
    private javax.swing.JMenuItem mnMeasure;
    private javax.swing.JMenuItem mnRegisterMeasure;
    private javax.swing.JMenuItem mnRegisterTotalization;
    private javax.swing.JMenu mnSaleDay;
    private javax.swing.JMenu nmAbout;
    private javax.swing.JMenu nmExit;
    private javax.swing.JMenuItem nmListProduct;
    private javax.swing.JMenuItem nmListUser;
    private javax.swing.JMenu nmRegister;
    private javax.swing.JMenuItem nmRegisterProduct;
    private javax.swing.JMenuItem nmRegisterUser;
    private javax.swing.JRadioButton rdbCard;
    private javax.swing.JRadioButton rdbMoney;
    private javax.swing.JRadioButton rdbTwoPayment;
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
