package com.haddaji.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.haddaji.httpserver.util.json;

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
        final String content;
        try {
            content = java.nio.file.Files.readString(java.nio.file.Path.of(filePath));
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        final JsonNode conf;
        try {
            conf = json.parse(content);
        } catch (IOException e) {
            throw new HttpConfigurationException("error parsing configuration file", e);
        }

        try {
            myCurrentConfiguration = json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("error parsing configuration file, internal", e);
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
