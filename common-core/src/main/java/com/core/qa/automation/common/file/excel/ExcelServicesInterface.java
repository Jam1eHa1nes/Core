package com.core.qa.automation.common.file.excel;

import java.util.List;

/**
 * Interface for Excel file operations.
 */
public interface ExcelServicesInterface {

    /**
     * Type enum for selecting files by modification time.
     */
    enum Type {
        LATEST, OLDEST
    }

    /**
     * Opens an Excel file specified by directory path and file name.
     *
     * @param directoryFilePath Directory path
     * @param fileName          File name (must include file format .xls or .xlsx)
     */
    void open(String directoryFilePath, String fileName);

    /**
     * Opens the latest or oldest Excel file in the specified directory.
     *
     * @param directoryFilePath Directory path
     * @param type              LATEST or OLDEST
     */
    void open(String directoryFilePath, Type type);

    /**
     * Closes the file and clears all stored data.
     */
    void close();

    /**
     * Prints the currently opened file content to console.
     */
    void print();

    /**
     * Gets cell value using Excel-style reference (e.g., "A1", "B2").
     *
     * @param cell Cell reference (e.g., "E14", "M22")
     * @return The cell value as a String
     */
    String getCellValue(String cell);

    /**
     * Gets cell value using row and column numbers.
     *
     * @param row    Row number (1-based)
     * @param column Column number (1-based, A=1)
     * @return The cell value as a String
     */
    String getCellValue(int row, int column);

    /**
     * Gets cell value using column letter and row number.
     *
     * @param columnLetter Column letter (A-Z, AA-AZ, etc.)
     * @param row          Row number
     * @return The cell value as a String
     */
    String getCellValue(String columnLetter, int row);

    /**
     * Searches for a term and returns its cell location.
     *
     * @param search Search term
     * @return Cell location of first match
     */
    String getLocation(String search);

    /**
     * Gets all values from the file as a list.
     *
     * @return List of all cell values
     */
    List<String> getAllValues();

    /**
     * Gets all values from a specific row.
     *
     * @param rowNumber Row number
     * @return List of values in the row
     */
    List<String> getRow(int rowNumber);

    /**
     * Gets all values from a specific column by number.
     *
     * @param columnNumber Column number (1-based)
     * @return List of values in the column
     */
    List<String> getColumn(int columnNumber);

    /**
     * Gets all values from a specific column by letter.
     *
     * @param columnLetter Column letter
     * @return List of values in the column
     */
    List<String> getColumn(String columnLetter);

    /**
     * Sets a cell value.
     *
     * @param cell  Cell reference (e.g., "A1")
     * @param value Value to set
     */
    void setCellValue(String cell, String value);

    /**
     * Saves changes to the file.
     */
    void save();

    /**
     * Saves the file with a new name.
     *
     * @param fileName New file name
     */
    void saveAs(String fileName);
}

