package br.com.sgv.database;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.model.AcessScreen;
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
        listAcessScreen.add(new AcessScreen("Cadastro Usuário"));
        listAcessScreen.add(new AcessScreen("Consulta Usuário"));
        listAcessScreen.add(new AcessScreen("Excluir Usuário"));
        listAcessScreen.add(new AcessScreen("Cadastro Produto"));
        listAcessScreen.add(new AcessScreen("Consulta Produto"));
        listAcessScreen.add(new AcessScreen("Atualizar Produto"));
        listAcessScreen.add(new AcessScreen("Excluir Produto"));
        listAcessScreen.add(new AcessScreen("Gerar Relatório"));
        listAcessScreen.add(new AcessScreen("Consultar Relatório"));
        
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
        
        listAcess.stream().forEach(acess -> session.save(acess));
    }
}
