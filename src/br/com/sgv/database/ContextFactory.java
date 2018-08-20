/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author ander
 */
public class ContextFactory {
    
    private static SessionFactory sf = null;
    private static Session ss = null;
    private static Transaction ts = null;
    
    public static Session initContextDb() {
        sf = HibernateDb.getSessionFactory();
        ss = sf.openSession();
        ts = ss.beginTransaction();
        
        return ss;
    }
    
    public static void beginTransaction() {
        ts.begin();
    }
    
    public static void commit() {
        ts.commit();
        close();
    }
    
    public static void rollback() {
        ts.rollback();
        close();
    }
    
    public static void close() {
        ss.flush();
        ss.close();
    }
}
