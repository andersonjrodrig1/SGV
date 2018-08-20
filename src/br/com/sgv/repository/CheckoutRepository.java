/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.Checkout;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author ander
 */
public class CheckoutRepository extends PersistenceRepository {
    
    private Session session = null;
    
    public List<Checkout> getAll() {
        this.session = ContextFactory.initContextDb();
        Query query = this.session.createQuery("from Checkout");
        List<Checkout> listCheckout = query.list();
        ContextFactory.close();
        
        return listCheckout;
    }
    
    public List<Checkout> getCheckoutToMonth(String initial, String finaly) {
        String hql = "from Checkout where checkout_date between :initial and :finally order by checkout_date desc";
        
        this.session = ContextFactory.initContextDb();
        Query query = this.session.createQuery(hql)
                .setParameter("initial", initial)
                .setParameter("finally", finaly);
        List<Checkout> listCheckout = query.list();
        ContextFactory.close();
        
        return listCheckout;
    }
}
