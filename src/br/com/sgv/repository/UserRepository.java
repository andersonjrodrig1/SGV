/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Anderson Junior Rodrigues
 */
public class UserRepository extends PersistenceRepository {
    
    private static Session session = null;
    
    public List<User> getAll() {
        session = ContextFactory.initContextDb();
        Query query = session.createQuery("from User");
        List<User> users = query.list();
        ContextFactory.commit();

        return users;
    }
    
    public User findUser(User user) {
        String hql = "from User where user_login = :login and user_password = :password";
        
        session = ContextFactory.initContextDb();        
        Object query = session.createQuery(hql)
                .setParameter("login", user.getUserName())
                .setParameter("password", user.getUserPassword())
                .uniqueResult();
        ContextFactory.commit();
        
        return (User)query;
    }
}
