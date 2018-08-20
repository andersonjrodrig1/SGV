package br.com.sgv.enumerator;

/**
 * @author Anderson Junior Rodrigues
 */

public enum PayTypeEnum {

    MONEY(1),
    CARD(2);
    
    public int value;
    
    private PayTypeEnum(int value) {
        this.value = value;
    }
}
