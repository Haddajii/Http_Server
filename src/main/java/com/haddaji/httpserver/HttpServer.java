package com.haddaji.httpserver;

import com.haddaji.httpserver.config.Configuration;
import com.haddaji.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//Driver class for the HTTP server
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("server started on port : " + conf.getPort() + " with webroot : " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept() ;

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>Haddaji's Http Server</title></head><body><h1>This page was served using Haddaji's Http Server</h1></body></html>";
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + html;
            outputStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
