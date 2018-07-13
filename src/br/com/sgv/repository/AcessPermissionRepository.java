package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.AcessPermission;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */
public class AcessPermissionRepository extends PersistenceRepository{
    
    private Session session = null;
    
    public List<AcessPermission> findByUserTypeId(int userTypeId) {
        String hql = "from AcessPermission where user_type_id = :usertype";
        
        session = ContextFactory.initContextDb();
        Query query = session.createQuery(hql).setParameter("usertype", userTypeId);        
        ContextFactory.commit();
        List<AcessPermission> listAcessPermission = query.list();
        
        return listAcessPermission;
    }
}
