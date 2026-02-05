package junit;

import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.file.pdf.PDFServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


public class JPDFServices {
    PDFServices services = new PDFServices();
    String downloads = System.getProperty("user.dir") + "\\src\\test\\java\\junit\\";

    @Test
    @DisplayName("Testing Open Method using filename using negative filename input")
    void testFileIsNotPresent() {
        assertThatThrownBy(() -> services.open(downloads, "image"))
                .isInstanceOf(CPOException.class)
                .hasMessageContaining("No matching PDF file found.");
    }


    @Test
    @DisplayName("Testing Open Method using filename using positive input")
    void testFileIsPresent() {
        assertThatCode(() -> services.open(downloads, "testFilePDF"))
                .doesNotThrowAnyException();
        services.close();
    }

    @Test
    @DisplayName("Testing Get Line method & checking for exception is file is not open")
    void testGetsLineCount() {

        services.open(downloads, "testFilePDF");

        assertThatCode(() -> services.getLineCount())
                .doesNotThrowAnyException();

        services.close();

        assertThatThrownBy(() -> services.getLineCount())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Page Count method & checking for exception is file is not open")
    void testGetsPageCount() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getPageCount())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getPageCount())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Author method & checking for exception is file is not open")
    void testGetsAuthor() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getAuthor())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getAuthor())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Title method & checking for exception is file is not open")
    void testGetTitle() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getTitle())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getTitle())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Creation Date method & checking for exception is file is not open")
    void testGetCreationDate() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getCreationDate())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getCreationDate())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Print method & checking for exception is file is not open")
    void testGetPrint() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getPrint())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getPrint())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Lines method & checking for exception is file is not open")
    void testGetLines() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getLines())
                .doesNotThrowAnyException();
        assertFalse(() -> services.getLines().isEmpty());
        services.close();
        assertThatThrownBy(() -> services.getLines())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Get Filename method & checking for exception is file is not open")
    void testGetFileName() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.getFileName())
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getFileName())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Content Present method & checking for exception is file is not open")
    void testContentPresent() {
        String keyWord = "testing";
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.contentPresent(keyWord))
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getFileName())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing Gets Line method while checking in the return is empty checking for exception is file is not open")
    void testCheckPresent() {
        List<String> keyWords = Arrays.asList("Audit Trail", "Donkey", "Apps", "Rules", "Hello World!", "Notes", "Cat", "Documents", "Links");
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.checkPresent(keyWords))
                .doesNotThrowAnyException();
        services.close();
        assertThatThrownBy(() -> services.getFileName())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Testing the Close method & checking for exception is file is not open")
    void testClose() {
        services.open(downloads, "testFilePDF");
        assertThatCode(() -> services.close())
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> services.close())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }
}
