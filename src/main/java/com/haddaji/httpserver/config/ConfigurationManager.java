package com.haddaji.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.haddaji.httpserver.util.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static Configuration myCurrentConfiguration;
    private static ConfigurationManager myConfigurationManager;

    private ConfigurationManager() {

    }


    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    //used to load configuration file by the path provided
    public void loadConfigurationFile(String filePath)  {
        FileReader filereader = null;
        try {
            filereader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuilder sb = new StringBuilder();
        int i;
        try {
            while ((i = filereader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("error parsing configuration file", e);
        }
        try {
            myCurrentConfiguration = json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("error parsing configuration file , internal",e);
        }
    }
    //used to get the current configuration
    public Configuration getCurrentConfiguration () {
        if(myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set");
        }
        return myCurrentConfiguration;
    }
}
