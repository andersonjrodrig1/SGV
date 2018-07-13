/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.service;

import br.com.sgv.model.AcessPermission;
import br.com.sgv.repository.AcessPermissionRepository;
import br.com.sgv.repository.PersistenceRepository;
import br.com.sgv.shared.Messages;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ander
 */
public class AcessPermissionService {
    
    private AcessPermissionRepository acessPermissionRepository = null;
    
    public AcessPermissionService () {
        acessPermissionRepository = new AcessPermissionRepository();
    }
    
    public List<AcessPermission> findListAcessPermissonByUserType(int userType) {
        List<AcessPermission> listAcessPermission = new ArrayList<>();
        
        try {
            listAcessPermission = acessPermissionRepository.findByUserTypeId(userType);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, Messages.fail_find);
            System.out.printf("Eror: ", ex);
            throw ex;
        }
        
        return listAcessPermission;
    }
}
