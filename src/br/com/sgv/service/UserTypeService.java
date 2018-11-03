package br.com.sgv.service;

import br.com.sgv.model.UserType;
import br.com.sgv.repository.UserTypeRepository;
import br.com.sgv.shared.ResponseModel;

/**
 * @author Anderson Junior Rodrigues
 */
public class UserTypeService {
    
    private LogService logService = null;
    private UserTypeRepository userTypeRepository = null;
    private ResponseModel<UserType> response = null;
    private UserType userType = null;
    
    public UserTypeService() {
        this.logService = new LogService(UserType.class.getName(), "SGV");
        this.userTypeRepository = new UserTypeRepository();
    }
    
    public ResponseModel<UserType> findUserTypeById(long userTypeId) {
        this.response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("busca das permmissoes de acesso para user type: " + userTypeId, "findUserTypeById");
            this.userType = userTypeRepository.find(new UserType(), userTypeId);
            this.response.setModel(userType);
        } catch(Exception ex){
            this.logService.logMessage(ex.toString(), "findUserTypeById");
            this.response.setError(ex.getMessage());
            this.response.setMensage("Falha ao buscar os dados!");
            this.response.setException(ex);
            this.response.setModel(null);
        }
        
        return response;
    }
}
