package excelTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.view.ViewImpl;

/**
 * Test class for MainMenuController.
 * This class tests the functionality of adding a tubular from an Excel file.
 */
public class MainMenuControllerTest {

    @Test
    public void testAddTubolarFromExcel() {
        ControllerModel controller = new ControllerModel(new ViewImpl(false));
        controller.addTubolarFromExcel(ClassLoader.getSystemResource("excel/TABELLA.xlsx").getPath(), 1);
        assertTrue(controller.getTubolarList().getMultiQueue().keySet().contains("PIA180150000000"));

    }
}
