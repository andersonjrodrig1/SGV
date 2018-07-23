package br.com.sgv.service;

import br.com.sgv.model.CalcType;
import br.com.sgv.model.MeasureType;
import br.com.sgv.repository.MeasureTypeRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class MeasureTypeService {
    
    private MeasureType measureType = null;
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
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> saveMeasureType(String name, String initials, CalcType calcType) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.measureType = new MeasureType();
            this.measureType.setMeasureType(name);
            this.measureType.setInitials(initials);
            this.measureType.setCalcType(calcType);
            
            this.measureTypeRepository.save(this.measureType);
            response.setModel(true);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_save);
            response.setModel(false);
        }
        
        return response;
    }
}
