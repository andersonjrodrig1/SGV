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
public enum MeasureTypeEnum {
    
    UNITY(1),
    PIECE(2),
    KILOGRAM(3),
    MILLILITER(4),
    GRASS(5),
    LITER(6),
    CUP(7);
    
    public int value;
    
    private MeasureTypeEnum(int value) {
        this.value = value;
    }
}
