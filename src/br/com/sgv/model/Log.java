package br.com.sgv.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author Anderson Junior Rodrigues
 */

@Entity
@Table(name = "log")
public class Log implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "class_name", nullable = false, length = 45)
    private String className;
    
    @Column(name = "method_name", nullable = false, length = 45)
    private String methodName;
    
    @Column(name = "screen_name", nullable = true, length = 45)
    private String screenName;
    
    @Column(name = "log_message", nullable = false)
    private String logMessage;
    
    @Column(name = "date_log", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLog;
    
    public Log(String className, String screenName) {
        this.className = className;
        this.screenName = screenName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }
}
