/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.repository;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.model.ReportType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author ander
 */
public class ReportTypeRepository extends PersistenceRepository {
    
    private static Session session = null;
    
    public List<ReportType> getAll() {
        session = ContextFactory.initContextDb();
        Query query = session.createQuery("from ReportType");
        List<ReportType> reports = query.list();
        ContextFactory.commit();

        return reports;
    }    
}
