package excelTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.view.ViewImpl;

public class MainMenuControllerTest {

    @Test
    public void testAddTubolarFromExcel() {
        ControllerModel controller = new ControllerModel(new ViewImpl(false));
        controller.addTubolarFromExcel(ClassLoader.getSystemResource("excel/TABELLA.xlsx").getPath());
        assertTrue(controller.getTubolarList().getMultiQueue().keySet().contains("PIA180150000000"));
    
    
}
}
