/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.repository.UserRepository;
import br.com.sgv.enumerator.UserTypeEnum;
import br.com.sgv.model.User;
import br.com.sgv.model.UserType;
import br.com.sgv.shared.ArchiveBase64;
import br.com.sgv.shared.Messages;
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 *
 * @author Anderson Junior Rodrigues
 */
public class UserService {
    
    private UserRepository userRepository = null;
    private User user = null;
    private UserType usertype = null;
    
    public UserService() {
        userRepository = new UserRepository();
    }
    
    public ResponseModel<List<User>> getAll() {
        ResponseModel<List<User>> response = new ResponseModel<>();
        List<User> users = null;
        
        try {
            users = userRepository.getAll();

            users.stream().forEach(u -> {
                String password = ArchiveBase64.decode(u.getUserPassword());
                u.setUserPassword(password);
            });
            
            response.setModel(users);
        } catch(Exception ex){
            System.out.printf("Eror: ", ex);
            response.setModel(null);
            response.setError(ex.getMessage());
            response.setError(ex.getMessage());
            response.setMensage(Messages.fail_find);
        }
        
        return response;
    }
    
    public ResponseModel<User> findUser(String userName, String userPassword) {
        ResponseModel<User> response = new ResponseModel<>();
        this.user = new User();
        
        String cryptoPassword = ArchiveBase64.encode(userPassword);        
        
        this.user.setUserName(userName);
        this.user.setUserPassword(cryptoPassword);
        
        try {
            this.user = userRepository.findUser(this.user);
            response.setModel(this.user);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setException(ex);
            response.setError(ex.getMessage());
            response.setError(Messages.fail_find);
            response.setModel(null);
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> saveUser(String name, String login, String password) {
        ResponseModel<Boolean> response = null;
        
        try {
            long type = UserTypeEnum.SALESMAN.value;
            String passwordEncode = ArchiveBase64.encode(password);
            
            this.user = new User();
            this.user.setUserName(name);
            this.user.setUserLogin(login);
            this.user.setUserPassword(passwordEncode);
            
            this.usertype = new UserType();
            this.usertype.setId(type);
            
            this.user.setUserType(this.usertype);
            
            userRepository.save(this.user);
            response.setModel(true);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setModel(false);
            response.setError(ex.getMessage());
            response.setMensage(Messages.fail_save);
            response.setException(ex);
        }
        
        return response;
    }
}
