package br.com.sgv.model;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "acess_screen")
public class AcessScreen implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "screen_name", nullable = false, length = 30)
    private String screenName;
    
    public AcessScreen() { }
    
    public AcessScreen(String screenName){
        this.screenName = screenName;
    }
    
    public AcessScreen(long id, String screenName) {
        this.id = id;
        this.screenName = screenName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
