package br.com.sgv.service;

import br.com.sgv.enumerator.StatusRegisterEnum;
import br.com.sgv.model.Checkout;
import br.com.sgv.model.StatusRegister;
import br.com.sgv.repository.CheckoutRepository;
import br.com.sgv.shared.ResponseModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class CheckoutService {
    
    private LogService logService = null;
    private CheckoutRepository checkoutRepository = null;
    
    public CheckoutService() {
        this.logService = new LogService(Checkout.class.getName(), "Checkout");
        this.checkoutRepository = new CheckoutRepository();
    }
    
    public ResponseModel<Checkout> getCheckoutById(int checkoutId) {
        ResponseModel<Checkout> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("buscar de checkout id: " + checkoutId, "getCheckoutById");
            Checkout checkout = new Checkout();
            checkout.setId(checkoutId);
            
            checkout = this.checkoutRepository.find(checkout, checkout.getId());
            response.setModel(checkout);
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getCheckoutById");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }        
        
        return response;
    }
    
    public ResponseModel<List<Checkout>> getAll() {
        ResponseModel<List<Checkout>> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("buscar de todos checkouts", "getCheckoutById");
            List<Checkout> listCheckout = this.checkoutRepository.getAll();
            response.setModel(listCheckout);
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getAll");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<List<Checkout>> getCheckoutToMonth(Date initial, Date finaly) {
        ResponseModel<List<Checkout>> response = new ResponseModel<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            this.logService.logMessage("periodo de busca " + initial + " à " + finaly, "getCheckoutToMonth");
            String initialDate = sdf.format(initial);
            String finalDate = sdf.format(finaly);
            
            List<Checkout> listCheckout = this.checkoutRepository.getCheckoutToMonth(initialDate, finalDate);
            response.setModel(listCheckout);
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "getCheckoutToMonth");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }        
        
        return response;
    }
    
    public ResponseModel<Boolean> saveCheckout(String checkoutName, String checkoutValue, Date checkoutDate) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("preparando dados para salvar", "saveCheckout");
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
            this.logService.logMessage(ex.toString(), "saveCheckout");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao salvar os dados!");
            response.setModel(false);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> removeCheckout(Checkout checkout) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("busca de checkout na base de dados", "removeCheckout");
            checkout = this.checkoutRepository.find(new Checkout(), checkout.getId());
            
            if (checkout.getStatusRegister().getId() == StatusRegisterEnum.TOTALIZED.value){
                response.setMensage("Falha ao excluir os dados!");
                response.setModel(false);
            } else {
                boolean status = this.checkoutRepository.remove(checkout);
                response.setModel(status);
            }
        } catch (Exception ex) {
            this.logService.logMessage(ex.toString(), "removeCheckout");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Registro de saída não pode ser excluida.\nTotalização já realizada.");
            response.setModel(false);
        }
        
        return response;
    }
}
