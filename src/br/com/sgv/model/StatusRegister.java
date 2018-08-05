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
@Table(name = "status_register")
public class StatusRegister implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "status_register", nullable = false, length = 45)
    private String statusRegister;
    
    public StatusRegister() { }
    
    public StatusRegister(String statusRegister) {
        this.statusRegister = statusRegister;
    }
    
    public StatusRegister(long id, String statusRegister) {
        this.id = id;
        this.statusRegister = statusRegister;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusRegister() {
        return statusRegister;
    }

    public void setStatusRegister(String statusRegister) {
        this.statusRegister = statusRegister;
    }
}
