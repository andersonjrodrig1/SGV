package br.com.sgv.main;

import br.com.sgv.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Anderson Junior Rodriugues
 */

public class SGV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        /***
        AcessPermission ap = new AcessPermission();
        ap.setHasAcessPermission(true);
        
        UserType ut = new UserType();
        ut.setId(1L);
        ut.setUserType("test user");
        ap.setUserType(ut);
        
        AcessType at = new AcessType();
        at.setId(1L);
        at.setScreenName("test.screen");
        ap.setAcessType(at);
        
        Transaction t = session.beginTransaction();
        session.saveOrUpdate(ap);
        t.commit();       
        session.flush();
        **/
        
        session.close();
    }
    
}
