package utilities;

import customModel.HoaDonDoanhThu;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author fallinluv2003
 */
public class ExportDoanhSo {

    public static final int COLUMN_MA = 0;
    public static final int COLUMN_TEN = 1;
    public static final int COLUMN_DM = 2;
    public static final int COLUMN_CHATLIEU = 3;
    public static final int COLUMN_MAU = 4;
    public static final int COLUMN_NSX = 5;
    public static final int COLUMN_SOLUONG = 6;

    public static void writeExcel(List<HoaDonDoanhThu> list, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);
        // Create sheet
        Sheet sheet = workbook.createSheet("Số lượng sản phẩm bán ra");
        int rowIndex = 0;
        // Write header
        writeHeader(sheet, rowIndex);
        // Write data
        rowIndex++;
        for (HoaDonDoanhThu hoaDon : list) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(hoaDon, row);
            rowIndex++;
        }
        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {

        CellStyle cellStyle = createStyleHeader(sheet);

        Row row = sheet.createRow(rowIndex);

        Cell cell = row.createCell(COLUMN_MA);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Mã CTSP");

        Cell cel = row.createCell(COLUMN_TEN);
        cel.setCellStyle(cellStyle);
        cel.setCellValue("Tên sản phẩm");

        cell = row.createCell(COLUMN_DM);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Danh mục");

        cell = row.createCell(COLUMN_CHATLIEU);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Chất liệu");

        cell = row.createCell(COLUMN_MAU);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Màu sắc");

        cell = row.createCell(COLUMN_NSX);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Nhà sản xuất");

        cell = row.createCell(COLUMN_SOLUONG);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Số lượng");
    }

    private static void writeBook(HoaDonDoanhThu hoaDon, Row row) {

        Cell cell = row.createCell(COLUMN_MA);
        cell.setCellValue(hoaDon.getMaCTSP());

        Cell cel = row.createCell(COLUMN_TEN);
        cel.setCellValue(hoaDon.getTenSP());

        cell = row.createCell(COLUMN_DM);
        cell.setCellValue(hoaDon.getTenDm());

        cell = row.createCell(COLUMN_CHATLIEU);
        cell.setCellValue(hoaDon.getTenCL());

        cell = row.createCell(COLUMN_MAU);
        cell.setCellValue(hoaDon.getTenMau());

        cell = row.createCell(COLUMN_NSX);
        cell.setCellValue(hoaDon.getTenNSX());

        cell = row.createCell(COLUMN_SOLUONG);
        cell.setCellValue(hoaDon.getSoLuongBanRa());
    }

    // Create CellStyle for header
    private static CellStyle createStyleHeader(Sheet sheet) {
        // Create font
        org.apache.poi.ss.usermodel.Font font = sheet.getWorkbook().createFont();
//        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 13); // font size
        font.setColor(IndexedColors.RED.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
