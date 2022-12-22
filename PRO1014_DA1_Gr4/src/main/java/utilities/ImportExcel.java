package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import model.ChiTietSanPham;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType._NONE;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repository.SanPhamRepository;
import repository.DanhMucRepository;
import repository.ChatLieuRepository;
import repository.MauRepository;
import repository.NSXRepository;

public class ImportExcel {

    public static final int COLUMN_MA = 0;
    public static final int COLUMN_TEN = 1;
    public static final int COLUMN_DM = 2;
    public static final int COLUMN_CHATLIEU = 3;
    public static final int COLUMN_MAU = 4;
    public static final int COLUMN_NSX = 5;
    public static final int COLUMN_SOLUONG = 6;
    public static final int COLUMN_GIANHAP = 7;
    public static final int COLUMN_GIABAN = 8;
    public static final int COLUMN_MOTA = 9;
    public static final int COLUMN_NGAYTAO = 10;
    public static final int COLUMN_NGAYSUA = 11;
    public static final int COLUMN_TRANGTHAI = 12;

    private static SanPhamRepository spRepo = new SanPhamRepository();
    private static DanhMucRepository dmRepo = new DanhMucRepository();
    private static ChatLieuRepository clRepo = new ChatLieuRepository();
    private static MauRepository mauRepo = new MauRepository();
    private static NSXRepository nsxRepo = new NSXRepository();
    
    public static void main(String[] args) throws IOException {
        final String excelFilePath = "D:/abc.xlsx";
        final List<ChiTietSanPham> ctsp = readExcel(excelFilePath);
        for (ChiTietSanPham x : ctsp) {
            System.out.println(x);
        }
    }


    public static List<ChiTietSanPham> readExcel(String excelFilePath) throws IOException {
        Date date = new Date();
        List<ChiTietSanPham> list = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            ChiTietSanPham ctsp = new ChiTietSanPham();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_MA:
                        ctsp.setMa((String) getCellValue(cell));
                        break;
                    case COLUMN_TEN:
                        ctsp.setSanPham(spRepo.getById(new BigDecimal((double) cellValue).intValue()));
                        break;
                    case COLUMN_DM:
                        ctsp.setDanhMuc(dmRepo.getById(new BigDecimal((double) cellValue).intValue()));
                        break;
                    case COLUMN_CHATLIEU:
                        ctsp.setMauSac(mauRepo.getById(new BigDecimal((double) cellValue).intValue()));
                        break;
                    case COLUMN_MAU:
                        ctsp.setChatLieu(clRepo.getById(new BigDecimal((double) cellValue).intValue()));
                        break;
                    case COLUMN_NSX:
                        ctsp.setNhaSanXuat(nsxRepo.getById(new BigDecimal((double) cellValue).intValue()));
                        break;
                    case COLUMN_SOLUONG:
                        ctsp.setSoLuongTon(new BigDecimal((double) cellValue).intValue());
                        break;
                    case COLUMN_GIANHAP:
                        ctsp.setGiaNhap(BigDecimal.valueOf((double) cellValue));
                        break;
                    case COLUMN_GIABAN:
                        ctsp.setGiaBan(BigDecimal.valueOf((double) cellValue));
                        break;
                    case COLUMN_MOTA:
                        ctsp.setMoTa((String) getCellValue(cell));
                        break;
                    case COLUMN_NGAYTAO:
                        ctsp.setNgayTao(date);
                        break;
                    case COLUMN_NGAYSUA:
                        ctsp.setNgaySua(date);
                        break;
                    case COLUMN_TRANGTHAI:
                        ctsp.setTrangThai(new BigDecimal((double) cellValue).intValue());
                        break;
                    default:
                        break;
                }

            }
            list.add(ctsp);
        }

        workbook.close();
        inputStream.close();

        return list;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}

