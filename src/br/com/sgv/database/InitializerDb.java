package br.com.sgv.database;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.AcessScreen;
import br.com.sgv.model.CalcType;
import br.com.sgv.model.MeasureType;
import br.com.sgv.model.PayType;
import br.com.sgv.model.ReportType;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */
public class InitializerDb {
    
    private static Session session;
    
    private static final UserType userTypeAdmin = new UserType(1, "Administrador");
    private static final UserType userTypeSalesman = new UserType(2, "Vendedor");
    
    private static final CalcType calcUnity = new CalcType("Unidade");
    private static final CalcType calcWeight = new CalcType("Peso");
    
    private static final AcessScreen registerProduct = new AcessScreen(1, "Cadastro Produto");
    private static final AcessScreen registerMeasure = new AcessScreen(2, "Cadastro Volume");
    private static final AcessScreen registerUser = new AcessScreen(3, "Cadastro Usuário");
    private static final AcessScreen viewProduct = new AcessScreen(4, "Visualizar Produtos");
    private static final AcessScreen viewMeasure = new AcessScreen(5, "Visualizar Volumes");
    private static final AcessScreen viewUser = new AcessScreen(6, "Visualizar Usuários");
    private static final AcessScreen checkout = new AcessScreen(7, "Registrar Saída");
    private static final AcessScreen registerReport = new AcessScreen(8, "Gerar Relatório");
    private static final AcessScreen viewReport = new AcessScreen(9, "Consultar Relatório");
    private static final AcessScreen viewAbout = new AcessScreen(10, "Visualizar Sobre");
    private static final AcessScreen viewSaleDay = new AcessScreen(11, "Visualizar Venda Dia");
    private static final AcessScreen viewSaleProduct = new AcessScreen(12, "Visualizar Venda Produto");
    
    public static void initializerDatabase() {
        session = ContextFactory.initContextDb();
        
        if (session.createQuery("from AcessScreen").list().size() <= 0) {
            insertAcessScreen();
        }
        
        if (session.createQuery("from AcessScreen where screen_name = :screenName")
                .setParameter("screenName", viewSaleProduct.getScreenName())
                .uniqueResult() == null) {
            session.save(viewSaleProduct);
        }
        
        if (session.createQuery("from UserType").list().size() <= 0) {
            insertUserType();
        }
        
        if (session.createQuery("from AcessPermission").list().size() <= 0) {
            insertAcessPermission();
        }
        
        if (session.createQuery("from AcessPermission where acess_screen_id = :acessScreenId and user_type_id = :userTypeId")
                .setParameter("acessScreenId", viewSaleProduct.getId())
                .setParameter("userTypeId", userTypeAdmin.getId())
                .uniqueResult() == null) {
            session.save(new AcessPermission(userTypeAdmin, viewSaleProduct, true));
        }
        
        if (session.createQuery("from AcessPermission where acess_screen_id = :acessScreenId and user_type_id = :userTypeId")
                .setParameter("acessScreenId", viewSaleProduct.getId())
                .setParameter("userTypeId", userTypeSalesman.getId())
                .uniqueResult() == null) {
            session.save(new AcessPermission(userTypeSalesman, viewSaleProduct, true));
        }
        
        if (session.createQuery("from User").list().size() <= 0) {
            insertUser();
        }
        
        if (session.createQuery("from CalcType").list().size() <= 0) {
            insertCalcType();
        }
        
        if (session.createQuery("from MeasureType").list().size() <= 0) {
            insertMeasureType();
        }
        
        if (session.createQuery("from StatusRegister").list().size() <= 0) {
            insertStatusRegister();
        }
        
        if (session.createQuery("from PayType").list().size() <= 0) {
            insertPayType();
        }
        
        if (session.createQuery("from PayType where pay_type = :payType")
                .setParameter("payType", "Dinheiro e Cartão")
                .uniqueResult() == null) {
            PayType payType = new PayType();
            payType.setId(3);
            payType.setPayType("Dinheiro e Cartão");
            
            session.save(payType);
        }
        
        if (session.createQuery("from ReportType").list().size() <= 0) {
            insertReportType();
        }
        
        ContextFactory.commit();
    }
    
    private static void insertUserType() {
        List<UserType> listUserType = new ArrayList<>();
        listUserType.add(new UserType("Administrador"));
        listUserType.add(new UserType("Vendedor"));
        
        listUserType.stream().forEach(user -> session.save(user));
    }
    
