package com.core.qa.automation.common;

/**
 * Interface for providing storage functionality.
 * This allows the HTTP services to retrieve stored values (like credentials)
 * from the automation framework (either Selenium or Playwright).
 */
public interface StorageProvider {

    /**
     * Retrieves a stored value by key.
     *
     * @param key the key to retrieve
     * @return the stored value, or null if not found
     */
    String retrieve(String key);

    /**
     * Stores a value with the specified key.
     *
     * @param key   the key to store
     * @param value the value to store
     */
    void store(String key, String value);
}

