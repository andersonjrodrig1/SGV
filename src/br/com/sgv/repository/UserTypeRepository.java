package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.UserType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class UserTypeRepository extends PersistenceRepository {    
    
    private static Session session = null;
    
    public List<UserType> getAll() {
        session = ContextFactory.initContextDb();
        Query query = session.createQuery("from UserType");
        List<UserType> usersType = query.list();
        ContextFactory.commit();
        
        return usersType;
    }
}
