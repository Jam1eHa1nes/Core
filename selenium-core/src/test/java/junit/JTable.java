package junit;


import com.core.qa.automation.common.utils.ListUtils;
import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Table;
import com.selenium.qa.automation.core.TableHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;

import static com.selenium.qa.automation.core.Enums.Direction.BACK;
import static com.selenium.qa.automation.core.Enums.Index.*;
import static com.selenium.qa.automation.core.Enums.Tag.H1;
import static com.selenium.qa.automation.core.Enums.Tag.TITLE;
import static com.selenium.qa.automation.core.locators.TargetFactory.id;
import static com.selenium.qa.automation.core.locators.TargetFactory.tagWithText;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JTable extends TableHandler {
    CommonPageObject cpo = new CommonPageObject();

    // Open browser
    @BeforeAll
    public void setUp() {
        setUp(cpo);
    }

    @Test
    @DisplayName("Get first table")
    public void testTable1() {
        Table table = new Table();
        testGrid(table);
    }


    @Test
    @DisplayName("Get first table by Index")
    public void testTableIndex() {
        Table table = new Table(1);
        testGrid(table);
    }

    @Test
    @DisplayName("Get first table by Index")
    public void testTable2() {
        Table table = new Table();
        testGrid(table);
    }

    @Test
    @DisplayName("Get table by Target")
    public void testTable3() {
        Table table = new Table(id("capitals"));
        testGrid(table);
    }

    @Test
    @DisplayName("Access by row List index and cell indexes")
    public void testTable4() {
        Table table = new Table(id("seasons"));
        List<String> row;
        assertFalse(table.getHeaders().isEmpty());
        assertFalse(table.getGrid().isEmpty());
        assertEquals(table.getHeaders().size(), 3);
        assertEquals(table.getRows(), 4);
        assertEquals(table.getColumns(), 3);
        List<String> headers = table.getHeaders();
        assertEquals(headers.get(0),"Season");
        assertEquals(headers.get(1),"Climate");
        assertEquals(headers.get(2),"Activity");
        row = table.getRow(FIRST);
        assertEquals(row.get(0),"Spring");
        assertEquals(row.get(1),"Mild");
        assertEquals(row.get(2),"Plant seeds");
        row = table.getRow(SECOND);
        assertEquals(row.get(0),"Summer");
        assertEquals(row.get(1),"Hot");
        assertEquals(row.get(2),"Admire Flowers");
        row = table.getRow(THIRD);
        assertEquals(row.get(0),"Autumn");
        assertEquals(row.get(1),"Moderate");
        assertEquals(row.get(2),"Sweep Leaves");
        row = table.getRow(FOURTH);
        assertEquals(row.get(0),"Winter");
        assertEquals(row.get(1),"Cold");
        assertEquals(row.get(2),"Forget Garden");
        row = table.getRow(1);
        assertEquals(row.get(0),"Spring");
        assertEquals(row.get(1),"Mild");
        assertEquals(row.get(2),"Plant seeds");
        row = table.getRow(2);
        assertEquals(row.get(0),"Summer");
        assertEquals(row.get(1),"Hot");
        assertEquals(row.get(2),"Admire Flowers");
        row = table.getRow(3);
        assertEquals(row.get(0),"Autumn");
        assertEquals(row.get(1),"Moderate");
        assertEquals(row.get(2),"Sweep Leaves");
        row = table.getRow(4);
        assertEquals(row.get(0),"Winter");
        assertEquals(row.get(1),"Cold");
        assertEquals(row.get(2),"Forget Garden");

        // CELLS

        assertEquals(table.getCell(1, 1 ),"Spring");
        assertEquals(table.getCell(1, 2),"Mild");
        assertEquals(table.getCell(1,3),"Plant seeds");

        assertEquals(table.getCell(2, FIRST),"Summer");
        assertEquals(table.getCell(2, SECOND),"Hot");
        assertEquals(table.getCell(2, THIRD),"Admire Flowers");

        assertEquals(table.getCell(THIRD, 1),"Autumn");
        assertEquals(table.getCell(THIRD, 2),"Moderate");
        assertEquals(table.getCell(THIRD, 3),"Sweep Leaves");

        assertEquals(table.getCell(FOURTH,FIRST),"Winter");
        assertEquals(table.getCell(FOURTH,SECOND),"Cold");
        assertEquals(table.getCell(FOURTH,THIRD ),"Forget Garden");

    }

    @Test
    @DisplayName("Access by column List index and cell indexes")
    public void testTable5() {
        Table table = new Table();
        List<String> col;

        col = table.getColumn(1);
        assertEquals(col.get(0), "UK");
        assertEquals(col.get(1), "France");
        assertEquals(col.get(2), "Italy");
        col = table.getColumn(2);
        assertEquals(col.get(0), "London");
        assertEquals(col.get(1), "Paris");
        assertEquals(col.get(2), "Rome");
        col = table.getColumn(3);
        assertEquals(col.get(0), "9");
        assertEquals(col.get(1), "2");
        assertEquals(col.get(2), "2.7");
        col = table.getColumn(4);
        assertEquals(col.get(0), "English");
        assertEquals(col.get(1), "French");
        assertEquals(col.get(2), "Italian");

        col = table.getColumn(FIRST);
        assertEquals(col.get(0), "UK");
        assertEquals(col.get(1), "France");
        assertEquals(col.get(2), "Italy");
        col = table.getColumn(2);
        assertEquals(col.get(0), "London");
        assertEquals(col.get(1), "Paris");
        assertEquals(col.get(2), "Rome");
        col = table.getColumn(3);
        assertEquals(col.get(0), "9");
        assertEquals(col.get(1), "2");
        assertEquals(col.get(2), "2.7");
        col = table.getColumn(4);
        assertEquals(col.get(0), "English");
        assertEquals(col.get(1), "French");
        assertEquals(col.get(2), "Italian");

        // Column by String
        col = table.getColumn("Country");
        assertEquals(col.get(0), "UK");
        assertEquals(col.get(1), "France");
        assertEquals(col.get(2), "Italy");
        col = table.getColumn("Capital");
        assertEquals(col.get(0), "London");
        assertEquals(col.get(1), "Paris");
        assertEquals(col.get(2), "Rome");
        col = table.getColumn("Population (m)");
        assertEquals(col.get(0), "9");
        assertEquals(col.get(1), "2");
        assertEquals(col.get(2), "2.7");
        col = table.getColumn("Language");
        assertEquals(col.get(0), "English");
        assertEquals(col.get(1), "French");
        assertEquals(col.get(2), "Italian");
    }

    @Test
    @DisplayName("Table has no headers")
    public void testTable7() {
        Table table = new Table(id("noheaders"));
        assertTrue(table.getHeaders().isEmpty());
        assertThrows(CPOException.class, () -> table.getHeader(1));
        assertThrows(CPOException.class, () -> table.getHeader(FIRST));
    }

    @Test
    @DisplayName("Headers")
    public void testTable8() {
        Table table = new Table(id("seasons"));
        assertFalse(table.getHeaders().isEmpty());
        assertEquals(table.getHeaders().size(), 3);
        assertEquals(table.getRows(), 4);
        List<String> headers = table.getHeaders();
        assertEquals(headers.get(0), "Season");
        assertEquals(headers.get(1), "Climate");
        assertEquals(headers.get(2), "Activity");
        assertEquals(table.getHeader(1), "Season");
        assertEquals(table.getHeader(2), "Climate");
        assertEquals(table.getHeader(3), "Activity");
        assertEquals(table.getHeader(FIRST), "Season");
        assertEquals(table.getHeader(SECOND), "Climate");
        assertEquals(table.getHeader(THIRD), "Activity");
    }

    @Test
    @DisplayName("table - no content")
    public void testTable9() {
        Table table = new Table(id("headersnocontent"));
        assertTrue(table.getGrid().isEmpty());
        CPOException exception = assertThrows(CPOException.class, () -> table.getCell(1, 1), "Cell 1");
        assertEquals("Cell does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Cell Hyperlinks")
    public void testTable11() {
        Table table = new Table(id("seasons"));

        table.clickCell(1, 1);
        cpo.focus(tagWithText(TITLE, "Spring - Wikipedia"));
        cpo.urlContains("wikipedia.org");
        cpo.go(BACK);
        table.reload();
        cpo.focus(tagWithText(H1, "Test Tables"));

        table.clickCell(SECOND, 1);
        cpo.focus(tagWithText(TITLE, "Summer - Wikipedia"));
        cpo.urlContains("wikipedia.org");
        cpo.go(BACK);
        table.clear();
        table.reload();
        cpo.focus(tagWithText(H1, "Test Tables"));

        table.clickCell(3, FIRST);
        cpo.focus(tagWithText(TITLE, "Autumn - Wikipedia"));
        cpo.urlContains("wikipedia.org");
        cpo.go(BACK);
        table.reload();
        cpo.focus(tagWithText(H1, "Test Tables"));

        table.clickCell(FOURTH, FIRST);
        cpo.focus(tagWithText(TITLE, "Winter - Wikipedia"));
        cpo.urlContains("wikipedia.org");
        setUp(cpo);
    }

    @Test
    @DisplayName("Parameters are zero")
    public void testTable10() {
        CPOException exception;
        Table table;
        exception = assertThrows(CPOException.class, () -> new Table(0));
        assertEquals("Invalid Index", exception.getMessage());
        table = new Table(1);
        exception = assertThrows(CPOException.class, () -> table.getCell(0, 1));
        assertEquals("Invalid Index", exception.getMessage());
        exception = assertThrows(CPOException.class, () -> table.getCell(1, 0));
        assertEquals("Invalid Index", exception.getMessage());
        exception = assertThrows(CPOException.class, () -> table.getCell(0, 0));
        assertEquals("Invalid Index", exception.getMessage());
    }

    @Test
    @DisplayName("Table reload")
    public void testTable12() {
        Table table = new Table();
        testGrid(table);
        table.reload();
        testGrid(table);

        table = new Table(FIRST);
        testGrid(table);
        table.reload();
        testGrid(table);

        table = new Table(id("capitals"));
        testGrid(table);
        testGrid(table);
    }
    @Test
    @DisplayName("Aggregates")
    public void testTable14() {
        Table table = new Table();
        assertEquals(ListUtils.aggregate(table.getColumn(3)), 13.7);
        assertEquals(ListUtils.aggregate(table.getColumn(THIRD)), 13.7);
        assertEquals(ListUtils.aggregate(table.getColumn("Population (m)")), 13.7);
        table = new Table(id("turnover"));
        assertEquals(ListUtils.aggregate(table.getColumn(2)), 6000);
        assertEquals(ListUtils.aggregate(table.getColumn(SECOND)), 6000);
        assertEquals(ListUtils.aggregate(table.getColumn("Q1")), 6000);

        assertEquals(ListUtils.aggregate(table.getColumn(1)), 6072);
        assertEquals(ListUtils.aggregate(table.getColumn(SECOND)), 6000);

        assertEquals(ListUtils.aggregate(table.getRow(1)), 11023);
        assertEquals(ListUtils.aggregate(table.getRow(2)), 10024);
        assertEquals(ListUtils.aggregate(table.getRow(3)), 13025);

        assertEquals(ListUtils.aggregate(table.getRow(FIRST)), 11023);
        assertEquals(ListUtils.aggregate(table.getRow(SECOND)), 10024);
        assertEquals(ListUtils.aggregate(table.getRow(THIRD)), 13025);
    }


    // Capitals table
    private void testGrid(Table table) {
        List<String> row;
        List<String> col;
        Map<Integer, List<String>> grid;
        assertFalse(table.getHeaders().isEmpty());
        assertFalse(table.getGrid().isEmpty());
        assertEquals(table.getHeaders().size(), 4);
        assertEquals(table.getRows(), 3);
        assertEquals(table.getColumns(), 4);
        List<String> headers = table.getHeaders();
        assertEquals(headers.get(0),"Country");
        assertEquals(headers.get(1),"Capital");
        assertEquals(headers.get(2),"Population (m)");
        assertEquals(headers.get(3),"Language");
        grid = table.getGrid();
        assertEquals(grid.get(0).get(0),"UK");
        assertEquals(grid.get(1).get(1),"Paris");
        assertEquals(grid.get(2).get(3),"Italian");
        assertEquals(table.getCell(1,2),"London");
        assertEquals(table.getCell(FIRST,SECOND),"London");
        assertEquals(table.getCell(1,SECOND),"London");
        assertEquals(table.getCell(FIRST,2),"London");
        // Header indexes
        assertEquals(table.getHeader("Country"),1);
        assertEquals(table.getHeader("Capital"),2);
        assertEquals(table.getHeader("Population (m)"),3);
        assertEquals(table.getHeader("Language"),4);

        // Cell by index and header as string
        assertEquals(table.getCell(1,"Country"),"UK");
        assertEquals(table.getCell( 2,"Capital"),"Paris");
        assertEquals(table.getCell(3, "Population (m)"),"2.7");
        assertEquals(table.getCell(3, "Language"),"Italian");
        // Cell by index and header as string
        assertEquals(table.getCell(FIRST,"Country"),"UK");
        assertEquals(table.getCell( SECOND,"Capital"),"Paris");
        assertEquals(table.getCell(THIRD, "Population (m)"),"2.7");
        assertEquals(table.getCell(THIRD, "Language"),"Italian");
        // Exceptions : Cell by index and header as string
        assertThrows(CPOException.class, () -> table.getCell(0,"Country"),"UK");
        assertThrows(CPOException.class, () -> table.getCell(-1,"Capital"),"Paris");
        assertThrows(CPOException.class, () -> table.getCell(1,""),"UK");

        assertEquals(table.getCell(2,3),"2");
        assertEquals(table.getCell(3,4),"Italian");
        assertEquals(table.getCell(THIRD,FOURTH),"Italian");
        row = table.getRow(1);
        assertEquals(row.get(0), table.getCell(1,1));
        assertEquals(row.get(0), table.getCell(1,FIRST));
        assertEquals(row.get(0), table.getCell(FIRST,1));
        assertEquals(row.get(0), table.getCell( FIRST,FIRST));
        row = table.getRow(FIRST);
        assertEquals(row.get(0), table.getCell(1,1));
        assertEquals(row.get(1), table.getCell(1,SECOND));
        assertEquals(row.get(2), table.getCell(FIRST,THIRD));
        assertEquals(row.get(3), table.getCell( FIRST,FOURTH));

        row = table.getRow(2);
        assertEquals(row.get(0), table.getCell(2,1));
        assertEquals(row.get(1), table.getCell(SECOND,SECOND));
        assertEquals(row.get(2), table.getCell(SECOND,3));
        assertEquals(row.get(3), table.getCell( 2,FOURTH));

        row = table.getRow(THIRD);
        assertEquals(row.get(0), table.getCell(3,1));
        assertEquals(row.get(1), table.getCell(THIRD,SECOND));
        assertEquals(row.get(2), table.getCell(THIRD,3));
        assertEquals(row.get(3), table.getCell( 3,FOURTH));

        col = table.getColumn(1);
        assertEquals(col.get(0), table.getCell(1,1));
        assertEquals(col.get(0), table.getCell(1, FIRST));
        assertEquals(col.get(0), table.getCell(   FIRST,1));
        assertEquals(col.get(0), table.getCell(   FIRST,FIRST));

        col = table.getColumn("Language");
        assertEquals(col.get(0), table.getCell(1,4));
        assertEquals(col.get(1), table.getCell(2, FOURTH));
        assertEquals(col.get(2), table.getCell(THIRD,FOURTH));
    }


    public static void setUp(CommonPageObject cpo) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir");
        cpo.open();
        cpo.go("file://" + path + "//src//test//java//junit//JTableExample.html");
    }
}

