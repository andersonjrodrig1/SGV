package br.com.sgv.service;

import br.com.sgv.model.MeasureType;
import br.com.sgv.model.Product;
import br.com.sgv.repository.ProductRepository;
import br.com.sgv.shared.ResponseModel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class ProductService {
    
    private LogService logService = null;
    private ProductRepository productRepository = null;
    
    public ProductService() {
        this.logService = new LogService("ProductService", "ListProduct");
        this.productRepository = new ProductRepository();
    }
    
    public ResponseModel<Boolean> saveProduct(String productKey, String productName, String productValue, MeasureType measureType) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {           
            this.logService.logMessage("montando objecto para salvar", "saveProduct");
            Product product = this.setProduct(productKey, productName, productValue, measureType);
            this.productRepository.save(product);
            
            response.setModel(true);
            this.logService.logMessage("produto salvo com sucesso", "saveProduct");
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "saveProduct");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao salvar os dados!");
            response.setModel(false);
        }
        
        return response;
    }
    
    public ResponseModel<List<Product>> getProducts() {
        ResponseModel<List<Product>> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("montando objecto para busca", "getProducts");
            List<Product> listProduct = this.productRepository.getAll();
            response.setModel(listProduct);
            this.logService.logMessage("encontrado " + listProduct.size() + " produtos", "getProducts");
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "getProducts");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setModel(null);
        }       
        
        return response;
    }
    
    public ResponseModel<List<Product>> getProductByNameOrKey(String productKey, String productName) {
        ResponseModel<List<Product>> response = new ResponseModel<>();
        List<Product> listProduct = new ArrayList<>();
        
        try {
            this.logService.logMessage("busca por parametro -> key: " + productKey + " product: " + productName, "getProductByNameOrKey");
            Product product = new Product();
            product.setProductKey(productKey);
            product.setProductName(productName);
            
            listProduct = this.productRepository.getProductByKeyOrName(product);
            response.setModel(listProduct);
            this.logService.logMessage("encontrados " + listProduct.size() + " produtos", "getProductByNameOrKey");
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "getProductByNameOrKey");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<Product> getProductByKey(String productKey) {
        ResponseModel<Product> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("buscar de produto pela key: " + productKey, "getProductByKey");
            Product product = this.productRepository.getProductByKey(productKey);
            response.setModel(product);
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "getProductByKey");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> removeProduct(Product product) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("solicitando exclusao do produto", "removeProduct");
            boolean status = this.productRepository.remove(product);
            response.setModel(status);
            this.logService.logMessage("produto excluido", "removeProduct");
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "removeProduct");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao excluir os dados!");
            response.setModel(false);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> updateProduct(long productId, String productKey, String productName, String productValue, MeasureType measureType) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("montando objeto para atualizar", "updateProduct");
            Product product = this.setProduct(productKey, productName, productValue, measureType);
            product.setId(productId);
            this.productRepository.update(product);
            
            response.setModel(true);
            this.logService.logMessage("produto atualizado", "updateProduct");
        } catch(Exception ex) {
            this.logService.logMessage(ex.toString(), "updateProduct");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao atualizar!");
            response.setModel(false);
        }
        
        return response;
    }
    
    private Product setProduct(String productKey, String productName, String productValue, MeasureType measureType) {
        double value = Double.parseDouble(productValue);
        Date now = Date.from(Instant.now());

        Product product = new Product();
        product.setProductKey(productKey);
        product.setProductName(productName);
        product.setProductValue(value);
        product.setMeasureType(measureType);
        product.setRegisterDate(now);
        
        return product;
    }
}
