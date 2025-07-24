package CalcoloTubolare.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import CalcoloTubolare.controller.scene.SceneControllerImpl;
import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.TubolarMultiListImpl;
import CalcoloTubolare.model.api.TubolarMultiList;
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

    TubolarMultiList tubolarList = new TubolarMultiListImpl();

    public TubolarMultiList getTubolarList() {
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

    public String partialCalcolateTubolar(Boolean optimal) {
        return CalcolatorTubolar.printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList,optimal));
    }

    public String totalCalcolateTubolar(Boolean optimal) {
        return CalcolatorTubolar
                .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList,optimal));
    }

    public void restart() {
        this.tubolarList = new TubolarMultiListImpl();
    }

    public void addTubolarFromExcel(final String pathString) {
        try (FileInputStream fis = new FileInputStream(pathString)) {
            Workbook workbook = WorkbookFactory.create(fis); // gestisce sia .xls che .xlsx
            Sheet sheet = workbook.getSheetAt(0);
            var rowIteretor = sheet.iterator();

            Row row = rowIteretor.next();
            while (rowIteretor.hasNext() || !row.getCell(2).getStringCellValue().equals("CODICE")
            || row.getCell(2).getStringCellValue().isEmpty()) {
                String name = row.getCell(2).getStringCellValue();
                int length = (int) row.getCell(4).getNumericCellValue();
                int quantity = (int) row.getCell(1).getNumericCellValue();

                if (Arrays.asList(GroupMerc.values()).stream().anyMatch(t -> name.contains(t.name()))) {
                    newTubolarList(name, length, quantity);
                }
                row = rowIteretor.next();
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
