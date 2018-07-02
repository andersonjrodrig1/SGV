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
@Table(name = "acess_permission")
public class AcessPermission implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;
    
    @ManyToOne
    @JoinColumn(name = "acess_type_id", nullable = false)
    private AcessType acessType;
    
    @Column(name = "has_acess_permission", nullable = false)
    private boolean hasAcessPermission;
    
    public AcessPermission() { }
    
    public AcessPermission(long id, UserType userType, AcessType acessType, boolean hasAcessPermission) {
        this.id = id;
        this.userType = userType;
        this.acessType = acessType;
        this.hasAcessPermission = hasAcessPermission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public AcessType getAcessType() {
        return acessType;
    }

    public void setAcessType(AcessType acessType) {
        this.acessType = acessType;
    }

    public boolean isHasAcessPermission() {
        return hasAcessPermission;
    }

    public void setHasAcessPermission(boolean hasAcessPermission) {
        this.hasAcessPermission = hasAcessPermission;
    }
}
