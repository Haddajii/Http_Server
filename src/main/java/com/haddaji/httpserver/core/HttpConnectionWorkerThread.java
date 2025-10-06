package com.haddaji.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    InputStream inputStream = null;
    OutputStream outputStream = null;

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


            String html = "<html><head><title>Haddaji's Http Server</title></head><body><h1>This page was served using Haddaji's Http Server</h1></body></html>";
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + html;
            outputStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));


            LOGGER.info("connection processing finished ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
                if(socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                LOGGER.error("error closing connection", e);
            }
        }
    }
}
