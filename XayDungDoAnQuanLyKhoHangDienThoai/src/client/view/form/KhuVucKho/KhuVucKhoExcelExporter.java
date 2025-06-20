package client.view.form.KhuVucKho;

import shared.models.KhuVucKho;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class KhuVucKhoExcelExporter {

    public static void exportKhuVucKhoToExcel(List<KhuVucKho> danhSachKhuVuc) {
        if (danhSachKhuVuc == null || danhSachKhuVuc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Danh sách khu vực kho trống!");
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) return;

            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) filePath += ".xlsx";

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Danh sách khu vực");

            String[] headers = {"Mã khu vực", "Tên khu vực", "Ghi chú"};

            // ==== Style Header ====
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // ==== Ghi Header ====
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // ==== Ghi dữ liệu ====
            int rowIndex = 1;
            for (KhuVucKho kvk : danhSachKhuVuc) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(kvk.getMakhuvuc());
                row.createCell(1).setCellValue(kvk.getTenkhuvuc());
                row.createCell(2).setCellValue(kvk.getGhichu());
            }

            // ==== Tự động căn cột ====
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // ==== Ghi file ====
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            workbook.close();

            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi ghi file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xuất Excel: " + e.getMessage());
        }
    }
}
