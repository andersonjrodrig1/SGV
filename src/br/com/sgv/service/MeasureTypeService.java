package br.com.sgv.service;

import br.com.sgv.model.CalcType;
import br.com.sgv.model.MeasureType;
import br.com.sgv.repository.MeasureTypeRepository;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class MeasureTypeService {
    
    private MeasureType measureType = null;
    private ResponseModel<List<MeasureType>> response = null;
    private MeasureTypeRepository measureTypeRepository = null;
    private LogService logService = null;
    
    public MeasureTypeService() {
        this.logService = new LogService("MeasureTypeService", "ListMeasure");
        this.measureTypeRepository = new MeasureTypeRepository();
    }
    
    public ResponseModel<List<MeasureType>> getMeasureType() {
        this.logService.logMessage("montagem de dados para busca", "getMeasureType");
        this.response = new ResponseModel<>();
        List<MeasureType> listMeasureType = null;
        
        try {
           listMeasureType = this.measureTypeRepository.getAll();
           response.setModel(listMeasureType);
           this.logService.logMessage("encontrado" + listMeasureType.size() + " volumes", "getMeasureType");
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getMeasureType");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> saveMeasureType(String name, String initials, CalcType calcType) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("montando dados para persistir na base", "saveMeasureType");
            this.measureType = new MeasureType();
            this.measureType.setMeasureType(name);
            this.measureType.setInitials(initials);
            this.measureType.setCalcType(calcType);
            
            this.measureTypeRepository.save(this.measureType);
            this.logService.logMessage("dados salvos na base", "saveMeasureType");
            response.setModel(true);
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "saveMeasureType");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao salvar os dados!");
            response.setModel(false);
        }
        
        return response;
    }
}
