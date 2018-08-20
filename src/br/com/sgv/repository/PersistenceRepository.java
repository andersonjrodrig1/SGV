package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */
public class PersistenceRepository {
    
    private static Session session = null;
    
    public <T> void save(T t) {        
        session = ContextFactory.initContextDb();
        session.save(t);
        ContextFactory.close();
    }

    public <T> T find(T t, long id) {
        session = ContextFactory.initContextDb();
        T response = (T) session.get(t.getClass(), id);
        ContextFactory.close();

        return response;
    }

    public <T> void update(T t) {
        session = ContextFactory.initContextDb();
        session.update(t);
        ContextFactory.close();
    }

    public <T> boolean remove(T t) {
        session = ContextFactory.initContextDb();
        session.delete(t);
        ContextFactory.close();

        return true;
    }
}
