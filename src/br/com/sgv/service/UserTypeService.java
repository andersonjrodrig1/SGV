/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.UserType;
import br.com.sgv.repository.UserTypeRepository;
import br.com.sgv.shared.Messages;
import javax.swing.JOptionPane;

/**
 *
 * @author ander
 */
public class UserTypeService {
    
    private UserTypeRepository userTypeRepository = null;
    private UserType userType = null;
    
    public UserTypeService() {
        userTypeRepository = new UserTypeRepository();
    }
    
    public UserType findUserTypeById(int userTypeId) {
        try {
            userType = userTypeRepository.find(new UserType(), userTypeId);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, Messages.fail_find);
            System.out.printf("Eror: ", ex);
            throw ex;
        }
        
        return userType;
    }
}
