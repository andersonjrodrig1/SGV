package br.com.sgv.repository;

import br.com.sgv.database.HibernateDb;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Anderson Junior Rodrigues
 */
public class PersistenceRepository {
    
    private static SessionFactory sf = null;
    private static Session ss = null;
    private static Transaction ts = null;
    
    public PersistenceRepository() {
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
    }

    public <T> void save(T t) {        
        ts = ss.beginTransaction();

        ss.save(t);

        ts.commit();
        ss.flush();
        ss.close();
    }

    public <T> T find(T t, long id) {
        ts = ss.beginTransaction();

        T response = (T) ss.get(t.getClass(), id);

        ts.commit();
        ss.flush();
        ss.close();

        return response;
    }

    public <T> void update(T t) {
        ts = ss.beginTransaction();

        ss.saveOrUpdate(t);

        ts.commit();
        ss.flush();
        ss.close();
    }

    public <T> boolean remove(T t) {
        ts = ss.beginTransaction();

        ss.delete(t);

        ts.commit();
        ss.flush();
        ss.close();

        return true;
    }
}
