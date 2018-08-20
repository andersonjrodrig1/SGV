package br.com.sgv.database;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.AcessScreen;
import br.com.sgv.model.CalcType;
import br.com.sgv.model.MeasureType;
import br.com.sgv.model.PayType;
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
    
    public static void initializerDatabase() {
        session = ContextFactory.initContextDb();
        
        if (session.createQuery("from AcessScreen").list().size() <= 0) {
            insertAcessScreen();
        }
        
        if (session.createQuery("from UserType").list().size() <= 0) {
            insertUserType();
        }
        
        if (session.createQuery("from AcessPermission").list().size() <= 0) {
            insertAcessPermission();
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
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), registerProduct, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), registerMeasure, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), registerUser, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), viewProduct, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), viewMeasure, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), viewUser, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), checkout, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), registerReport, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), viewReport, true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), viewAbout, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), registerProduct, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), registerMeasure, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), registerUser, false));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), viewProduct, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), viewMeasure, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), viewUser, false));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), checkout, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), registerReport, true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), viewReport, false));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), viewAbout, true));
        
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
        listMeasureType.add(new MeasureType("Miligrama", "ml", calcWeight));
        listMeasureType.add(new MeasureType("Grama", "g", calcWeight));        
        
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
}
