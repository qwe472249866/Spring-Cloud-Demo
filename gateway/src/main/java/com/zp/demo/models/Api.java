package com.zp.demo.models;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zp
 * @Title: Api
 * @Description: TODO
 * @date 2019/1/3017:17
 */
public class Api {

    private String path;

    private String method;

    private List<URI> uris = new ArrayList<>();

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

    public List<URI> getUris() {
        return uris;
    }

    public void setUris(List<URI> uris) {
        this.uris = uris;
    }

    @Override
    public String toString() {
        return "Api{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", uris=" + uris +
                '}';
    }
}
