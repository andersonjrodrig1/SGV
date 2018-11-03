package br.com.sgv.service;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.model.Sale;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.repository.SaleRepository;
import br.com.sgv.repository.TransactionSaleRepository;
import br.com.sgv.shared.CriptoText;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class TransactionSaleService {
    
    private LogService logService = null;
    private TransactionSaleRepository transactionSaleRepository = null;
    private SaleRepository saleRepository = null;
    private Session session = null;
    
    public TransactionSaleService(boolean isTransaction) {        
        if (isTransaction){
            this.session = ContextFactory.initContextDb();
            this.saleRepository = new SaleRepository(this.session);            
            this.transactionSaleRepository = new TransactionSaleRepository(this.session);
            this.logService = new LogService(TransactionSale.class.getName(), "RegisterTransaction", "saveTransactionSale");
        } else {
            this.transactionSaleRepository = new TransactionSaleRepository();
            this.logService = new LogService(TransactionSale.class.getName(), "RegisterTransaction");
        }
    }
    
    public ResponseModel<Boolean> saveTransactionSale(TransactionSale transactionSale, List<Sale> itemsSale) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        if (transactionSale != null && itemsSale != null && itemsSale.size() > 0){
            try {
                this.logService.addLogMessage("iniciando transacao de venda");
                long transactionMaxId = 0L;
                
                Object obj = this.transactionSaleRepository.getMaxTransaction();
                Date now = Date.from(Instant.now());
                
                if (obj != null) {
                    String id = String.valueOf(obj);
                    transactionMaxId = Long.valueOf(id) + 1;
                } else {
                    transactionMaxId = 1;
                }
                
                String transactionId = "T".concat(CriptoText.convertHexadecimal(transactionMaxId));
                this.logService.addLogMessage("código de transação " + transactionId + " gerado");
                
                StatusRegister statusRegister = new StatusRegister();
                statusRegister.setId(StatusRegisterEnum.PENDING.value);
                statusRegister.setStatusRegister("Pendente");
                
                transactionSale.setTransactionId(transactionId);
                transactionSale.setStatusRegister(statusRegister);
                transactionSale.setRegisterDate(now);
                
                this.logService.addLogMessage("salvando transacao");
                this.transactionSaleRepository.saveTransactionSale(transactionSale);
                
                itemsSale.stream().forEach(item -> {
                    item.setTransactionId(transactionId);
                    item.setSaleDate(now);
                });
                
                this.logService.addLogMessage("salvando lista de produtos vendidos");
                this.saleRepository.saveSaleList(itemsSale);              
                ContextFactory.commit();
                
                response.setModel(true);
            } catch(Exception ex) {
                this.logService.addLogMessage(ex.toString());
                response.setModel(false);
                response.setMensage("Falha ao salvar os dados!");
                response.setException(ex);
            }
        } else {            
            response.setModel(false);
            response.setMensage("Falha ao registrar a venda. \nVerifique se os itens foram selecionados corretamente.");
            response.setException(null);
            response.setError(null);
        }
        
        this.logService.saveListLog();
        return response;
    } 
    
    public ResponseModel<List<TransactionSale>> getTransactionSaleByDay(Date dateSearch) {
        ResponseModel<List<TransactionSale>> response = new ResponseModel<>();
        
        try {
            String dateSearchString = new SimpleDateFormat("yyyy-MM-dd").format(dateSearch);
            this.logService.logMessage("busca de transacao por dia: " + dateSearchString, "getTransactionSaleByDay");
            
            List<TransactionSale> list = this.transactionSaleRepository.getTransactionSaleList(dateSearchString);
            response.setModel(list);
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "getTransactionSaleByDay");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
}
