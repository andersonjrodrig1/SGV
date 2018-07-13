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

        ContextFactory.commit();
    }

    public <T> T find(T t, long id) {
        session = ContextFactory.initContextDb();
        T response = (T) session.get(t.getClass(), id);
        ContextFactory.commit();

        return response;
    }

    public <T> void update(T t) {
        session = ContextFactory.initContextDb();
        session.saveOrUpdate(t);

        ContextFactory.commit();
    }

    public <T> boolean remove(T t) {
        session = ContextFactory.initContextDb();
        session.delete(t);
        ContextFactory.commit();

        return true;
    }
}
