package com.haddaji.httpserver.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

public class WebRootHandler {
    private File webRoot ;

    public WebRootHandler(String webRootPath) throws WebRootNotFoundException {
        this.webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException("Web root directory not found: " + webRootPath);
        }

    }

    private boolean checkIfEndsWithSlash(String path){
        return path.endsWith("/");
    }

    private boolean checkIfiProvidedPathExists(String path){
        File file = new File(webRoot, path);
        if(!file.exists()){
            return false;
        }

        try {
            if(file.getCanonicalPath().startsWith(webRoot.getCanonicalPath())){
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public String getFileMimeType(String relativePath) throws FileNotFoundException {
        if(checkIfEndsWithSlash(relativePath)){
            relativePath = "index.html";
        }
        if(!checkIfiProvidedPathExists(relativePath)){
            throw new FileNotFoundException("File not found: " + relativePath);
        }
        File file = new File(webRoot, relativePath);
        String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
        if(mimeType == null){
            return "application/octet-stream";
        }
        return mimeType;
    }

    public byte[] getFileByteArrayData(String relativePath) throws FileNotFoundException {
        if(checkIfEndsWithSlash(relativePath)){
            relativePath = "index.html";
        }
        if(!checkIfiProvidedPathExists(relativePath)){
            throw new FileNotFoundException("File not found: " + relativePath);
        }
        File file = new File(webRoot, relativePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        try{
            fileInputStream.read(fileBytes);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBytes;
    }
}
