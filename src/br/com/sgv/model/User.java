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

/** *
 * @author Anderson Junior Rodrigues
 */

@Entity
@Table(name = "user")
public class User implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false, length = 45)
    private String userName;
    
    @Column(name = "user_login", nullable = false, length = 15)
    private String userLogin;
    
    @Column(name = "user_password", nullable = false, length = 10)
    private String userPassword;
    
    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;
    
    public User() { }
    
    public User(String userName, String userLogin, String userPassword, UserType userType) {
        this.userName = userName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
