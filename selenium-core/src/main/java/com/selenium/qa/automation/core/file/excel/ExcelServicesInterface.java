package com.selenium.qa.automation.core.file.excel;

import java.util.List;

public interface ExcelServicesInterface {
    /**
     * Open Excel File specified by directory path and file name (must included file format e.g. .xls or .xlsx).
     * @param directoryFilePath Directory path
     * @param fileName String file name (must include file format)
     */
    public void open(String directoryFilePath, String fileName);


    /**
     * Open Excel file specified by directory path and type (Latest or Oldest)
     * This will select the latest/oldest edited .xls or .xlsx file in stated directory
     * @param directoryFilePath
     * @param type
     */
    public void open(String directoryFilePath, ExcelServices.Type type);


    /**
     * Closes file, clears all stored data
     */
    public void close();


    /**
     * Prints currently opened file to commandline.
     * 'open()' has to be ran first
     */
    public void print();


    /**
     * Gets cell value on the opened Excel file. Give format letter and number like used in Excel. For example, 'E14'.
     * 'open()' has to be ran first
     * @param cell expects letter and row number as displayed in Excel. For example, 'R21' or 'M22'.
     * @return This will return the value stored in the cell as a String value.
     */
    public String getCellValue(String cell);


    /**
     * Gets cell value on the opened Excel file via Excel co-ordinates. Row and Column as integers.
     * @param row This will be the row number shown on the left side of an Excel worksheet.
     * @param column This will be the column number (displayed as letter) A is 1, F is 6. Function requires Integer as input.
     * @return This will return the value stored in the cell as a String value.
     */
    public String getCellValue(int row, int column);


    /**
     * Gets cell value on the opened Excel file via Excel co-ordinates using the row number (int) and column letter.
     * @param columnLetter String value as displayed in Excel worksheet. Column A to Z then AA - AZ
     * @param row Row number
     * @return This will return the value stored in the cell as a String value.
     */
    public String getCellValue(String columnLetter, int row);


    /**
     * Method will search the Excel file for given search term and return the location of the found result.
     * @param search String search term
     * @return Location of first found match of search term.
     */
    public String getLocation(String search);


    /**
     * Similar to print method, however, returns in a list so user can interact.
     * @return List of all results found in file
     */
    public List<String> getAllValues();


    /**
     * Returns all values stored in specific row as a list.
     * @param rowNumber The row number displayed on Excel file on the left hand side
     * @return A List of all values found on row as a List
     */
    public List<String> getRow(int rowNumber);


    /**
     * Returns all values stored in specific column as a list. Using Integer rather than letter - A=1 Z=26
     * @param columnNumber Integer of column
     * @return List of all values stored in column
     */
    public List<String> getColumn(int columnNumber);


    /**
     * Returns all values stored in specific column as a list. Rollover after Z follows AA - AZ - BA - BZ - ZZ
     * @param columnLetter Letter of column
     * @return List of all values stored in column
     */
    public List<String> getColumn(String columnLetter);

}
