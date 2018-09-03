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
@Table(name = "report_type")
public class ReportType implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "report_type", nullable = false, length = 45)
    private String reportType;
    
    public ReportType() {}
    
    public ReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public ReportType(int id, String reportType) {
        this.id = id;
        this.reportType = reportType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    } 

    @Override
    public String toString() {
        return getReportType();
    }
}
