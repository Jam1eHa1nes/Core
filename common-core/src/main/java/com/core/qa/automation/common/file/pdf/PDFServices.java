package com.core.qa.automation.common.file.pdf;

import com.core.qa.automation.common.exception.AutomationException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of PDFServicesInterface for reading PDF files.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     PDFServices pdf = new PDFServices();
 *     pdf.open("/path/to/dir", "file.pdf");
 *     String content = pdf.getPrint();
 *     boolean hasKeyword = pdf.contentPresent("important");
 *     pdf.close();
 * </pre>
 */
public class PDFServices implements PDFServicesInterface {

    private File currentFile = null;
    private List<String> lines = null;
    private PDDocument inputPdf = null;
    private String contents = null;

    @Override
    public void open(String directoryFilePath, Type type) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file ->
                file.isFile() && file.getName().toLowerCase().endsWith(".pdf"));

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
            throw new AutomationException("No PDF file found in directory: " + directoryFilePath);
        }

        currentFile = chosenFile;
        lines = openAndParse(currentFile);
    }

    @Override
    public void open(String directoryFilePath, String fileName) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(file ->
                file.isFile()
                        && file.getName().toLowerCase().endsWith(".pdf")
                        && file.getName().toLowerCase().contains(fileName.toLowerCase()));

        if (files != null && files.length > 0) {
            currentFile = files[0];
            lines = openAndParse(currentFile);
        } else {
            throw new AutomationException("No matching PDF file found: " + fileName);
        }
    }

    @Override
    public int getLineCount() {
        ensureFileOpen();
        return lines.size();
    }

    @Override
    public int getPageCount() {
        ensureFileOpen();
        return inputPdf.getNumberOfPages();
    }

    @Override
    public String getAuthor() {
        ensureFileOpen();
        String author = inputPdf.getDocumentInformation().getAuthor();
        if (author == null) {
            throw new AutomationException("No Author found in document");
        }
        return author;
    }

    @Override
    public String getTitle() {
        ensureFileOpen();
        String title = inputPdf.getDocumentInformation().getTitle();
        if (title == null) {
            throw new AutomationException("No Title found in document");
        }
        return title;
    }

    @Override
    public String getCreationDate() {
        ensureFileOpen();
        Calendar calendar = inputPdf.getDocumentInformation().getCreationDate();
        if (calendar == null) {
            throw new AutomationException("No Creation Date found in document");
        }
        return calendar.getTime().toString();
    }

    @Override
    public String getPrint() {
        ensureFileOpen();
        return contents;
    }

    @Override
    public List<String> getLines() {
        ensureFileOpen();
        return new ArrayList<>(lines);
    }

    @Override
    public String getFileName() {
        ensureFileOpen();
        return currentFile.getName();
    }

    @Override
    public boolean contentPresent(String keyWord) {
        ensureFileOpen();
        for (String line : lines) {
            if (line.contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPresent(List<String> keyWords) {
        ensureFileOpen();
        List<String> missingKeyWords = new ArrayList<>();

        for (String keyWord : keyWords) {
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(keyWord)) {
                    System.out.println("Keyword \"" + keyWord + "\" found on line " + (i + 1));
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingKeyWords.add(keyWord);
            }
        }

        if (!missingKeyWords.isEmpty()) {
            System.out.println("Missing keywords: " + String.join(", ", missingKeyWords));
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        if (inputPdf != null) {
            try {
                inputPdf.close();
            } catch (IOException e) {
                throw new AutomationException("Error closing PDF document", e);
            }
        }
        currentFile = null;
        lines = null;
        inputPdf = null;
        contents = null;
    }

    private void ensureFileOpen() {
        if (currentFile == null) {
            throw new IllegalStateException("File has not been opened");
        }
    }

    private List<String> openAndParse(File file) {
        try {
            inputPdf = Loader.loadPDF(file);
            PDFTextStripper pdfReader = new PDFTextStripper();
            contents = pdfReader.getText(inputPdf);
            return Arrays.asList(contents.split("\\r?\\n"));
        } catch (IOException e) {
            throw new AutomationException("Error reading PDF file: " + file.getName(), e);
        }
    }
}

