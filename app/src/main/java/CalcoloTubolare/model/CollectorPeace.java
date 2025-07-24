package CalcoloTubolare.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import CalcoloTubolare.controller.GroupMerc;
import CalcoloTubolare.model.api.Peace;
import CalcoloTubolare.model.api.Tubolar;
import CalcoloTubolare.model.api.TubolarMultiList;

public class CollectorPeace {

    List<Peace> tableList = new LinkedList<>();

    TubolarMultiList tubolarList = new TubolarMultiListImpl();

    public CollectorPeace(String path , Integer quantitySilo) {

        try (FileInputStream fis = new FileInputStream(path)) {
            Workbook workbook = WorkbookFactory.create(fis); // gestisce sia .xls che .xlsx
            Sheet sheet = workbook.getSheetAt(0);
            var rowIteretor = sheet.iterator();

            Row row = rowIteretor.next();
            while (rowIteretor.hasNext() || !row.getCell(2).getStringCellValue().equals("CODICE")
                    || row.getCell(2).getStringCellValue().isEmpty()) {

                String name = row.getCell(2).getStringCellValue();
                int length = (int) row.getCell(4).getNumericCellValue();
                int quantity = (int) row.getCell(1).getNumericCellValue() * quantitySilo;
                String description = row.getCell(3).getStringCellValue();
                String material = row.getCell(5).getStringCellValue();

                if (Arrays.asList(GroupMerc.values()).stream().anyMatch(t -> name.contains(t.name()))) {
                    newTubolarList(name, length, quantity, description, material);
                } else {
                    tableList.add(new Peace(name, description, quantity, material));
                }
                row = rowIteretor.next();
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity, String description,
            String material) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar,nameTubolar, quantity);
    }

    public List<Peace> getTableList() {
        return tableList;
    }

    public TubolarMultiList getTubolarList() {
        return tubolarList;
    }

}
