/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.ReportType;
import br.com.sgv.repository.ReportTypeRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 *
 * @author ander
 */
public class ReportTypeService {
    
    private ReportTypeRepository reportTypeRepository = null;
    
    public ReportTypeService() {
        this.reportTypeRepository = new ReportTypeRepository();
    }
    
    public ResponseModel<List<ReportType>> getReportTypes() {
        ResponseModel<List<ReportType>> response = new ResponseModel<>();
        
        try {
            List<ReportType> listReportType = this.reportTypeRepository.getAll();
            response.setModel(listReportType);
        } catch(Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
}
