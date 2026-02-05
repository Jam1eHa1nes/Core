package com.core.qa.automation.common.utils;

import com.core.qa.automation.common.exception.AutomationException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for file operations, such as reading file contents.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String content = FileUtils.getFileContents("data.txt");
 *     String resourceContent = FileUtils.getResourceContents("test-data/config.json");
 * </pre>
 */
public class FileUtils {

    private FileUtils() {
        // Utility class
    }

    /**
     * Gets the contents of a file from the classpath.
     *
     * @param filePath the path to the file in the classpath
     * @return the file contents as a string
     * @throws AutomationException if the file cannot be read
     */
    public static String getFileContents(String filePath) {
        try {
            return new String(Files.readAllBytes(
                    Paths.get(ClassLoader.getSystemResource(filePath).toURI())),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new AutomationException("Failed to open file: " + filePath, e);
        }
    }

    /**
     * Gets the contents of a resource file from the classpath.
     *
     * @param resourcePath the path to the resource
     * @return the resource contents as a string
     * @throws AutomationException if the resource cannot be read
     */
    public static String getResourceContents(String resourcePath) {
        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new AutomationException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AutomationException("Failed to read resource: " + resourcePath, e);
        }
    }

    /**
     * Reads the contents of a file from the file system.
     *
     * @param path the path to the file
     * @return the file contents as a string
     * @throws AutomationException if the file cannot be read
     */
    public static String readFile(Path path) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AutomationException("Failed to read file: " + path, e);
        }
    }

    /**
     * Reads the contents of a file from the file system.
     *
     * @param filePath the path to the file as a string
     * @return the file contents as a string
     * @throws AutomationException if the file cannot be read
     */
    public static String readFile(String filePath) {
        return readFile(Paths.get(filePath));
    }

    /**
     * Writes content to a file.
     *
     * @param path    the path to the file
     * @param content the content to write
     * @throws AutomationException if the file cannot be written
     */
    public static void writeFile(Path path, String content) {
        try {
            Files.writeString(path, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AutomationException("Failed to write file: " + path, e);
        }
    }

    /**
     * Writes content to a file.
     *
     * @param filePath the path to the file as a string
     * @param content  the content to write
     * @throws AutomationException if the file cannot be written
     */
    public static void writeFile(String filePath, String content) {
        writeFile(Paths.get(filePath), content);
    }

    /**
     * Checks if a file exists.
     *
     * @param filePath the path to check
     * @return true if the file exists, false otherwise
     */
    public static boolean exists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Deletes a file if it exists.
     *
     * @param filePath the path to the file
     * @return true if the file was deleted, false otherwise
     */
    public static boolean delete(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
    }
}

