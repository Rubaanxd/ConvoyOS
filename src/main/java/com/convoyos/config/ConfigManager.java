package com.convoyos.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input =
                ConfigManager.class
                        .getClassLoader()
                        .getResourceAsStream("application.properties")) {

            if (input != null) {
                PROPERTIES.load(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConfigManager() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}