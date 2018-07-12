/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.repository;

import br.com.sgv.database.HibernateDb;
import br.com.sgv.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Anderson Junior Rodrigues
 */
public class UserRepository extends PersistenceRepository {
    
    private static SessionFactory sf = null;
    private static Session ss = null;
    private static Transaction ts = null;
    
    public UserRepository() {
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
    }
    
    public List<User> getAll() {
        ts = ss.beginTransaction();

        Query query = ss.createQuery("from " + User.class.getName());
        List<User> users = query.list();

        ss.flush();
        ss.close();

        return users;
    }
    
    public User findUser(User user) {
        String hql = "from User u where u.user_name = :user_name" +
                " and u.user_password = :user_password";
        
        ts = ss.beginTransaction();
        
        Query query = ss.createQuery(hql)
                .setParameter("user_name", user.getUserName())
                .setParameter("user_password", user.getUserPassword());
        
        ss.flush();
        ss.close();
        
        return (User)query;
    }
}
