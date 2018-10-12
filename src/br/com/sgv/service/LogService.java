package br.com.sgv.service;

import br.com.sgv.model.Log;
import br.com.sgv.repository.LogRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson Junior Rodrigues
 */

public class LogService {
    
    private LogRepository logRepository = null;
    private List<Log> logs = null;
    private Log log = null;
    
    private String className = null;
    private String screenName = null;
    private String methodName = null;
    
    public LogService(String className, String screenName) {
        this.logRepository = new LogRepository();
        
        this.className = className;
        this.screenName = screenName;
    }
    
    public LogService(String className, String screenName, String methodName){
        this.logRepository = new LogRepository();
        
        this.logs = new ArrayList<>();
        this.className = className;
        this.screenName = screenName;
        this.methodName = methodName;
    }
    
    public void logMessage(String logMessage, String methodName){
        this.log = new Log(this.className, this.screenName);
        this.log.setMethodName(methodName);
        this.log.setDateLog(Date.from(Instant.now()));
        this.log.setLogMessage(logMessage);
        
        this.saveLogSystem();
    }
    
    public void addLogMessage(String logMessage){
        Log log = new Log();
        log.setClassName(this.className);
        log.setScreenName(this.screenName);
        log.setMethodName(this.methodName);
        log.setLogMessage(logMessage);
        
        this.logs.add(log);
    }
    
    protected void saveListLog(){
        this.logs.stream().forEach(log -> this.logRepository.save(log));
    }
    
    private void saveLogSystem(){
        this.logRepository.save(this.log);
    }
}
