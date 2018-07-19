package br.com.sgv.shared;

/**
 * @author Anderson Junior Rodrigues
 * @param <T>
 */
public class ResponseModel<T> {
    
    public ResponseModel() { }
    
    public ResponseModel(T model) {
        this.model = model;
    }
    
    private T model;
    private Exception exception;
    private String mensage;
    private String error;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMensage() {
        return mensage;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
