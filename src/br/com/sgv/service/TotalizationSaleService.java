package br.com.sgv.service;

import br.com.sgv.database.ContextFactory;
import br.com.sgv.enumerator.PayTypeEnum;
import br.com.sgv.enumerator.ReportTypeEnum;
import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.model.ReportType;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.model.TotalizeSale;
import br.com.sgv.model.TransactionSale;
import br.com.sgv.repository.TotalizationSaleRepository;
import br.com.sgv.repository.TransactionSaleRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 * @author Anderson Junior Rodrigues
 */

public class TotalizationSaleService {
    
    private double totalValueSale = 0;
    private double totalValueMoney = 0;
    private double totalValueCard = 0;
    
    private Session session = null;
    private TotalizationSaleRepository totalizationSaleRepository = null;
    private TransactionSaleRepository transactionSaleRepository = null;
    
    public TotalizationSaleService() {
        this.session = ContextFactory.initContextDb();
        this.totalizationSaleRepository = new TotalizationSaleRepository(this.session);
        this.transactionSaleRepository = new TransactionSaleRepository(this.session);
    }
    
    public ResponseModel<Boolean> totalizerSaleDay(Date dayTotalization) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            int typeStatus = StatusRegisterEnum.PENDING.value;            
            String dateSearch = new SimpleDateFormat("yyyy-MM-dd").format(dayTotalization);
            
            List<TransactionSale> listTransactionSale = this.transactionSaleRepository.getTransactionSaleList(dateSearch, typeStatus);
            
            if (listTransactionSale != null && listTransactionSale.size() > 0){                
                listTransactionSale.stream().forEach(transaction -> {
                    this.totalValueSale += transaction.getTotalValue();
                    
                    if (transaction.getPayType().getId() == PayTypeEnum.MONEY.value){
                        this.totalValueMoney += transaction.getTotalValue();
                    } else {
                        this.totalValueCard += transaction.getTotalValue();
                    }
                });
                
                Date now = Date.from(Instant.now());
                
                List<TotalizeSale> listTotalizeSearch = this.totalizationSaleRepository.getTotalizationDayList(dateSearch);
                
                if (listTotalizeSearch != null && listTotalizeSearch.size() > 0) {
                    listTotalizeSearch.stream().forEach(totalization -> {
                        totalization.setRegisterDate(now);
                        
                        if (totalization.getReportType().getId() == ReportTypeEnum.SALE.value){
                            this.totalValueSale += totalization.getTotalValue();
                            totalization.setTotalValue(this.totalValueSale);
                        } else {
                            if (totalization.getDescrition().equals("Venda Cartão")) {
                                this.totalValueCard += totalization.getTotalValue();
                                totalization.setTotalValue(this.totalValueCard);
                            } else if (totalization.getDescrition().equals("Venda Dinheiro")) {
                                this.totalValueMoney += totalization.getTotalValue();
                                totalization.setTotalValue(this.totalValueMoney);
                            }
                        }
                    });
                    
                    this.totalizationSaleRepository.saveTotalizationSaleList(listTotalizeSearch);
                } else {
                    List<TotalizeSale> listtotalizeSale = new ArrayList<>();
                    
                    TotalizeSale totalizationBySale = new TotalizeSale();
                    totalizationBySale.setDescrition("Total Venda");
                    totalizationBySale.setRegisterDate(now);
                    totalizationBySale.setSaleDate(dayTotalization);
                    totalizationBySale.setTotalValue(this.totalValueSale);
                    totalizationBySale.setReportType(new ReportType(ReportTypeEnum.SALE.value, "Por Venda"));
                    listtotalizeSale.add(totalizationBySale);

                    TotalizeSale totalizationByMoney = new TotalizeSale();
                    totalizationByMoney.setDescrition("Venda Dinheiro");
                    totalizationByMoney.setRegisterDate(now);
                    totalizationByMoney.setSaleDate(dayTotalization);
                    totalizationByMoney.setTotalValue(this.totalValueMoney);
                    totalizationByMoney.setReportType(new ReportType(ReportTypeEnum.PAID.value, "Por Pagamento"));
                    listtotalizeSale.add(totalizationByMoney);

                    TotalizeSale totalizationByCard = new TotalizeSale();
                    totalizationByCard.setDescrition("Venda Cartão");
                    totalizationByCard.setRegisterDate(now);
                    totalizationByCard.setSaleDate(dayTotalization);
                    totalizationByCard.setTotalValue(this.totalValueCard);
                    totalizationByCard.setReportType(new ReportType(ReportTypeEnum.PAID.value, "Por Pagamento"));
                    listtotalizeSale.add(totalizationByCard);

                    this.totalizationSaleRepository.saveTotalizationSaleList(listtotalizeSale);
                }
                
                listTransactionSale.stream().forEach(transaction -> {
                    transaction.setStatusRegister(new StatusRegister(StatusRegisterEnum.TOTALIZED.value, "Totalizado"));
                });

                this.transactionSaleRepository.saveTransactionSaleList(listTransactionSale);
                
                ContextFactory.commit();
                response.setModel(true);
            } else {
                ContextFactory.rollback();
                
                response.setMensage(Messages.not_found_sale);
                response.setModel(false);
            }
        } catch(Exception ex) {
            ContextFactory.rollback();
            
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_totalization);
            response.setModel(false);
        }
        
        return response;
    }
    
    public ResponseModel<List<TotalizeSale>> getTotalizationSaleByPeriodic(Date dateInitial, Date dateFinal){
        ResponseModel<List<TotalizeSale>> response = new ResponseModel<>();
        
        try {
            String dtInitial = new SimpleDateFormat("yyyy-MM-dd").format(dateInitial);
            String dtFinal = new SimpleDateFormat("yyyy-MM-dd").format(dateFinal);
            
            List<TotalizeSale> list = new TotalizationSaleRepository().getTotalizationByPeriodic(dtInitial, dtFinal);
            response.setModel(list);            
        } catch(Exception ex) {            
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
}
