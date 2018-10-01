package br.com.sgv.service;

import br.com.sgv.model.Log;
import br.com.sgv.repository.LogRepository;
import java.time.Instant;
import java.util.Date;

/**
 * @author Anderson Junior Rodrigues
 */

public class LogService {
    
    private LogRepository logRepository = null;
    Log log = null;
    
    private String className = null;
    private String screenName = null;
    
    public LogService(String className, String screenName) {
        this.logRepository = new LogRepository();
        
        this.className = className;
        this.screenName = screenName;
    }
    
    public void logMessage(String logMessage, String methodName){
        this.log = new Log(this.className, this.screenName);
        this.log.setMethodName(methodName);
        this.log.setDateLog(Date.from(Instant.now()));
        this.log.setLogMessage(logMessage);
        
        this.saveLogSystem();
    }
    
    private void saveLogSystem(){
        this.logRepository.save(this.log);
    }
}
