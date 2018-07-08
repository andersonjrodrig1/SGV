package br.com.sgv.controller;

/**
 * @author Anderson Junior Rodrigues
 */
public interface Persistence {
    
    public <T> boolean save(T obj);
    
    public <T> T find(int id);
    
    public <T> boolean update(T obj);
    
    public <T> boolean remove(T obj);
}
