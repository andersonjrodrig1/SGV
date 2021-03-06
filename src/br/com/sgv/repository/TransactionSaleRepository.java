package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.TransactionSale;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 * @author Anderson Junior Rodrigues
 */

public class TransactionSaleRepository {
    
    private Session session = null;
    
    public TransactionSaleRepository() { }
    
    public TransactionSaleRepository(Session session) {
        this.session = session;
    }
    
    public void saveTransactionSale(TransactionSale transactionSale) {
        this.session.save(transactionSale);
    }
    
    public void saveTransactionSaleList(List<TransactionSale> listtransactionSale) {
        listtransactionSale.stream().forEach(transaction -> this.session.save(transaction));
    }
    
    public Object getMaxTransaction() {
        Criteria criteria = this.session
                .createCriteria(TransactionSale.class)
                .setProjection(Projections.max("id"));
        
        Object maxId = criteria.uniqueResult();
        
        return maxId;
    }
    
    public List<TransactionSale> getTransactionSaleList(String dateInit, String dateFinal, int statusRegisterType) {
        String hql = "from TransactionSale where register_date between :dateInit and :dateFinal and status_register_id = :status";
        
        Query query = this.session.createQuery(hql)
                .setParameter("dateInit", dateInit)
                .setParameter("dateFinal", dateFinal)
                .setParameter("status", statusRegisterType);
        List<TransactionSale> listTransactionSale = query.list();
        
        return listTransactionSale;
    }
    
    public List<TransactionSale> getTransactionSaleList(String dateInit, String dateFinal) {
        String hql = "from TransactionSale where register_date between :dateInit and :dateFinal";
        
        this.session = ContextFactory.initContextDb();
        Query query = this.session.createQuery(hql)
                .setParameter("dateInit", dateInit)
                .setParameter("dateFinal", dateFinal);
        List<TransactionSale> listTransactionSale = query.list();
        ContextFactory.commit();
        
        return listTransactionSale;
    }
}