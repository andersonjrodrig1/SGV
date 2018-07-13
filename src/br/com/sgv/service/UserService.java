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
import br.com.sgv.shared.Messages;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.swing.JOptionPane;

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
    
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        
        try {
            users = userRepository.getAll();

            users.stream().forEach(u -> {
                String password = this.decode(u.getUserPassword());
                u.setUserPassword(password);
            });
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, Messages.fail_find);
            System.out.printf("Eror: ", ex);
            throw ex;
        }
        
        return users;
    }
    
    public User findUser(String userName, String userPassword) {
        String cryptoPassword = this.encode(userPassword);
        
        User userLogin = new User();
        userLogin.setUserName(userName);
        userLogin.setUserPassword(cryptoPassword);
        
        try {
            userLogin = userRepository.findUser(userLogin);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Messages.fail_find);
            System.out.printf("Eror: ", ex);
            throw ex;
        }
        
        return userLogin;
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
            
            userRepository.save(this.user);            
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
    
    private String decode(String text) {
        byte[] decodeBytes = Base64.getMimeDecoder().decode(text);
        String decode = new String(decodeBytes);
        
        return decode;
    }
}
