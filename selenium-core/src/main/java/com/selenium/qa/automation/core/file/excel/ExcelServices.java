package com.selenium.qa.automation.core.file.excel;

import com.selenium.qa.automation.core.CPOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.selenium.qa.automation.core.file.excel.ExcelServices.Type.OLDEST;


public class ExcelServices implements ExcelServicesInterface {
    public enum Type{
        LATEST, OLDEST
    }


    Map<String, String> cellData = new HashMap<>();

    File currentFile = null;


    @Override
    public void open(String directoryFilePath, String fileName) {
        this.currentFile = new File(directoryFilePath, fileName);
        if (!currentFile.exists()) {
            System.err.println("File not found: " + currentFile.getAbsolutePath());
            throw new CPOException("File not found");
        }
        storeCellData();
    }

    @Override
    public void open(String directoryFilePath, Type type) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file -> file.isFile() && (file.getName().toLowerCase().endsWith(".xls") || file.getName().toLowerCase().endsWith(".xlsx")));
        File chosenFile = null;
        long lastModifiedTime = Long.MIN_VALUE;
        long oldestModifiedTime = Long.MAX_VALUE;
        if (type.equals(Type.LATEST)) {
            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        chosenFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }
            currentFile = chosenFile;
            storeCellData();
        }
        if (type.equals(OLDEST)) {
            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() < oldestModifiedTime) {
                        chosenFile = file;
                        oldestModifiedTime = file.lastModified();
                    }
                }
            }
            currentFile = chosenFile;
            storeCellData();
        }
    }

    @Override
    public void close() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        currentFile = null;
        cellData = new HashMap<>();
    }

    @Override
    public void print() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        if (cellData.isEmpty()) {
            System.out.print("No cell data found. Make sure to call getCellData() first.");
            return;
        }

        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            System.out.println("Cell: " + entry.getKey() + "\t\tValue: " + entry.getValue());
        }
    }


    @Override
    public List<String> getAllValues() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        if (cellData.isEmpty()) {
            throw new IllegalStateException("No cell data found. Make sure the file was read correctly.");
        }

        List<String> values = new ArrayList<>();

        for (String value : cellData.values()) {
            if (value != null && !value.trim().isEmpty()) {
                values.add(value);
            }
        }

        return values;
    }


    @Override
    public String getCellValue(String cell) {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return cellData.get(cell);
    }


    @Override
    public String getCellValue(int row, int column) {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        column--;

        String cellIndex = columnToLetter(column) + "" + row;

        return cellData.get(cellIndex);
    }

    @Override
    public String getCellValue(String columnLetter, int row) {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        String cellIndex = columnLetter + "" + row;
        return cellData.get(cellIndex);
    }

    @Override
    public String getLocation(String search) {

        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            String cell = entry.getKey();
            String value = entry.getValue();


            if (value != null && value.contains(search)) {
                return cell;
            }
        }
        throw new CPOException("Item not found");
    }


    @Override
    public List<String> getRow(int rowNumber) {
        List<String> rowValues = new ArrayList<>();


        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        for (Map.Entry<String, String> entry : cellData.entrySet()) {
            String key = entry.getKey();
            String col = key.replaceAll("\\d", "");
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
        List<String> columnValues = new ArrayList<>();

        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        columnNumber--;
        String columnLetter = columnToLetter(columnNumber);

        int maxRow = 0;
        for (String cellLocation : cellData.keySet()) {
            if (cellLocation.startsWith(columnLetter)) {
                try {
                    int row = Integer.parseInt(cellLocation.substring(columnLetter.length()));
                    if (row > maxRow) {
                        maxRow = row;
                    }
                } catch (NumberFormatException e) {
                    // skip
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
    public List<String> getColumn(String columnLetter) {
        List<String> columnValues = new ArrayList<>();

        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

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

                }
            }
        }

        for (int row = 1; row <= maxRow; row++) {
            String cellRef = columnLetter + row;
            columnValues.add(cellData.getOrDefault(cellRef, ""));
        }

        return columnValues;
    }


    private Workbook createWorkbook(File file, FileInputStream fis) throws IOException {
        String name = file.getName().toLowerCase();

        if (name.endsWith(".xls")) {
            return new HSSFWorkbook(fis); // Excel 97-2003
        } else if (name.endsWith(".xlsx")) {
            return new XSSFWorkbook(fis); // Excel 2007+
        } else {
            throw new CPOException("Unsupported file format: " + name);
        }
    }


    private void storeCellData() {
        try (FileInputStream fis = new FileInputStream(currentFile);
             Workbook workbook = createWorkbook(currentFile, fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    int rowIndex = cell.getRowIndex();
                    int colIndex = cell.getColumnIndex();
                    String colLetter = columnToLetter(colIndex);
                    String location = colLetter + (rowIndex + 1);

                    String value;
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                String dataFormat = cell.getCellStyle().getDataFormatString().toLowerCase();

                                if (dataFormat.contains("h") && !dataFormat.contains("d")) {
                                    value = new SimpleDateFormat("HH:mm").format(date); // 24-hour time
                                } else if (dataFormat.contains("d") || dataFormat.contains("m") || dataFormat.contains("y")) {
                                    value = new SimpleDateFormat("dd/MM/yyyy").format(date);
                                } else {
                                    value = date.toString();
                                }
                            } else {
                                value = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            value = cell.getCellFormula();
                            break;
                        default:
                            value = "";
                    }


                    cellData.put(location, value);
                }
            }


        } catch (IOException e) {
            throw new CPOException("No matching Excel file found.");
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
