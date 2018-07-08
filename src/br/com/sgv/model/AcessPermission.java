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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;
    
    @ManyToOne
    @JoinColumn(name = "acess_screen_id", nullable = false)
    private AcessScreen acessScreen;
    
    @Column(name = "has_acess_permission", nullable = false)
    private boolean hasAcessPermission;
    
    public AcessPermission() { }
    
    public AcessPermission(long id, UserType userType, AcessScreen acessScreen, boolean hasAcessPermission) {
        this.id = id;
        this.userType = userType;
        this.acessScreen = acessScreen;
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

    public AcessScreen getAcessScreen() {
        return acessScreen;
    }

    public void setAcessScreen(AcessScreen acessScreen) {
        this.acessScreen = acessScreen;
    }

    public boolean isHasAcessPermission() {
        return hasAcessPermission;
    }

    public void setHasAcessPermission(boolean hasAcessPermission) {
        this.hasAcessPermission = hasAcessPermission;
    }
}
