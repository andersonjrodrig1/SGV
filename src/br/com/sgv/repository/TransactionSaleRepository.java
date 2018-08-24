package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.TransactionSale;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 * @author Anderson Junior Rodrigues
 */

public class TransactionSaleRepository {
    
    private Session session = null;
    
    public TransactionSaleRepository() {
        this.session = ContextFactory.initContextDb();
    }
    
    public TransactionSaleRepository(Session session) {
        this.session = session;
    }
    
    public void saveTransactionSale(TransactionSale transactionSale) {
        this.session.save(transactionSale);
    }
    
    public Object getMaxTransaction() {
        Criteria criteria = this.session
                .createCriteria(TransactionSale.class)
                .setProjection(Projections.max("id"));
        
        Object maxId = criteria.uniqueResult();
        
        return maxId;
    }
}
