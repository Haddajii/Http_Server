package com.haddaji.httpserver.core;

import com.haddaji.httpserver.core.io.WebRootHandler;
import com.haddaji.httpserver.core.io.WebRootNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            WebRootHandler webRootHandler = new WebRootHandler("WebRoot");


            String path = "/";

            try {
                byte[] fileData = webRootHandler.getFileByteArrayData(path);
                String mimeType = webRootHandler.getFileMimeType(path);

                String headers = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Content-Length: " + fileData.length + "\r\n" +
                        "\r\n";

                outputStream.write(headers.getBytes(StandardCharsets.US_ASCII));
                outputStream.write(fileData);

                LOGGER.info("Served file for path: {}", path);
            } catch (FileNotFoundException e) {
                String notFound = "<html><body><h1>404 - Not Found</h1></body></html>";
                String response = "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + notFound.length() + "\r\n" +
                        "\r\n" +
                        notFound;
                outputStream.write(response.getBytes(StandardCharsets.US_ASCII));

                LOGGER.warn("File not found for path: {}", path);
            }

        } catch (IOException e) {
            LOGGER.error("Error handling connection", e);
        } catch (WebRootNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                LOGGER.error("Error closing connection", e);
            }
        }
    }
}
