package br.com.sgv.service;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.model.Sale;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.repository.SaleRepository;
import br.com.sgv.repository.TransactionSaleRepository;
import br.com.sgv.shared.CriptoText;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class TransactionSaleService {
    
    private TransactionSaleRepository transactionSaleRepository = null;
    private SaleRepository saleRepository = null;
    private Session session = null;
    
    public TransactionSaleService() {
        this.session = ContextFactory.initContextDb();
        this.transactionSaleRepository = new TransactionSaleRepository(this.session);
        this.saleRepository = new SaleRepository(this.session);
    }
    
    public ResponseModel<Boolean> saveTransactionSale(TransactionSale transactionSale, List<Sale> itemsSale) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        if (transactionSale != null && itemsSale != null && itemsSale.size() > 0){
            try {
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
                
                StatusRegister statusRegister = new StatusRegister();
                statusRegister.setId(StatusRegisterEnum.PENDING.value);
                statusRegister.setStatusRegister("Pendente");
                
                transactionSale.setTransactionId(transactionId);
                transactionSale.setStatusRegister(statusRegister);
                transactionSale.setRegisterDate(now);
                
                this.transactionSaleRepository.saveTransactionSale(transactionSale);
                
                itemsSale.stream().forEach(item -> {
                    item.setTransactionId(transactionId);
                    item.setSaleDate(now);
                });
                
                this.saleRepository.saveSaleList(itemsSale);
                
                ContextFactory.commit();
                
                response.setModel(true);
            } catch(Exception ex) {
                ContextFactory.rollback();
                
                response.setModel(false);
                response.setMensage(Messages.fail_save);
                response.setException(ex);
            }
        } else {            
            ContextFactory.rollback();
            
            response.setModel(false);
            response.setMensage(Messages.selected_item);
            response.setException(null);
            response.setError(null);
        }
        
        return response;
    } 
}
