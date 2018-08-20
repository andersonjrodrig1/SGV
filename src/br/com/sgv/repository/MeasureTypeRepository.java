package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.MeasureType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */
public class MeasureTypeRepository extends PersistenceRepository {
    
    private Session session = null;
    
    public List<MeasureType> getAll() {
        session = ContextFactory.initContextDb();
        Query query = session.createQuery("from MeasureType");
        List<MeasureType> listMeasureType = query.list();
        ContextFactory.close();
        
        return listMeasureType;
    }
}
