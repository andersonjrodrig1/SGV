package br.com.sgv.controller;

import br.com.sgv.database.HibernateDb;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Anderson Junior Rodrigues
 */
public class PersistenceController {
    
    private static SessionFactory sf = null;
    private static Session ss = null;
    private static Transaction ts = null;
    
    private static void initFactoryContext() {
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
    }

    public <T> boolean save(T t) {
        try {
            initFactoryContext();            
            ts = ss.beginTransaction();
            
            ss.save(t);
            
            ts.commit();
            ss.flush();
            ss.close();
            
            return true;
        } catch(Exception e) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, "Error: " + e.getMessage(), e);
            throw e;
        }
    }

    public <T> T find(T t, long id) {
        try {
            initFactoryContext();
            ts = ss.beginTransaction();
            
            T response = (T) ss.get(t.getClass(), id);
            
            ts.commit();
            ss.flush();
            ss.close();
            
            return response;
        } catch(Exception e) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, "Error: " + e.getMessage(), e);
            throw e;
        }
    }

    public <T> boolean update(T t) {
       try {
            initFactoryContext();            
            ts = ss.beginTransaction();
            
            ss.saveOrUpdate(t);
            
            ts.commit();
            ss.flush();
            ss.close();
            
            return true;
       } catch(Exception e) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, "Error: " + e.getMessage(), e);
            throw e;
       }
    }

    public <T> boolean remove(T t) {
        try {
            initFactoryContext();            
            ts = ss.beginTransaction();
            
            ss.delete(t);
            
            ts.commit();
            ss.flush();
            ss.close();
            
            return true;
        } catch(Exception e) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, "Error: " + e.getMessage(), e);
            throw e;
        }
    }
}
