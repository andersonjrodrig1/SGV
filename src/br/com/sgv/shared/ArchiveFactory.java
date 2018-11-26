package br.com.sgv.shared;

import br.com.sgv.model.Product;
import br.com.sgv.model.Sale;
import br.com.sgv.service.LogService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Júnior Rodrigues
 */
public class ArchiveFactory {
    
    private LogService logService = null;
    
    public ArchiveFactory() {
        this.logService = new LogService(Sale.class.getName(), "SGV");
    }
    
    public List<Sale> processArchive(String path) {
        this.logService.logMessage("iniciando processo de importação", "processArchive");
        List<Sale> list = new ArrayList<>();
        FileReader file = null;
        Sale sale = null;
        Product product = null;
        
        try {
            file = new FileReader(path);
            BufferedReader buffer = new BufferedReader(file);
            
            Object saleReflection = Class.forName(Sale.class.getName()).newInstance();
            Object productReflection = Class.forName(Product.class.getName()).newInstance();
            
            this.logService.logMessage("lendo arquivo importado", "processArchive");
            int countRow = 0;
            String row = buffer.readLine();       
            
            while (row != null){
                if (countRow > 0) {
                    sale = new Sale();
                    product = new Product();
                    String[] fields = row.split(",");

                    for (Field fieldSale : saleReflection.getClass().getDeclaredFields()) {
                        if (fieldSale.getName().equals("product")) {
                            for (Field fieldProduct : productReflection.getClass().getDeclaredFields()) {
                                if (fieldProduct.getName().equals("productKey")){
                                    product.setProductKey(fields[0]);                                    
                                }
                                
                                if (fieldProduct.getName().equals("productName")) {
                                    product.setProductName(fields[1]);
                                }
                            }
                        }
                        
                        if (fieldSale.getName().equals("amount")) {
                            double amount = Double.valueOf(fields[2]);
                            sale.setAmount(amount);
                        }
                        
                        if (fieldSale.getName().equals("saleDate")) {
                            Date date = new SimpleDateFormat("yy/MM/yyyy").parse(fields[3]);
                            sale.setSaleDate(date);
                        }
                    }
                    
                    this.logService.logMessage("adicionando produto " + product.getProductKey(), "processArchive");
                    sale.setProduct(product);                    
                    list.add(sale);
                }
                
                countRow++;
                row = buffer.readLine();
            }
            
            this.logService.logMessage("encerrando processo de leitura", "processArchive");
            file.close();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | ParseException ex) {
            this.logService.logMessage(ex.toString(), "processArchive");
            ex.printStackTrace();
        }
        
        return list;
    }
}
