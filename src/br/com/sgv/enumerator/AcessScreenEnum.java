package br.com.sgv.enumerator;

/**
 * @author Anderson Junior Rodrigues
 */
public enum AcessScreenEnum {
    
    REGISTER_PRODUCT(1),
    REGISTER_MEASURE(2),
    REGISTER_USER(3),
    VIEW_PRODUCT(4),
    VIEW_MEASURE(5),
    VIEW_USER(6),
    REGISTER_EXIT(7),
    REGISTER_REPORT(8),
    VIEW_REPORT(9),
    VIEW_ABOUT(10);
    
    public int value;
    
    private AcessScreenEnum(int value){
        this.value = value;
    }
}
