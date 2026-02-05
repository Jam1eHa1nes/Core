package com.core.qa.automation.common.file.pdf;

import java.util.List;

/**
 * Interface for PDF file operations.
 */
public interface PDFServicesInterface {

    /**
     * Type enum for selecting files by modification time.
     */
    enum Type {
        LATEST, OLDEST
    }

    /**
     * Opens a PDF file by type (latest or oldest) in a directory.
     *
     * @param directoryFilePath Directory path
     * @param type              LATEST or OLDEST
     */
    void open(String directoryFilePath, Type type);

    /**
     * Opens a PDF file by path and filename.
     *
     * @param directoryFilePath Directory path
     * @param fileName          File name
     */
    void open(String directoryFilePath, String fileName);

    /**
     * Gets the number of lines in the document.
     *
     * @return Line count
     */
    int getLineCount();

    /**
     * Gets the page count of the document.
     *
     * @return Page count
     */
    int getPageCount();

    /**
     * Gets the author of the document.
     *
     * @return Author name
     */
    String getAuthor();

    /**
     * Gets the title of the document.
     *
     * @return Document title
     */
    String getTitle();

    /**
     * Gets the creation date of the document.
     *
     * @return Creation date string
     */
    String getCreationDate();

    /**
     * Gets the full text content of the document.
     *
     * @return Document content as string
     */
    String getPrint();

    /**
     * Gets all lines of the document as a list.
     *
     * @return List of lines
     */
    List<String> getLines();

    /**
     * Gets the filename of the document.
     *
     * @return Filename
     */
    String getFileName();

    /**
     * Checks if a keyword is present in the document.
     *
     * @param keyWord Keyword to search for
     * @return true if found, false otherwise
     */
    boolean contentPresent(String keyWord);

    /**
     * Checks if all keywords in a list are present.
     *
     * @param keyWords List of keywords
     * @return true if all found, false otherwise
     */
    boolean checkPresent(List<String> keyWords);

    /**
     * Closes the document and clears data.
     */
    void close();
}

