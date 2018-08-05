/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.enumerator;

/**
 *
 * @author ander
 */
public enum StatusRegisterEnum {
    
    PENDING(1),
    TOTALIZED(2);
    
    public int value;
    
    private StatusRegisterEnum(int value) {
        this.value = value;
    }
}
