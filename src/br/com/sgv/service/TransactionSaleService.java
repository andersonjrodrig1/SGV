package br.com.sgv.service;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.enumerator.PayTypeEnum;
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
    private String transactionId = null;
    
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
    
    public ResponseModel<Boolean> saveTransactionSale(List<TransactionSale> transactionSale, List<Sale> itemsSale) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        if (transactionSale != null && transactionSale.size() > 0 && itemsSale != null && itemsSale.size() > 0){
            try {
                this.logService.addLogMessage("iniciando transacao de venda");
                Date now = Date.from(Instant.now());
                long transactionMaxId = 0L;
                
                if (transactionSale.get(0).getPayType().getId() == PayTypeEnum.TWO_PAYMENTS.value) {
                    transactionSale.get(0).setTransactionId("C");
                    transactionSale.get(1).setTransactionId("D");
                } else {
                    Object obj = this.transactionSaleRepository.getMaxTransaction();

                    if (obj != null) {
                        String id = String.valueOf(obj);
                        transactionMaxId = Long.valueOf(id) + 1;
                    } else {
                        transactionMaxId = 1;
                    }
                    
                    this.transactionId = "T".concat(CriptoText.convertHexadecimal(transactionMaxId));
                    this.logService.addLogMessage("código de transação " + transactionId + " gerado");
                }
                
                StatusRegister statusRegister = new StatusRegister();
                statusRegister.setId(StatusRegisterEnum.PENDING.value);
                statusRegister.setStatusRegister("Pendente");
                
                transactionSale.stream().forEach(transaction -> {
                    if (transaction.getPayType().getId() != PayTypeEnum.TWO_PAYMENTS.value){
                        transaction.setTransactionId(this.transactionId);
                    }
                    
                    transaction.setStatusRegister(statusRegister);
                    transaction.setRegisterDate(now);

                    this.logService.addLogMessage("salvando transacao");
                    this.transactionSaleRepository.saveTransactionSale(transaction);
                });
                
                if (transactionSale.get(0).getPayType().getId() == PayTypeEnum.TWO_PAYMENTS.value) {
                    this.transactionId = "CD";
                }
                
                itemsSale.stream().forEach(item -> {
                    item.setTransactionId(this.transactionId);
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
            String dateInit = dateSearchString.concat(" 00:00:00");
            String dateFinish = dateSearchString.concat(" 23:59:59");
            
            this.logService.logMessage("busca de transacao por dia: " + dateSearchString, "getTransactionSaleByDay");
            
            List<TransactionSale> list = this.transactionSaleRepository.getTransactionSaleList(dateInit, dateFinish);
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
