package br.com.sgv.service;

import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.model.Checkout;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.repository.CheckoutRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class CheckoutService {
    
    private CheckoutRepository checkoutRepository = null;
    
    public CheckoutService() {
        this.checkoutRepository = new CheckoutRepository();
    }
    
    public ResponseModel<Checkout> getCheckoutById(int checkoutId) {
        ResponseModel<Checkout> response = new ResponseModel<>();
        
        try {
            Checkout checkout = new Checkout();
            checkout.setId(checkoutId);
            
            checkout = this.checkoutRepository.find(checkout, checkout.getId());
            response.setModel(checkout);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }        
        
        return response;
    }
    
    public ResponseModel<List<Checkout>> getAll() {
        ResponseModel<List<Checkout>> response = new ResponseModel<>();
        
        try {
            List<Checkout> listCheckout = this.checkoutRepository.getAll();
            response.setModel(listCheckout);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<List<Checkout>> getCheckoutToMonth(Date initial, Date finaly) {
        ResponseModel<List<Checkout>> response = new ResponseModel<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            String initialDate = sdf.format(initial);
            String finalDate = sdf.format(finaly);
            
            List<Checkout> listCheckout = this.checkoutRepository.getCheckoutToMonth(initialDate, finalDate);
            response.setModel(listCheckout);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }        
        
        return response;
    }
    
    public ResponseModel<Boolean> saveCheckout(String checkoutName, String checkoutValue, Date checkoutDate) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            int status = StatusRegisterEnum.PENDING.value;
            double value = Double.valueOf(checkoutValue);
            
            StatusRegister statusRegister = new StatusRegister();
            statusRegister.setId(status);
            
            Checkout checkout = new Checkout();            
            checkout.setCheckoutName(checkoutName);
            checkout.setCheckoutValue(value);
            checkout.setCheckoutDate(checkoutDate);
            checkout.setStatusRegister(statusRegister);
            
            this.checkoutRepository.save(checkout);
            response.setModel(true);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_save);
            response.setModel(false);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> removeCheckout(Checkout checkout) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            checkout = this.checkoutRepository.find(new Checkout(), checkout.getId());
            
            if (checkout.getStatusRegister().getId() == StatusRegisterEnum.TOTALIZED.value){
                response.setMensage(Messages.fail_remove);
                response.setModel(false);
            } else {
                boolean status = this.checkoutRepository.remove(checkout);
                response.setModel(status);
            }
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.status_register);
            response.setModel(false);
        }
        
        return response;
    }
}
