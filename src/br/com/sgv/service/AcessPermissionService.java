package br.com.sgv.service;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.repository.AcessPermissionRepository;
import br.com.sgv.shared.ResponseModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class AcessPermissionService {
    
    private LogService logService = null;
    private AcessPermissionRepository acessPermissionRepository = null;
    
    public AcessPermissionService () {
        this.logService = new LogService(AcessPermission.class.getName(), "SGV");
        this.acessPermissionRepository = new AcessPermissionRepository();
    }
    
    public ResponseModel<List<AcessPermission>> findListAcessPermissonByUserType(long userType) {
        ResponseModel<List<AcessPermission>> response = new ResponseModel<>();
        List<AcessPermission> listAcessPermission = new ArrayList<>();
        
        try {
            this.logService.logMessage("verificando telas de acesso", "findListAcessPermissonByUserType");
            listAcessPermission = acessPermissionRepository.findByUserTypeId(userType);
            response.setModel(listAcessPermission);
        } catch(Exception ex){
            this.logService.logMessage(ex.toString(), "findListAcessPermissonByUserType");
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage("Falha ao buscar os dados!");
            response.setModel(null);
        }
        
        return response;
    }
}
