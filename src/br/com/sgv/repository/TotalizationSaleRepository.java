package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.TotalizeSale;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class TotalizationSaleRepository {
    
    private Session session = null;
    
    public TotalizationSaleRepository() {
        this.session = ContextFactory.initContextDb();
    }
    
    public TotalizationSaleRepository(Session session) {
        this.session = session;
    }
    
    public void saveTotalizationSale(TotalizationSaleRepository totalization) {
        this.session.save(totalization);
    }
    
    public void saveTotalizationSaleList(List<TotalizeSale> listTotalization) {
        listTotalization.stream().forEach(totalization -> this.session.save(totalization));
    }
    
    public void updateTotalizationSaleList(List<TotalizeSale> listTotalization) {
        listTotalization.stream().forEach(totalization -> this.session.update(totalization));
    }
    
    public List<TotalizeSale> getTotalizationDayList(String dateTotalization) {
        String hql = "from TotalizeSale where register_date = :date";
        
        Query query = this.session.createQuery(hql)
                .setParameter("date", dateTotalization);
        List<TotalizeSale> listTotalizeSale = query.list();
        
        return listTotalizeSale;
    }
    
    public TotalizeSale getTotalizationDay(String dateTotalization, int reportType) {
        String hql = "from TotalizeSale where register_date = :date and report_type_id = :type";
        
        Query query = this.session.createQuery(hql)
                .setParameter("date", dateTotalization)
                .setParameter("type", reportType);
        TotalizeSale totalizeSale = (TotalizeSale)query.uniqueResult();
        ContextFactory.commit();
        
        return totalizeSale;
    }
}
