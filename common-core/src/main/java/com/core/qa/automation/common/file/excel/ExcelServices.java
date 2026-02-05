package com.core.qa.automation.common.file.excel;

import com.core.qa.automation.common.exception.AutomationException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of ExcelServicesInterface for reading and writing Excel files.
 * Supports both .xls (Excel 97-2003) and .xlsx (Excel 2007+) formats.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     ExcelServices excel = new ExcelServices();
 *     excel.open("/path/to/dir", "file.xlsx");
 *     String value = excel.getCellValue("A1");
 *     excel.close();
 * </pre>
 */
public class ExcelServices implements ExcelServicesInterface {

    private Map<String, String> cellData = new HashMap<>();
    private File currentFile = null;
    private Workbook workbook = null;
    private String directoryPath = null;

    @Override
    public void open(String directoryFilePath, String fileName) {
        this.directoryPath = directoryFilePath;
        this.currentFile = new File(directoryFilePath, fileName);
        if (!currentFile.exists()) {
            throw new AutomationException("File not found: " + currentFile.getAbsolutePath());
        }
        storeCellData();
    }

    @Override
    public void open(String directoryFilePath, Type type) {
        this.directoryPath = directoryFilePath;
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file ->
                file.isFile() && (file.getName().toLowerCase().endsWith(".xls")
                        || file.getName().toLowerCase().endsWith(".xlsx")));

        File chosenFile = null;

        if (type == Type.LATEST) {
            long lastModifiedTime = Long.MIN_VALUE;
            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        chosenFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }
        } else if (type == Type.OLDEST) {
            long oldestModifiedTime = Long.MAX_VALUE;
            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() < oldestModifiedTime) {
                        chosenFile = file;
                        oldestModifiedTime = file.lastModified();
                    }
                }
            }
        }

        if (chosenFile == null) {
            throw new AutomationException("No Excel file found in directory: " + directoryFilePath);
        }

        currentFile = chosenFile;
        storeCellData();
    }

    @Override
    public void close() {
        ensureFileOpen();
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            throw new AutomationException("Error closing workbook", e);
        }
        currentFile = null;
        workbook = null;
        cellData = new HashMap<>();
    }

    @Override
    public void print() {
        ensureFileOpen();
        if (cellData.isEmpty()) {
            System.out.println("No cell data found.");
            return;
        }
        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            System.out.println("Cell: " + entry.getKey() + "\t\tValue: " + entry.getValue());
        }
    }

    @Override
    public String getCellValue(String cell) {
        ensureFileOpen();
        return cellData.get(cell);
    }

    @Override
    public String getCellValue(int row, int column) {
        ensureFileOpen();
        String cellIndex = columnToLetter(column - 1) + row;
        return cellData.get(cellIndex);
    }

    @Override
    public String getCellValue(String columnLetter, int row) {
        ensureFileOpen();
        String cellIndex = columnLetter.toUpperCase() + row;
        return cellData.get(cellIndex);
    }

    @Override
    public String getLocation(String search) {
        ensureFileOpen();
        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            if (entry.getValue() != null && entry.getValue().contains(search)) {
                return entry.getKey();
            }
        }
        throw new AutomationException("Search term not found: " + search);
    }

    @Override
    public List<String> getAllValues() {
        ensureFileOpen();
        List<String> values = new ArrayList<>();
        for (String value : cellData.values()) {
            if (value != null && !value.trim().isEmpty()) {
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public List<String> getRow(int rowNumber) {
        ensureFileOpen();
        List<String> rowValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            String key = entry.getKey();
            int row = Integer.parseInt(key.replaceAll("[A-Z]", ""));
            if (row == rowNumber) {
                String value = entry.getValue();
                if (value != null && !value.trim().isEmpty()) {
                    rowValues.add(value);
                }
            }
        }
        return rowValues;
    }

    @Override
    public List<String> getColumn(int columnNumber) {
        return getColumn(columnToLetter(columnNumber - 1));
    }

    @Override
    public List<String> getColumn(String columnLetter) {
        ensureFileOpen();
        List<String> columnValues = new ArrayList<>();
        columnLetter = columnLetter.toUpperCase();

        int maxRow = 0;
        for (String cellLocation : cellData.keySet()) {
            if (cellLocation.startsWith(columnLetter)) {
                try {
                    int row = Integer.parseInt(cellLocation.substring(columnLetter.length()));
                    if (row > maxRow) {
                        maxRow = row;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid cell references
                }
            }
        }

        for (int row = 1; row <= maxRow; row++) {
            String cellRef = columnLetter + row;
            columnValues.add(cellData.getOrDefault(cellRef, ""));
        }
        return columnValues;
    }

    @Override
    public void setCellValue(String cell, String value) {
        ensureFileOpen();
        cellData.put(cell.toUpperCase(), value);

        // Also update the workbook
        String columnLetter = cell.replaceAll("\\d", "").toUpperCase();
        int rowNum = Integer.parseInt(cell.replaceAll("[A-Z]", ""));
        int colNum = letterToColumn(columnLetter) - 1;

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowNum - 1);
        if (row == null) {
            row = sheet.createRow(rowNum - 1);
        }
        Cell excelCell = row.getCell(colNum);
        if (excelCell == null) {
            excelCell = row.createCell(colNum);
        }
        excelCell.setCellValue(value);
    }

    @Override
    public void save() {
        ensureFileOpen();
        try (FileOutputStream fos = new FileOutputStream(currentFile)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new AutomationException("Error saving file", e);
        }
    }

    @Override
    public void saveAs(String fileName) {
        ensureFileOpen();
        File newFile = new File(directoryPath, fileName);
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new AutomationException("Error saving file as " + fileName, e);
        }
    }

    private void ensureFileOpen() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
    }

    private Workbook createWorkbook(File file, FileInputStream fis) throws IOException {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".xls")) {
            return new HSSFWorkbook(fis);
        } else if (name.endsWith(".xlsx")) {
            return new XSSFWorkbook(fis);
        } else {
            throw new AutomationException("Unsupported file format: " + name);
        }
    }

    private void storeCellData() {
        try (FileInputStream fis = new FileInputStream(currentFile)) {
            workbook = createWorkbook(currentFile, fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    int rowIndex = cell.getRowIndex();
                    int colIndex = cell.getColumnIndex();
                    String colLetter = columnToLetter(colIndex);
                    String location = colLetter + (rowIndex + 1);

                    String value = getCellValueAsString(cell);
                    cellData.put(location, value);
                }
            }
        } catch (IOException e) {
            throw new AutomationException("Error reading Excel file", e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    String dataFormat = cell.getCellStyle().getDataFormatString().toLowerCase();
                    if (dataFormat.contains("h") && !dataFormat.contains("d")) {
                        return new SimpleDateFormat("HH:mm").format(date);
                    } else if (dataFormat.contains("d") || dataFormat.contains("m") || dataFormat.contains("y")) {
                        return new SimpleDateFormat("dd/MM/yyyy").format(date);
                    } else {
                        return date.toString();
                    }
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static String columnToLetter(int columnIndex) {
        StringBuilder columnLetter = new StringBuilder();
        while (columnIndex >= 0) {
            columnLetter.insert(0, (char) ('A' + (columnIndex % 26)));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnLetter.toString();
    }

    private static int letterToColumn(String columnLetter) {
        int result = 0;
        for (int i = 0; i < columnLetter.length(); i++) {
            char ch = columnLetter.charAt(i);
            result *= 26;
            result += (ch - 'A' + 1);
        }
        return result;
    }
}

