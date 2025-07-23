package CalcoloTubolare.controller;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import CalcoloTubolare.controller.scene.SceneControllerImpl;
import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.InputExcelTableImpl;
import CalcoloTubolare.model.MultiQueueImpl;
import CalcoloTubolare.model.api.InputExcelTable;
import CalcoloTubolare.model.api.MultiQueue;
import CalcoloTubolare.view.View;

public class ControllerModel extends SceneControllerImpl {

    View view;

    public View getView() {
        return view;
    }

    public ControllerModel(View mainView) {
        super(mainView);
        this.view = mainView;
    }

    MultiQueue tubolarList = new MultiQueueImpl();

    public MultiQueue getTubolarList() {
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
        this.tubolarList = new MultiQueueImpl();
    }

    public void addTubolarFromExcel(String pathString) {
        InputExcelTable excelTable = new InputExcelTableImpl(pathString);
        Sheet sheet = excelTable.getWorkBook().getSheetAt(0);
        var rowIteretor = sheet.iterator();

        Row row = rowIteretor.next();
        while (rowIteretor.hasNext() || !row.getCell(2).getStringCellValue().equals("CODICE")) {
            
            String name = row.getCell(2).getStringCellValue();
            int length = (int) row.getCell(4).getNumericCellValue();
            int quantity = (int) row.getCell(1).getNumericCellValue();

            if (Arrays.asList(GroupMerc.values()).stream().filter(t -> name.contains(t.name())).count() != 0) {
                newTubolarList(name, length, quantity);
            }
            row = rowIteretor.next();
        }
        
    }

}
