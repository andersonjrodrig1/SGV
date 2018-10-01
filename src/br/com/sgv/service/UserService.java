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
import br.com.sgv.shared.ResponseModel;
import java.util.List;

/**
 *
 * @author Anderson Junior Rodrigues
 */
public class UserService {
    
    private LogService logService = null;
    private UserRepository userRepository = null;
    private User user = null;
    private UserType usertype = null;
    
    public UserService() {
        this.userRepository = new UserRepository();
        this.logService = new LogService(UserService.class.getName(), "Login");
    }
    
    public ResponseModel<List<User>> getAll() {
        ResponseModel<List<User>> response = new ResponseModel<>();
        List<User> users = null;
        
        try {
            this.logService.logMessage("Busca de Usuários", "getAll");
            users = this.userRepository.getAll();

            users.stream().forEach(u -> {
                String password = ArchiveBase64.decode(u.getUserPassword());
                u.setUserPassword(password);
            });
            
            this.logService.logMessage("Usuários encontrados", "getAll");
            response.setModel(users);
        } catch(Exception ex){
            System.out.printf("Error: ", ex);
            response.setModel(null);
            response.setError(ex.getMessage());
            response.setMensage("Falha ao buscar os dados!");
            this.logService.logMessage(ex.toString(), "getAll");
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
            this.logService.logMessage("Busca de Usuário", "findUser");
            this.user = userRepository.findUser(this.user);
            this.logService.logMessage("Busca realizada", "findUser");
            response.setModel(this.user);
            
            if (this.user == null) {
                response.setMensage("Usuário e/ou senha inválida!");
                response.setModel(null);
            }
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setException(ex);
            response.setError(ex.getMessage());
            response.setMensage("Falha ao buscar os dados!");
            this.logService.logMessage(ex.toString(), "findUser");
        }
        
        return response;
    }
    
    public ResponseModel<Boolean> saveUser(String name, String login, String password) {
        ResponseModel<Boolean> response = new ResponseModel<>();
        
        try {
            this.logService.logMessage("Salvar usuário", "saveUser");
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
            this.logService.logMessage("Usuário salvo", "saveUser");
            response.setModel(true);
        } catch (Exception ex) {
            System.out.printf("Error: ", ex);
            response.setModel(false);
            response.setError(ex.getMessage());
            response.setMensage("Falha ao salvar os dados!");
            response.setException(ex);
            this.logService.logMessage(ex.toString(), "saveUser");
        }
        
        return response;
    }
}
