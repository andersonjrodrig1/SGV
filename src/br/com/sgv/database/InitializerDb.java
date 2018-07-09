package br.com.sgv.database;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.AcessScreen;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Anderson Junior Rodrigues
 */
public class InitializerDb {
    
    private static SessionFactory sf;
    private static Session ss;
    private static Transaction ts;
    
    public static void initializerDatabase() {
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
        ts = ss.beginTransaction();
        
        if (ss.createQuery("from " + AcessScreen.class.getName()).list().size() <= 0) {
            insertAcessScreen();
        }
        
        if (ss.createQuery("from " + UserType.class.getName()).list().size() <= 0) {
            insertUserType();
        }
        
        if (ss.createQuery("from " + AcessPermission.class.getName()).list().size() <= 0) {
            insertAcessPermission();
        }
        
        if (ss.createQuery("from " + User.class.getName()).list().size() <= 0) {
            insertUser();
        }
        
        ts.commit();
        ss.flush();
        ss.close();
    }
    
    private static void insertUserType() {
        List<UserType> listUserType = new ArrayList<>();
        listUserType.add(new UserType("Administrador"));
        listUserType.add(new UserType("Vendedor"));
        
        listUserType.stream().forEach(user -> ss.save(user));
    }
    
    private static void insertAcessScreen() {
        List<AcessScreen> listAcessScreen = new ArrayList<>();
        listAcessScreen.add(new AcessScreen("Cadastro Usuário"));
        listAcessScreen.add(new AcessScreen("Consulta Usuário"));
        listAcessScreen.add(new AcessScreen("Excluir Usuário"));
        listAcessScreen.add(new AcessScreen("Cadastro Produto"));
        listAcessScreen.add(new AcessScreen("Consulta Produto"));
        listAcessScreen.add(new AcessScreen("Atualizar Produto"));
        listAcessScreen.add(new AcessScreen("Excluir Produto"));
        listAcessScreen.add(new AcessScreen("Gerar Relatório"));
        listAcessScreen.add(new AcessScreen("Consultar Relatório"));
        
        listAcessScreen.stream().forEach(screen -> ss.save(screen));
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
        
        ss.save(user);
    }
    
    private static void insertAcessPermission() {
        List<AcessPermission> listAcess = new ArrayList<>();
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(1, "Cadastro Usuário"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(2, "Consulta Usuário"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(3, "Excluir Usuário"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(4, "Cadastro Produto"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(5, "Consulta Produto"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(6, "Atualizar Produto"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(7, "Excluir Produto"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(8, "Gerar Relatório"), true));
        listAcess.add(new AcessPermission(new UserType(1, "Administrador"), new AcessScreen(9, "Consultar Relatório"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(1, "Cadastro Usuário"), false));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(2, "Consulta Usuário"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(3, "Excluir Usuário"), false));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(4, "Cadastro Produto"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(5, "Consulta Produto"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(6, "Atualizar Produto"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(7, "Excluir Produto"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(8, "Gerar Relatório"), true));
        listAcess.add(new AcessPermission(new UserType(2, "Vendedor"), new AcessScreen(9, "Consultar Relatório"), false));
        
        listAcess.stream().forEach(acess -> ss.save(acess));
    }
}
