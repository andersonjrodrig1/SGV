package br.com.sgv.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author Anderson Junior Rodrigues
 */

@Entity
@Table(name = "checkout")
public class Checkout implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "checkout_name", nullable = false, length = 45)
    private String checkoutName;
    
    @Column(name = "checkout_value", nullable = false)
    private double checkoutValue;
    
    @Column(name = "checkout_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date checkoutDate;
    
    @ManyToOne
    @JoinColumn(name = "status_register_id", nullable = false)
    private StatusRegister statusRegister;
    
    public Checkout() { }
    
    public Checkout(long id, String checkoutName, double checkoutValue, Date checkoutDate, StatusRegister statusRegister) {
        this.id = id;
        this.checkoutName = checkoutName;
        this.checkoutValue = checkoutValue;
        this.checkoutDate = checkoutDate;
        this.statusRegister = statusRegister;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCheckoutName() {
        return checkoutName;
    }

    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public double getCheckoutValue() {
        return checkoutValue;
    }

    public void setCheckoutValue(double checkoutValue) {
        this.checkoutValue = checkoutValue;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public StatusRegister getStatusRegister() {
        return statusRegister;
    }

    public void setStatusRegister(StatusRegister statusRegister) {
        this.statusRegister = statusRegister;
    }
}
