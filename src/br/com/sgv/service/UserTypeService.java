/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.UserType;
import br.com.sgv.repository.UserTypeRepository;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;

/**
 *
 * @author ander
 */
public class UserTypeService {
    
    private UserTypeRepository userTypeRepository = null;
    private ResponseModel<UserType> response = null;
    private UserType userType = null;
    
    public UserTypeService() {
        userTypeRepository = new UserTypeRepository();
    }
    
    public ResponseModel<UserType> findUserTypeById(long userTypeId) {
        this.response = new ResponseModel<>();
        
        try {
            this.userType = userTypeRepository.find(new UserType(), userTypeId);
            this.response.setModel(userType);
        } catch(Exception ex){
            System.out.printf("Eror: ", ex);
            this.response.setError(ex.getMessage());
            this.response.setMensage(Messages.fail_find);
            this.response.setException(ex);
            this.response.setModel(null);
        }
        
        return response;
    }
}
