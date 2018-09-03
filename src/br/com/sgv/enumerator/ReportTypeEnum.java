package br.com.sgv.enumerator;

/**
 * @author Anderson Junior Rodrigues
 */

public enum ReportTypeEnum {
    
    SALE(1),
    PAID(2);
    
    public int value;
    
    private ReportTypeEnum(int value) {
        this.value = value;
    }
}
