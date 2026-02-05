package com.core.qa.automation.common.utils;

import com.core.qa.automation.common.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading properties from .properties files.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     PropertiesReader reader = new PropertiesReader("src/main/resources/properties");
 *     String value = reader.get("my.key");
 * </pre>
 */
public class PropertiesReader {

    private static final Logger logger = new Logger();
    private final Properties properties;

    /**
     * Creates a PropertiesReader that loads all .properties files from the specified directory.
     *
     * @param propertiesDir the directory containing .properties files
     */
    public PropertiesReader(String propertiesDir) {
        properties = new Properties();
        loadPropertiesFromDirectory(propertiesDir);
    }

    /**
     * Creates a PropertiesReader with default directory "src/main/resources/properties".
     */
    public PropertiesReader() {
        this("src/main/resources/properties");
    }

    private void loadPropertiesFromDirectory(String propertiesDir) {
        File dir = new File(propertiesDir);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.warn("Properties directory does not exist: " + propertiesDir);
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".properties")) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    Properties propsFromFile = new Properties();
                    propsFromFile.load(fis);
                    properties.putAll(propsFromFile);
                } catch (IOException e) {
                    logger.warn("Failed to load properties file: " + file.getName());
                }
            }
        }
    }

    /**
     * Gets a property value by key.
     *
     * @param key the property key
     * @return the property value, or empty string if not found
     * @throws IllegalArgumentException if key is null or empty
     */
    public String get(String key) {
        if (StringUtils.isNullOrEmpty(key)) {
            throw new IllegalArgumentException("getProperty() : Invalid key");
        }
        String value = properties.getProperty(key);
        if (StringUtils.isNullOrEmpty(value)) {
            logger.warn("No entry for key: " + key);
            return "";
        }
        return value;
    }

    /**
     * Gets a property value by key with a default value.
     *
     * @param key          the property key
     * @param defaultValue the default value if key is not found
     * @return the property value, or defaultValue if not found
     */
    public String get(String key, String defaultValue) {
        if (StringUtils.isNullOrEmpty(key)) {
            return defaultValue;
        }
        String value = properties.getProperty(key);
        return StringUtils.isNullOrEmpty(value) ? defaultValue : value;
    }

    /**
     * Gets a property value as an integer.
     *
     * @param key          the property key
     * @param defaultValue the default value if key is not found or not a number
     * @return the property value as integer, or defaultValue
     */
    public int getInt(String key, int defaultValue) {
        String value = get(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets a property value as a boolean.
     *
     * @param key          the property key
     * @param defaultValue the default value if key is not found
     * @return the property value as boolean, or defaultValue
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    /**
     * Checks if a property key exists.
     *
     * @param key the property key
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    /**
     * Gets all loaded properties.
     *
     * @return the Properties object
     */
    public Properties getProperties() {
        return properties;
    }
}

