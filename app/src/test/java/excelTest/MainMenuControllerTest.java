package excelTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import CalcoloTubolare.controller.MainMenuController;
import CalcoloTubolare.view.ViewImpl;

public class MainMenuControllerTest {

    @Test
    public void testAddTubolarFromExcel() {
        MainMenuController controller = new MainMenuController(new ViewImpl(false));
        controller.addTubolarFromExcel(ClassLoader.getSystemResource("excel/TABELLA.xlsx").getPath());
        assertTrue(controller.getTubolarList().getMultiQueue().keySet().contains("PIA180150000000"));
    
    
}
}
