package junit;

import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.file.excel.ExcelServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;



public class JExcelServices {
    ExcelServices services = new ExcelServices();
    String downloads = System.getProperty("user.dir") + "\\src\\test\\java\\junit\\";
    List<String> expectedList = null;
    int row = 0;
    int column = 0;
    String columnLetter = null;

    void openDocument(){
        services.open(downloads, "testFile.xlsx");
    }

    @Test
    @DisplayName("Negative: Opening the file with wrong file type")
    void testFileIsNotPresentFileType(){
        assertThatThrownBy(() -> services.open(downloads, "testFilePDF.pdf"))
                .isInstanceOf(CPOException.class);
    }

    @Test
    @DisplayName("Negative: Opening the file with wrong file name")
    void testFileIsNotPresentFileName(){
        assertThatThrownBy(() -> services.open(downloads, "testingFile.xlsx"))
                .isInstanceOf(CPOException.class);
    }


    @Test
    @DisplayName("Opening the correct file .xls")
    void testFileIsPresentOldFile() {
        assertThatCode(() -> services.open(downloads, "testFile.xlsx"))
                .doesNotThrowAnyException();
        services.close();
    }

    @Test
    @DisplayName("Opening the correct file .xlsx")
    void testFileIsPresentNewFile(){
        assertThatCode(() -> services.open(downloads, "testFile.xlsx"))
                .doesNotThrowAnyException();
        services.close();
    }

    @Test
    @DisplayName("Using the print method")
    void testPrintMethod(){
        openDocument();
        assertThatCode(() -> services.print())
                .doesNotThrowAnyException();

        services.close();

        assertThatThrownBy(() -> services.print())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Using the getCellValue Method with String input")
    void testGetCellValueString(){
        String checkCellValue = "H7";
        openDocument();
        assertEquals("TEST", services.getCellValue(checkCellValue));

        services.close();

        assertThatThrownBy(() -> services.getCellValue(checkCellValue))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Using getCellValue method with Row & Column input")
    void testGetCellValueCoOrds(){
        // E10 is 5, 10
        row = 10;
        column = 5;

        openDocument();
        assertEquals("This is an Excel document", services.getCellValue(row, column));

        services.close();

        assertThatThrownBy(() -> services.getCellValue(row, column))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");

    }

    @Test
    @DisplayName("Using getCellValue method with Column Letter and Row Number")
    void testGetCellValueStringAndInt(){
        row = 10;
        columnLetter = "E";

        openDocument();

        assertEquals("This is an Excel document", services.getCellValue(columnLetter, row));

        services.close();

        assertThatThrownBy(() -> services.getCellValue(columnLetter, row))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");

    }

    @Test
    @DisplayName("Using getLocation method to test method")
    void testGetLocation(){
        String searchTerm = "TEST";
        String expectedResult = "H7";

        openDocument();
        assertEquals(expectedResult, services.getLocation(searchTerm));

        services.close();


    }


    @Test
    @DisplayName("Negative: Using getLocation method with negative input")
    void testGetLocationNegative(){
        String searchTerm = "This is not an excel document";

        openDocument();

        assertThatThrownBy(() -> services.getLocation(searchTerm))
                .isInstanceOf(CPOException.class)
                .hasMessageContaining("Item not found");

        services.close();
    }


    @Test
    @DisplayName("Using getValues method")
    void testGetValues(){
        expectedList = Arrays.asList("hello world", "2.0", "M1", "M19", "1.0", "This is an Excel document", "4.0", "3.0", "F14", "!M19", "C2", "TEST");

        openDocument();

        assertEquals(expectedList, services.getAllValues());

        services.close();
    }

    @Test
    @DisplayName("Negative: using getValues method & checking negative")
    void testGetValuesNegative(){
        expectedList = null;

        openDocument();

        assertNotEquals(expectedList, services.getAllValues());

        services.close();
    }



    @Test
    @DisplayName("Using getRow method")
    void testGetRow(){
        row = 7;
        expectedList = Arrays.asList("4.0", "TEST");


        openDocument();
        assertEquals(expectedList, services.getRow(row));

        services.close();

        assertThatThrownBy(() -> services.getRow(row))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Using getColumn method with Integer input")
    void testGetColumnInt(){
        column = 1;
        expectedList = Arrays.asList("hello world", "", "", "", "", "1.0", "4.0", "", "", "", "", "2.0", "", "", "", "3.0", "", "", "!M19");

        openDocument();

        assertEquals(expectedList, services.getColumn(column));

        services.close();

        assertThatThrownBy(() -> services.getColumn(column))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }


    @Test
    @DisplayName("Using getColumn method with String input")
    void testGetColumnString(){
        columnLetter = "A";
        expectedList = Arrays.asList("hello world", "", "", "", "", "1.0", "4.0", "", "", "", "", "2.0", "", "", "", "3.0", "", "", "!M19");

        openDocument();

        assertEquals(expectedList, services.getColumn(columnLetter));

        services.close();

        assertThatThrownBy(() -> services.getColumn(columnLetter))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("File has not been opened");
    }

    @Test
    @DisplayName("Negative: Using getColumn using String Input but wrong string to check with")
    void testGetColumnStringNegative(){
        columnLetter = "C";
        expectedList = Arrays.asList("One", "Two", "Three");

        openDocument();

        assertNotEquals(expectedList, services.getColumn(columnLetter));

        services.close();
    }
}
