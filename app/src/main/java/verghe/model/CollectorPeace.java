package verghe.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Pair;

import verghe.model.api.GroupMerceologiciTubolar;
import verghe.model.api.Peace;
import verghe.model.api.TubolarMultiList;

/**
 * CollectorPeace class for collecting and managing tubulars and sample peace
 * data.
 */
public class CollectorPeace {

    final private List<Peace> tableSeampleList = new LinkedList<>();
    final private Set<Pair<String, String>> tableTubolarList = new HashSet<>();
    final private TubolarMultiList tubolarList = new TubolarMultiListImpl();

    /**
     * Constructor for CollectorPeace.
     * 
     * @param path         the path to the Excel file containing tubular data
     * @param quantitySilo the quantity multiplier for each tubular
     */
    public CollectorPeace(String path, Integer quantitySilo) {

        try (FileInputStream fis = new FileInputStream(path)) {
            Workbook workbook = WorkbookFactory.create(fis); // gestisce sia .xls che .xlsx
            Sheet sheet = workbook.getSheetAt(0);
            var rowIteretor = sheet.iterator();

            Row row = rowIteretor.next();
            while (rowIteretor.hasNext() || !row.getCell(2).getStringCellValue().equals("CODICE")
                    || row.getCell(2).getStringCellValue().isEmpty()) {

                int quantity = (int) row.getCell(1).getNumericCellValue() * quantitySilo;
                String name = row.getCell(2).getStringCellValue();
                String description = row.getCell(3).getStringCellValue();
                int length = (int) row.getCell(4).getNumericCellValue();
                String material = row.getCell(5).getStringCellValue();

                if (Arrays.asList(GroupMerceologiciTubolar.values()).stream()
                        .anyMatch(t -> name.contains(t.name()))) {
                    newTubolarList(name, length, quantity, description, material);
                    tableSeampleList.add(new Peace(name, description, quantity, material, length));
                } else {
                    tableSeampleList.add(new Peace(name, description, quantity, material));
                }
                row = rowIteretor.next();
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates a new tubular list with the specified parameters.
     * 
     * @param nameTubolar   the name of the tubular
     * @param lengthTubolar the length of the tubular
     * @param quantity      the quantity of the tubular
     * @param description   the description of the tubular
     * @param material      the material of the tubular
     */
    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity, String description,
            String material) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar, nameTubolar, quantity);
    }

    /**
     * Gets the list of sample peace data.
     * 
     * @return a list of Peace objects
     */
    public List<Peace> getTableSeampleList() {
        return tableSeampleList;
    }

    /**
     * Gets the list of tubular data.
     * 
     * @return a set of Pair objects containing tubular code and description
     */
    public TubolarMultiList getTubolarList() {
        return tubolarList;
    }

    /**
     * Gets the list of tubular data as a set of pairs.
     * 
     * @return a set of Pair objects containing tubular code and description
     */
    public Set<Pair<String, String>> getTableTubolarList() {
        return tableTubolarList;
    }

}
