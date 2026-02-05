package com.selenium.qa.automation.core.file.pdf;

import com.selenium.qa.automation.core.CPOException;

import java.util.List;

public interface PDFServiceInterface {
    /**
     * Open PDF file specified by path and type
     * @param directoryFilePath Directory path
     * @param type One of LATEST or OLDEST
     * @throws CPOException if file not found
     */
    public void open(String directoryFilePath, PDFServices.Type type);


    /**
     * Open PDF file specified by path and filename
     * @param directoryFilePath Directory path
     * @param fileName Filename
     */
    public void open(String directoryFilePath, String fileName);


    /**
     * Get number of lines in the PDF document
     * Open() must be ran first to use this or will throw exception
     * @return number of lines in the document
     */
    public int getLineCount();


    /**
     * Get page count of the PDF document
     * Open() must be ran first to use this or will throw exception
     * @return number of pages in the document
     */
    public int getPageCount();


    /**
     * Gets Author of the document
     * Will throw exception if Author does not exist
     * Open() must be ran first to use this or will throw exception
     * @return name of the Author on the document
     */
    public String getAuthor();


    /**
     * Gets Title of the document
     * Will throw exception if Title does not exist
     * Open() must be ran first to use this or will throw exception
     * @return name of the Title on the document
     */
    public String getTitle();


    /**
     * Gets Creation Date on the document
     * Open() must be ran first to use this or will throw exception
     * @return creation date of the document
     */
    public String getCreationDate();


    /**
     * Prints all of document as a string
     * Open() must be ran first to use this or will throw exception
     * @return a string of all content in the document
     */
    public String getPrint();


    /**
     * Gets all contents of the file and stores each line to a list
     * Open() must be ran first to use this or will throw exception
     * @return an array list of strings of each lines of the document
     */
    public List<String> getLines();


    /**
     * Gets the filename of the document
     * Open() must be ran first to use this or will throw exception
     * @return a string containing the filename of the document
     */
    public String getFileName();


    /**
     * Checks the document for a specific keyword and returns if the word is present or not
     * Open() must be ran first to use this or will throw exception
     * @param keyWord the word that you want to check against
     * @return a boolean. true if the document has contains the word, false if not.
     */
    public boolean contentPresent(String keyWord);


    /**
     * Checks the document against a specific list of keywords. Will return false if one of the words is not present.
     * Prints to console all found words and what lines it is on. Also lists the words not found in the document in the
     * log.
     * Open() must be ran first to use this or will throw exception
     * @param keyWords list of words to be checked against the document
     * @return true if all words have been found. False if one word hasn't been found
     */
    public boolean checkPresent(List<String> keyWords);


    /**
     * Closes the opened document
     * Open() must be ran first to use this or will throw exception
     */
    public void close();
}
