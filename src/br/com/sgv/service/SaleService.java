package br.com.sgv.service;

import br.com.sgv.enumerator.ImportArchiveEnum;
import br.com.sgv.model.Sale;
import br.com.sgv.repository.SaleRepository;
import br.com.sgv.shared.ArchiveFactory;
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
            
            String dateInit = this.sdf.format(dateSearch).concat(" 00:00:00");
            String dateFinal = this.sdf.format(dateSearch).concat(" 23:59:59");
            
            List<Sale> sales = this.saleRepository.getSalesByDay(dateInit, dateFinal);
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
    
    public ResponseModel<List<Sale>> getSalesByPeriodicAndProductId(Date dateSearch, long productId){
        ResponseModel<List<Sale>> response = new ResponseModel<>();
        
        try {
            String dtSearch = this.sdf.format(dateSearch);
            String dateInit = dtSearch.concat(" 00:00:00");
            String dateFinal = dtSearch.concat(" 23:59:59");
            
            this.logService.logMessage(String.format("buscar de venda por periodo: %s e produto id: %d", dtSearch, productId), "getSalesByPeriodicAndProductId");
            
            List<Sale> sales = this.saleRepository.getSaleByPeriodicAndProductId(dateInit, dateFinal, productId);
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
    
    public ResponseModel<List<Sale>> getSalesByImportation(String path, ImportArchiveEnum archiveEnum) {
        ResponseModel<List<Sale>> response = new ResponseModel<>();
        List<Sale> sales = null;
        
        try {
            if (archiveEnum.equals(ImportArchiveEnum.TEXT)) {
                sales = new ArchiveFactory().processArchive(path);
            } else if (archiveEnum.equals(ImportArchiveEnum.EXCEL)) {
                
            }
            
            response.setModel(sales);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return response;
    }
}
