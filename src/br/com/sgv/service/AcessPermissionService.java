package br.com.sgv.service;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.repository.AcessPermissionRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */
public class AcessPermissionService {
    
    ResponseModel<List<AcessPermission>> response = null;
    private AcessPermissionRepository acessPermissionRepository = null;
    
    public AcessPermissionService () {
        acessPermissionRepository = new AcessPermissionRepository();
    }
    
    public ResponseModel<List<AcessPermission>> findListAcessPermissonByUserType(long userType) {
        response = new ResponseModel<>();
        List<AcessPermission> listAcessPermission = new ArrayList<>();
        
        try {
            listAcessPermission = acessPermissionRepository.findByUserTypeId(userType);
            response.setModel(listAcessPermission);
        } catch(Exception ex){
            System.out.printf("Eror: ", ex);
            response.setError(ex.getMessage());
            response.setException(ex);
            response.setMensage(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
}
