package br.com.sgv.service;

import br.com.sgv.model.Sale;
import br.com.sgv.repository.SaleRepository;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */

public class SaleService {
    
    private LogService logService = null;
    private SaleRepository saleRepository = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public SaleService() {
        this.logService = new LogService(Sale.class.getName(), "ListSaleDay");
        this.saleRepository = new SaleRepository();
    }
    
    public ResponseModel<List<Sale>> getSalesByDay(Date dateSearch) {
        ResponseModel<List<Sale>> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("montando objeto para busca", "getSalesByDay");
            String dateSearchString = this.sdf.format(dateSearch);
            List<Sale> sales = this.saleRepository.getSalesByDay(dateSearchString);
            response.setModel(sales);
            this.logService.logMessage("busca realizada", "getSalesByDay");
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getSalesByDay");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<List<Sale>> getSalesByPeriodicAndProductId(Date dateInit, Date dateFinish, int productId){
        ResponseModel<List<Sale>> response = new ResponseModel<>();
        
        try {
            String dtInit = this.sdf.format(dateInit);
            String dtFinish = this.sdf.format(dateFinish);
            this.logService.logMessage(String.format("buscar de venda por periodo: %s e %s e produto id: %d", dtInit, dtFinish, productId), "getSalesByPeriodicAndProductId");
            
            List<Sale> sales = this.saleRepository.getSaleByPeriodicAndProductId(dtInit, dtFinish, productId);
            response.setModel(sales);
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getSalesByPeriodicAndProductId");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }        
        
        return response;
    }
}
