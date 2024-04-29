package com.company;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class configGetter {

    private Properties properties;

    public configGetter(String configFileName){
        properties = new Properties();
        try {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
            if (inputStream == null) {
                throw new FileNotFoundException(configFileName + " not found");
            }
            properties.load(inputStream);
            inputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigProperty(String prop){
        return properties.getProperty(prop);
    }
}


