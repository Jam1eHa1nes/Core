package com.selenium.qa.automation.core.file.pdf;

import com.selenium.qa.automation.core.CPOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.selenium.qa.automation.core.file.pdf.PDFServices.Type.LATEST;
import static com.selenium.qa.automation.core.file.pdf.PDFServices.Type.OLDEST;


public class PDFServices implements PDFServiceInterface {
    public enum Type {
        LATEST,
        OLDEST,
    }

    File currentFile = null;
    List<String> lines = null;
    PDFTextStripper pdfReader = null;
    PDDocument inputPdf = null;
    String contents = null;

    @Override
    public void open(String directoryFilePath, Type type) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".pdf"));
        File chosenFile = null;
        long lastModifiedTime = Long.MIN_VALUE;
        long oldestModifiedTime = Long.MAX_VALUE;
        if (type.equals(LATEST)) {
            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        chosenFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }
            currentFile = chosenFile;
            lines = open(currentFile);
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
            lines = open(currentFile);
        }
    }

    @Override
    public void open(String directoryFilePath, String fileName) {

        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".pdf") && file.getName().toLowerCase().contains(fileName.toLowerCase()));

        File chosenFile = null;

        if (files != null && files.length > 0) {
            currentFile = files[0];
            lines = open(currentFile);
        } else {
            currentFile = null;
            lines = null;
            throw new CPOException("No matching PDF file found.");
        }
    }


    @Override
    public int getLineCount() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return lines.size();


    }

    @Override
    public int getPageCount() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return inputPdf.getNumberOfPages();
    }

    @Override
    public String getAuthor() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        String fileAuthor = inputPdf.getDocumentInformation().getAuthor();
        if (fileAuthor == null) {
            throw new IllegalStateException("No Author found");
        }
        return fileAuthor;
    }

    @Override
    public String getTitle() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        String fileTitle = inputPdf.getDocumentInformation().getTitle();
        if (fileTitle == null) {
            throw new IllegalStateException("No Title found");
        }

        return fileTitle;
    }

    @Override
    public String getCreationDate() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        Calendar calendar = inputPdf.getDocumentInformation().getCreationDate();
        if (calendar == null) {
            throw new IllegalStateException("No Date found");
        }
        return calendar.getTime().toString();
    }

    @Override
    public String getPrint() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return contents;
    }

    @Override
    public List<String> getLines() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return lines;
    }

    @Override
    public String getFileName() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        return currentFile.getName();
    }

    @Override
    public boolean contentPresent(String keyWord) {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        for (String line : lines) {
            if (line.contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPresent(List<String> keyWords) {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }

        List<String> missingKeyWords = new ArrayList<>();

        for (String keyWord : keyWords) {
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(keyWord)) {
                    System.out.println("Keyword \"" + keyWord + "\" found on line " + (i + 1) + ": " + line);
                    found = true;
                }
            }

            if (!found) {
                missingKeyWords.add(keyWord);
            }
        }
        if (!missingKeyWords.isEmpty()) {
            System.out.println("The following keywords were NOT found in the document: " + String.join(", ", missingKeyWords));
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void close() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
        currentFile = null;
        try{
            inputPdf.close();
        }
        catch (IOException ie){
            throw new CPOException(ie.getMessage());
        }
    }


    private List<String> open(File file) {
        try {
            inputPdf = PDDocument.load(file);
            pdfReader = new PDFTextStripper();
            contents = pdfReader.getText(inputPdf);
            String[] arr = contents.split("\r\n");

            for (int i = 0; i < arr.length; i++) {
                arr[i] = StringUtils.normalizeSpace(arr[i]);
            }

            lines = new ArrayList<>(Arrays.asList(arr));
        } catch (FileNotFoundException fnfe) {
            throw new CPOException("No matching PDF file found.");
        } catch (IOException e) {
            throw new CPOException("IOException.");
        }
        ;
        return lines;
    }
}


