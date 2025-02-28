package CalcoloTubolare.model.api;
import org.apache.poi.ss.usermodel.*;

public interface InputExcelTable {

    public void setWorkBook(String excelFilePath);

    public Workbook getWorkBook();
    
}
