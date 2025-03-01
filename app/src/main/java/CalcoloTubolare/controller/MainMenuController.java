package CalcoloTubolare.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import CalcoloTubolare.controller.scene.SceneControllerImpl;
import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.InputExcelTableImpl;
import CalcoloTubolare.model.MultiQueueImpl;
import CalcoloTubolare.model.api.InputExcelTable;
import CalcoloTubolare.model.api.MultiQueue;
import CalcoloTubolare.view.View;

public class MainMenuController extends SceneControllerImpl {

    public MainMenuController(View mainView) {
        super(mainView);
    }

    MultiQueue<Integer, String> tubolarList = new MultiQueueImpl<>();

    public MultiQueue<Integer, String> getTubolarList() {
        return tubolarList;
    }

    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar, nameTubolar, quantity);
    }

    public void removeTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        tubolarList.removeTubolar(nameTubolar, lengthTubolar);
    }

    public String partialCalco() {
        return CalcolatorTubolar.printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public String totalCalco() {
        return CalcolatorTubolar
                .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public void restart() {
        this.tubolarList = new MultiQueueImpl<>();
    }

    public void addTubolarFromExcel(String pathString) {
        InputExcelTable excelTable = new InputExcelTableImpl(pathString);
        Sheet sheet = excelTable.getWorkBook().getSheetAt(0);
        var rowIteretor = sheet.iterator();
        while (rowIteretor.hasNext()) {
            Row row = rowIteretor.next();
            if (row.getCell(2).getStringCellValue().equals("CODICE")) {
                break;
            }
            String name = row.getCell(2).getStringCellValue();
            int length = (int) row.getCell(4).getNumericCellValue();
            int quantity = (int) row.getCell(1).getNumericCellValue();
            newTubolarList(name, length, quantity);
        }
    }

}
