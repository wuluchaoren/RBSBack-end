package com.rbs.project.secruity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:53 2018/12/27
 */
public class UrlAuthorization {
    private String url;
    private List<String> method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getMethod() {
        return method;
    }

    public void setMethod(List<String> method) {
        this.method = method;
    }

    public UrlAuthorization(String url) {
        this.url = url;
    }

    public UrlAuthorization addAllMethod() {
        if (this.method == null) {
            this.method = new ArrayList<>();
        }
        this.method.add("GET");
        this.method.add("POST");
        this.method.add("DELETE");
        this.method.add("PUT");
        return this;
    }

    public UrlAuthorization addGetMethod() {
        if (this.method == null) {
            this.method = new ArrayList<>();
        }
        this.method.add("GET");
        return this;
    }

    public UrlAuthorization addPostMethod() {
        if (this.method == null) {
            this.method = new ArrayList<>();
        }
        this.method.add("POST");
        return this;
    }

    public UrlAuthorization addDeleteMethod() {
        if (this.method == null) {
            this.method = new ArrayList<>();
        }
        this.method.add("DELETE");
        return this;
    }

    public UrlAuthorization addPutMethod() {
        if (this.method == null) {
            this.method = new ArrayList<>();
        }
        this.method.add("PUT");
        return this;
    }

}
