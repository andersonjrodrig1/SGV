package br.com.sgv.service;

import br.com.sgv.model.Sale;
import br.com.sgv.repository.SaleRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */

public class SaleService {
    
    private SaleRepository saleRepository = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public SaleService() {
        this.saleRepository = new SaleRepository();
    }
    
    public ResponseModel<List<Sale>> getSalesByDay(Date dateSearch) {
        ResponseModel<List<Sale>> response = new ResponseModel<>();
        
        try {
            String dateSearchString = this.sdf.format(dateSearch);
            List<Sale> sales = this.saleRepository.getSalesByDay(dateSearchString);
            response.setModel(sales);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    } 
}
