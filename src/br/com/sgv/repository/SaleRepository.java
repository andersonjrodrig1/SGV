package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.Sale;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class SaleRepository {
    
    private Session session = null;
    
    public SaleRepository() {
        this.session = ContextFactory.initContextDb();
    }
    
    public SaleRepository(Session session) {
        this.session = session;
    }
    
    public void saveSaleList(List<Sale> itemsSale) {
        itemsSale.stream().forEach(item -> this.session.save(item));
    }
    
    public void updateSaleList(List<Sale> itemsSale) {
        itemsSale.stream().forEach(item -> this.session.update(item));
    }
    
    public List<Sale> getAll() {
        ContextFactory.initContextDb();
        Query query = this.session.createQuery("from Sale");
        List<Sale> sales = query.list();
        ContextFactory.commit();
        
        return sales;
    }
    
    public List<Sale> getSalesByDay(String dateSearch) {
        String hql = "from Sale where sale_date = :datesearch";
        
        ContextFactory.initContextDb();
        Query query = this.session.createQuery(hql).setParameter("datesearch", dateSearch);
        List<Sale> sales = query.list();
        ContextFactory.commit();
        
        return sales;
    }
   
    public List<Sale> getSaleByPeriodicAndProductId(String dateInit, String dateFinish, int productId) {
        String hql = "from Sale where product_id = :productid and sale_date between :dateInit and :dateFinish";
        
        ContextFactory.initContextDb();
        Query query = this.session.createQuery(hql)
                .setParameter("productid", productId)
                .setParameter("dateInit", dateInit)
                .setParameter("dateFinish", dateFinish);
        List<Sale> sales = query.list();
        ContextFactory.commit();
        
        return sales;
    }
}
