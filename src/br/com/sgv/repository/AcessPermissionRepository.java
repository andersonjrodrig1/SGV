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
    
    public List<AcessPermission> findByUserTypeId(long userTypeId) {
        String hql = "from AcessPermission where user_type_id = :usertypeid";
        
        session = ContextFactory.initContextDb();
        Query query = session.createQuery(hql).setParameter("usertypeid", userTypeId);        
        List<AcessPermission> listAcessPermission = query.list();
        ContextFactory.close();
        
        return listAcessPermission;
    }
}
