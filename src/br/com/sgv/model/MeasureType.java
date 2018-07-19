package br.com.sgv.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Anderson Junior Rodrigues
 */

@Entity
@Table(name = "measure_type")
public class MeasureType implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "measure_type", nullable = false, length = 45)
    private String measureType;
    
    @Column(name = "initials", nullable = true, length = 2)
    private String initials;
    
    @ManyToOne
    @JoinColumn(name = "calc_type_id", nullable = false)
    private CalcType calcType;
    
    public MeasureType() { }
    
    public MeasureType(String measureType, String initials, CalcType calcType) {
        this.measureType = measureType;
        this.initials = initials;
        this.calcType = calcType;
    }

    public CalcType getCalcType() {
        return calcType;
    }

    public void setCalcType(CalcType calcType) {
        this.calcType = calcType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @Override
    public String toString() {
        return getMeasureType();
    }
}
