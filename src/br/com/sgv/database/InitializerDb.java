package br.com.sgv.database;

import br.com.sgv.model.AcessScreen;
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
    
    public static void initializerDatabase(){
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
        ts = ss.beginTransaction();
        
        if (ss.createQuery("from " + UserType.class.getName()).list().size() <= 0){
            insertUserType();
        }
        
        if (ss.createQuery("from " + AcessScreen.class.getName()).list().size() <= 0){
            insertAcessScreen();
        }
        
        ts.commit();
        ss.flush();
        ss.close();
    }
    
    private static void insertUserType(){
        List<UserType> listUserType = new ArrayList<>();
        listUserType.add(new UserType("Administrador"));
        listUserType.add(new UserType("Vendedor"));
        
        listUserType.stream().forEach(user -> ss.save(user));
    }
    
    private static void insertAcessScreen(){
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
}