    private static void insertAcessScreen() {
        List<AcessScreen> listAcessScreen = new ArrayList<>();
        listAcessScreen.add(registerProduct);
        listAcessScreen.add(registerMeasure);
        listAcessScreen.add(registerUser);
        listAcessScreen.add(viewProduct);
        listAcessScreen.add(viewMeasure);
        listAcessScreen.add(viewUser);
        listAcessScreen.add(checkout);
        listAcessScreen.add(registerReport);
        listAcessScreen.add(viewReport);
        listAcessScreen.add(viewAbout);
        listAcessScreen.add(viewSaleDay);
        
        listAcessScreen.stream().forEach(screen -> session.save(screen));
    }
    
    private static void insertUser() {
        User user = new User();
        user.setId(1l);
        user.setUserName("Usuário Administrador");
        user.setUserLogin("admin");
        user.setUserPassword("YWRtaW4=");
        UserType userType = new UserType();
        userType.setId(1l);
        user.setUserType(userType);
        
        session.save(user);
    }
    
    private static void insertAcessPermission() {
        List<AcessPermission> listAcess = new ArrayList<>();
        listAcess.add(new AcessPermission(userTypeAdmin, registerProduct, true));
        listAcess.add(new AcessPermission(userTypeAdmin, registerMeasure, true));
        listAcess.add(new AcessPermission(userTypeAdmin, registerUser, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewProduct, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewMeasure, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewUser, true));
        listAcess.add(new AcessPermission(userTypeAdmin, checkout, true));
        listAcess.add(new AcessPermission(userTypeAdmin, registerReport, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewReport, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewAbout, true));
        listAcess.add(new AcessPermission(userTypeAdmin, viewSaleDay, true));
        listAcess.add(new AcessPermission(userTypeSalesman, registerProduct, true));
        listAcess.add(new AcessPermission(userTypeSalesman, registerMeasure, true));
        listAcess.add(new AcessPermission(userTypeSalesman, registerUser, false));
        listAcess.add(new AcessPermission(userTypeSalesman, viewProduct, true));
        listAcess.add(new AcessPermission(userTypeSalesman, viewMeasure, true));
        listAcess.add(new AcessPermission(userTypeSalesman, viewUser, false));
        listAcess.add(new AcessPermission(userTypeSalesman, checkout, true));
        listAcess.add(new AcessPermission(userTypeSalesman, registerReport, true));
        listAcess.add(new AcessPermission(userTypeSalesman, viewReport, false));
        listAcess.add(new AcessPermission(userTypeSalesman, viewAbout, true));
        listAcess.add(new AcessPermission(userTypeSalesman, viewSaleDay, true));
        
        listAcess.stream().forEach(acess -> session.save(acess));
    }
    
    private static void insertCalcType() {
        List<CalcType> listCalcType = new ArrayList<>();
        listCalcType.add(calcUnity);
        listCalcType.add(calcWeight);
        
        listCalcType.stream().forEach(calc -> session.save(calc));
    }
    
    private static void insertMeasureType() {
        List<MeasureType> listMeasureType = new ArrayList<>();
        listMeasureType.add(new MeasureType("Unidade", null, calcUnity));
        listMeasureType.add(new MeasureType("Pedaço", null, calcUnity));
        listMeasureType.add(new MeasureType("Quilograma", "kg", calcWeight));
        listMeasureType.add(new MeasureType("Mililitro", "ml", calcWeight));
        listMeasureType.add(new MeasureType("Grama", "g", calcWeight));
        listMeasureType.add(new MeasureType("Litro", "l", calcWeight));
        listMeasureType.add(new MeasureType("Copo", null, calcUnity));
        
        listMeasureType.stream().forEach(measure -> session.save(measure));
    }
    
    private static void insertStatusRegister() {
        List<StatusRegister> listStatusRegister = new ArrayList<>();
        listStatusRegister.add(new StatusRegister("Pendente"));
        listStatusRegister.add(new StatusRegister("Totalizado"));
        
        listStatusRegister.stream().forEach(status -> session.save(status));
    }
    
    private static void insertPayType() {
        List<PayType> listPayType = new ArrayList<>();
        listPayType.add(new PayType("Dinheiro"));
        listPayType.add(new PayType("Cartão"));
        
        listPayType.stream().forEach(payType -> session.save(payType));
    }
    
    private static void insertReportType() {
        List<ReportType> listReportType = new ArrayList<>();
        listReportType.add(new ReportType("Por Venda"));
        listReportType.add(new ReportType("Por Pagamento"));
        
        listReportType.stream().forEach(report -> session.save(report));
    }
}
