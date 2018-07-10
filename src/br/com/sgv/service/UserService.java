/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.controller.UserController;
import br.com.sgv.enumerator.UserTypeEnum;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import br.com.sgv.shared.Messages;
import java.util.Base64;
import javax.swing.JOptionPane;

/**
 *
 * @author ander
 */
public class UserService {
    
    private UserController userController = null;
    private User user = null;
    private UserType usertype = null;
    
    public UserService() {
        userController = new UserController();
    }
    
    public void saveUser(String name, String login, String password) {
        try {
            long type = UserTypeEnum.SALESMAN.value;
            String passwordEncode = this.encode(password);
            
            this.user = new User();
            this.user.setUserName(name);
            this.user.setUserLogin(login);
            this.user.setUserPassword(passwordEncode);
            
            this.usertype = new UserType();
            this.usertype.setId(type);
            
            this.user.setUserType(this.usertype);
            
            userController.save(this.user);            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Messages.fail_save);
            System.out.printf("Eror: ", ex);
            throw ex;
        }
    }
    
    private String encode(String text) {
        byte[] encodeBytes = text.getBytes();
        String encode = Base64.getEncoder().encodeToString(encodeBytes);
        
        return encode;
    }
}
