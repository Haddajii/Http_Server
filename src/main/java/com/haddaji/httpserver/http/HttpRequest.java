package com.haddaji.httpserver.http;

public class HttpRequest extends HttpMessage{
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest(){
    }

    public HttpMethod getMethod() {
        return method;
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
}
