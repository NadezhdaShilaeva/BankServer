package com.shilaeva.handlers.query;

import java.util.Map;

public class Request  implements Query {
    private final RequestMethod method;
    private final String path;
    private final Map<String, String> headers;
    private String body;
    private Principal principal;

    public Request(String method, String path, Map<String, String> headers) {
        this.method = RequestMethod.valueOf(method);
        this.path = path;
        this.headers = headers;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Principal getPrincipal() {
        return principal;
    }
}
