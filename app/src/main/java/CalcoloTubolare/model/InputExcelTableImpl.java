package CalcoloTubolare.model;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import CalcoloTubolare.model.api.InputExcelTable;

public class InputExcelTableImpl implements InputExcelTable {

    private Workbook workBook;

    public InputExcelTableImpl(String excelFilePath) {
        setWorkBook(excelFilePath);
    }

    @Override
    public void setWorkBook(String excelFilePath) {
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath))) {
            this.workBook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Workbook getWorkBook() {
        return this.workBook;
    }

}
