package br.com.sgv.service;

import br.com.sgv.model.CalcType;
import br.com.sgv.repository.CalcTypeRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class CalcTypeService {
    
    private ResponseModel<List<CalcType>> response = null;
    private CalcTypeRepository calcTypeRepository = null;
    
    public CalcTypeService() {
        this.calcTypeRepository = new CalcTypeRepository();
    }
    
    public ResponseModel<List<CalcType>> getCalcType() {
        this.response = new ResponseModel<>();
        List<CalcType> listCalcType = new ArrayList<>();
        
        try {
            listCalcType = this.calcTypeRepository.getAll();
            response.setModel(listCalcType);            
        } catch(Exception ex) {
            System.out.printf("Eror: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return this.response;
    }
}
