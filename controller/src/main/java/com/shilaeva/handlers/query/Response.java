package com.shilaeva.handlers.query;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response<T> implements Query {
    private final String protocolVersion = "HTTP/1.1";
    private final int statusCode;
    private final String statusMessage;
    private Map<String, String> headers;
    private T body;

    private Response(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    private Response(int statusCode, String statusMessage, T body) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;

        this.headers = new LinkedHashMap<>();
        this.headers.put("Server", "MyBankServer");
        this.headers.put("Content-Type", "application/json");

        this.body = body;
    }

    public static <T> Response<T> ok(T body) {
        return new Response<T>(200, "OK", body);
    }

    public static <T> Response<T> created(T body) {
        return new Response<T>(201, "Created", body);
    }

    public static <T> Response<T> badRequest(T body) {
        return new Response<T>(400, "Bad Request", body);
    }

    public static <T> Response<T> notAuthorized(T body) {
        return new Response<T>(401, "Not Authorized", body);
    }

    public static <T> Response<T> forbidden(T body) {
        return new Response<T>(403, "Forbidden", body);
    }

    public static <T> Response<T> notFound(T body) {
        return new Response<T>(404, "Not Found", body);
    }

    public static <T> Response<T> internalServerError(T body) {
        return new Response<T>(500, "Internal Server Error", body);
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }
}
