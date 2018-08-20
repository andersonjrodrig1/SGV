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
@Table(name = "transaction_sale")
public class TransactionSale implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "transaction_id", nullable = false, length = 45)
    private String transactionId;
    
    @Column(name = "amount", nullable = true)
    private int amount;
    
    @Column(name = "weight", nullable = true)
    private double weight;
    
    @Column(name = "discount_value", nullable = true)
    private double discountValue;
    
    @Column(name = "total_value", nullable = false)
    private double totalValue;
    
    @Column(name = "value_with_discount", nullable = true)
    private double valueWithDiscount;
    
    @ManyToOne
    @JoinColumn(name = "pay_type_id", nullable = false)
    private PayType payType;
    
    @Column(name = "register_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registerDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getValueWithDiscount() {
        return valueWithDiscount;
    }

    public void setValueWithDiscount(double valueWithDiscount) {
        this.valueWithDiscount = valueWithDiscount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
