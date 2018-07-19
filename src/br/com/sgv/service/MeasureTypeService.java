/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.MeasureType;
import br.com.sgv.repository.MeasureTypeRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 *
 * @author ander
 */
public class MeasureTypeService {
    
    private ResponseModel<List<MeasureType>> response = null;
    private MeasureTypeRepository measureTypeRepository = null;
    
    public MeasureTypeService() {
        this.measureTypeRepository = new MeasureTypeRepository();
    }
    
    public ResponseModel<List<MeasureType>> getMeasureType() {
        this.response = new ResponseModel<>();
        List<MeasureType> listMeasureType = null;
        
        try {
           listMeasureType = this.measureTypeRepository.getAll();
           response.setModel(listMeasureType);
        } catch (Exception ex) {
            System.out.printf("Eror: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
}
