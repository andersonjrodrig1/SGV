package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.Sale;
import java.util.List;
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
}
