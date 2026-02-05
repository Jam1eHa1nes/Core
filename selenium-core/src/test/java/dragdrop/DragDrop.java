package dragdrop;
// TODO : TO BE DELETED PENDING PLATFORM TESTS //

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.performable.AngularDragDrop;

import static com.selenium.qa.automation.core.Enums.Index.FIRST;
import static com.selenium.qa.automation.core.Enums.Tag.DIV;
import static com.selenium.qa.automation.core.Enums.Tag.TD;

public class DragDrop extends CommonPageObject {

    public DragDrop() {

        open();

        // ANGULAR SITES

        go("https:v7.material.angular.io/cdk/drag-drop/overview");
        pause(2000);
        scroll(id("disable-dragging"));
        pause(5000);
        perform(new AngularDragDrop(tagWithText(DIV, "2"), id("even")));
        pause(2);

        go("https:practice.expandtesting.com/drag-and-drop");
        pause(2);
        perform(new AngularDragDrop(id("column-a"), id("column-b")));

        go("https:js.devexpress.com/Angular/Demos/WidgetsGallery/Demo/Scheduler/CustomDragAndDrop/MaterialBlueLight/");
        pause(5);
        frame("demoFrame");
        focus(className("dx-scheduler-date-table")).collect(tag(TD)).choose(FIRST);
        drag(tagWithText("dx-draggable", "New Brochures"));
        pause(2);

        go("https:stackblitz.com/edit/angular-mwnmo5?file=src%2Fapp%2Fapp.component.ts");
        pause(5);
        frame(name("previewFrame"));
        perform(new AngularDragDrop(tagWithText(DIV, "item 1"), id("availableList")));
        pause(2);
    }

    protected void pause(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
