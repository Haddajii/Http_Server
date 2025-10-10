package com.haddaji.httpserver.http;

public class HttpRequest extends HttpMessage{
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleHttpVersion;

    HttpRequest(){
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }
    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    void setMethod(String method) throws HttpParsingException {
        for(HttpMethod m : HttpMethod.values()){
            if(m.name().equals(method)){
                this.method = m;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED , "Method Not Implemented");
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.isEmpty()){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR, "Bad Request");
        }
        this.requestTarget = requestTarget;
    }

    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if(this.bestCompatibleHttpVersion == null){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED , "HTTP Version Not Supported");
        }
    }
}

