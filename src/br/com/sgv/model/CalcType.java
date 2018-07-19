package br.com.sgv.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Anderson Junior Rodrigues
 */

@Entity
@Table(name = "calc_type")
public class CalcType implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "calc_type", nullable = false, length = 45)
    private String calcType;
    
    public CalcType() { }
    
    public CalcType(String calcType) {
        this.calcType = calcType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }    
}
