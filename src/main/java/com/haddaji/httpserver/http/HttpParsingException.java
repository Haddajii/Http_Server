package com.haddaji.httpserver.http;

public class HttpParsingException extends Exception {
    private final HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode errorCode, String message) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }
}
