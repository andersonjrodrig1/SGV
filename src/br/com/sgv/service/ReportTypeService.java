package br.com.sgv.service;

import br.com.sgv.model.ReportType;
import br.com.sgv.repository.ReportTypeRepository;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class ReportTypeService {
    
    private LogService logService = null;
    private ReportTypeRepository reportTypeRepository = null;
    
    public ReportTypeService() {
        this.logService = new LogService(ReportType.class.getName(), "ReportTypeService");
        this.reportTypeRepository = new ReportTypeRepository();
    }
    
    public ResponseModel<List<ReportType>> getReportTypes() {
        ResponseModel<List<ReportType>> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("busca de tipos de relatórios", "getReportTypes");
            List<ReportType> listReportType = this.reportTypeRepository.getAll();
            response.setModel(listReportType);
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "getReportTypes");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
}
