package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.CalcType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */
public class CalcTypeRepository extends PersistenceRepository {
    
    private static Session session = null;
    
    public List<CalcType> getAll() {
        session = ContextFactory.initContextDb();
        Query query = session.createQuery("from CalcType");
        List<CalcType> listCalcType = query.list();
        ContextFactory.close();
        
        return listCalcType;
    }
}
