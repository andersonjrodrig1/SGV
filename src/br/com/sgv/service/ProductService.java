/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.MeasureType;
import br.com.sgv.model.Product;
import br.com.sgv.repository.ProductRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ander
 */
public class ProductService {
    
    private ProductRepository productRepository = null;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    public ResponseModel<Boolean> saveProduct(String productKey, String productName, String productValue, MeasureType measureType) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            double value = Double.parseDouble(productValue);
            Date now = Date.from(Instant.now());
            
            Product product = new Product();
            product.setProductKey(productKey);
            product.setProductName(productName);
            product.setProductValue(value);
            product.setMeasureType(measureType);
            product.setRegisterDate(now);
            
            this.productRepository.save(product);
            response.setModel(true);
        } catch(Exception ex) {
            System.out.println("Error: " + ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_save);
            response.setModel(false);
        }
        
        return response;
    }
}
