package com.haddaji.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpHeadersParserTest {
    private HttpParser parser ;
    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException {
        parser = new HttpParser();
        parseHeadersMethod = HttpParser.class.getDeclaredMethod("parseHeaders", InputStream.class);
        parseHeadersMethod.setAccessible(true);
    }

    @Test
    public void testPrivateMethod() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest() ;
        parseHeadersMethod.invoke(parser, generateSimpleSingleHeaderMessage(),request);
    }

    private InputStream generateSimpleSingleHeaderMessage(){
        String rawData = "Host: localhost:8080\r\n" +
                "\r\n";
        return new java.io.ByteArrayInputStream(rawData.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
    }
}
