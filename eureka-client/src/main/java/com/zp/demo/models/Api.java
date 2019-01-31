package com.zp.demo.models;



/**
 *  api model
 */
public class Api {

    //TODO not null check
    private String path;

    //TODO not null check
    private String method;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Api path(String path){
        this.path = path;
        return this;
    }

    public Api method(String method){
        this.method = method;
        return this;
    }

    @Override
    public String toString() {
        return "Api{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
