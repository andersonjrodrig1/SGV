package br.com.sgv.enumerator;

/**
 * @author Anderson Junior Rodrigues
 */
public enum AcessScreenEnum {
    
    INSERT_USER(1),
    CONSULT_USER(2),
    DELETE_USER(3),
    INSERT_PRODUCT(4),
    CONSULT_PRODUCT(5),
    UPDATE_PRODUCT(6),
    DELETE_PRODUCT(7),
    GENERATE_REPORT(8),
    CONSULTE_PRODUCT(9);
    
    public int value;
    
    private AcessScreenEnum(int value){
        this.value = value;
    }
}
