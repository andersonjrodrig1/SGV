/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.Product;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author ander
 */
public class ProductRepository extends PersistenceRepository {
    
    private Session session = null;
    
    public List<Product> getAll() {
        this.session = ContextFactory.initContextDb();
        Query query = this.session.createQuery("from Product");
        List<Product> products = query.list();
        ContextFactory.commit();
        
        return products;
    }
    
    public List<Product> getProductByKeyOrName(Product product) {
        String hql = "from Product where 1 = 1";
        
        if (!product.getProductKey().isEmpty()) {
            hql += " and product_key like '%" + product.getProductKey() + "%'";
        }
        
        if (!product.getProductName().isEmpty()) {
            hql += " and product_name like '%" + product.getProductName() + "%'";
        }
        
        this.session = ContextFactory.initContextDb();
        Query query = this.session.createQuery(hql);        
        List<Product> products = query.list();
        ContextFactory.commit();
        
        return products;
    }
    
    public Product getProductByKey(String productKey) {
        String hql = "from Product where product_key = :productKey";
        
        this.session = ContextFactory.initContextDb();
        Object query = this.session.createQuery(hql)
                .setParameter("productKey", productKey)
                .uniqueResult();
        ContextFactory.commit();
        
        return (Product)query;
    }
}
