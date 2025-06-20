package client.controller;

import client.view.form.KhuVucKho.AddStockForm;
import client.view.form.KhuVucKho.EditStockForm;
import client.view.form.KhuVucKho.KhuVucKhoExcelExporter;
import client.view.form.KhuVucKho.MapPanel;
import client.view.views.StockView;
import server.DAO.KhuVucKhoDAO;
import shared.models.KhuVucKho;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StockController {

    private StockView stockView;
    private KhuVucKhoDAO khuVucKhoDAO = new KhuVucKhoDAO();

    public StockController(StockView stockView) {
        this.stockView = stockView;
        initController();
        stockView.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleRowSelection();
            }
            else {
                System.out.println("Nó Là null");
            }
        });
    }

    private void initController() {
        stockView.getBtnAdd().addActionListener(e -> openAddStockForm());
        stockView.getBtnEdit().addActionListener(e -> openEditStockForm());
        stockView.getBtnDelete().addActionListener(e -> deleteKhuVuc());
        stockView.getBtnImport().addActionListener(e -> openEditStockForm());
        stockView.getBtnExport().addActionListener(e -> {
            int [] seclectedRows = stockView.getTable().getSelectedRows();
            if(seclectedRows.length == 0){
                JOptionPane.showMessageDialog(stockView,"Vui lòng chọn kho cần xuất!");
                return;
            }
            List<KhuVucKho> selectedKhuVuc = new ArrayList<>();
            for (int row : seclectedRows){
                int  makhuVuc = (int) stockView.getTable().getValueAt(row,0);
                KhuVucKho kv = khuVucKhoDAO.getKhuVucKhoByID(makhuVuc);
                if(kv!=null){
                    selectedKhuVuc.add(kv);
                }

            }
            KhuVucKhoExcelExporter.exportKhuVucKhoToExcel(selectedKhuVuc);
        });
    }

    private void openAddStockForm() {
        AddStockForm form = new AddStockForm();
        form.getBtnCancel().addActionListener(e -> form.dispose());
        form.getBtnAdd().addActionListener(e -> {
            addKhuVuc(form);
        });
        form.setVisible(true);
    }

    private void openEditStockForm() {
        EditStockForm form = new EditStockForm();
        int selectedRow = stockView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(stockView, "Chọn khu vực cần sửa!");
            return;
        }
        int productId = (int) stockView.getTable().getValueAt(selectedRow, 0);
        form.khuVucEdit(khuVucKhoDAO.getKhuVucKhoByID(productId));
        form.getBtnCancel().addActionListener(e -> form.dispose());
        form.getBtnAdd().addActionListener(e->{
            editKhuVuc(form);
            MapPanel mapPanel = new MapPanel();
            mapPanel.updateMapFromDatabase();
            stockView.setMap(mapPanel);


        });
        form.setVisible(true);
    }

    private void deleteKhuVuc() {
        int[] selectedRows = stockView.getTable().getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(stockView, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(stockView,
                "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            for (int row : selectedRows) {
                int  maKho = (int ) stockView.getTable().getValueAt(row, 0);
                khuVucKhoDAO.xoaKhuVuc(maKho);
            }

            stockView.updateTable(khuVucKhoDAO.getAllkhuVuc());
            stockView.getMap().updateMapFromDatabase();


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(stockView, "Lỗi khi xóa sản phẩm: " + ex.getMessage());
        }
    }
    private void addKhuVuc(AddStockForm form) {
        String tenKhuVuc = form.getTxtName().getText().trim();
        String ghiChu = form.getTxtNote().getText().trim();
        String diaChi = form.getTxtAddress().getText().trim();
        if (tenKhuVuc.isEmpty()||diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tên khu vực không được để trống!");
            return;
        }

        if (ghiChu.length() > 255) {
            JOptionPane.showMessageDialog(form, "Ghi chú không được vượt quá 255 ký tự!");
            return;
        }



        try {
            KhuVucKho khuVucKho = new KhuVucKho();
            khuVucKho.setToado(diaChi);
            khuVucKho.setTenkhuvuc(tenKhuVuc);
            khuVucKho.setGhichu(ghiChu);

            int rows = khuVucKhoDAO.themKhuVucKho(khuVucKho);
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Thêm khu vực kho thành công!");
                stockView.updateTable(khuVucKhoDAO.getAllkhuVuc());
                stockView.getMap().updateMapFromDatabase();
                form.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm khu vực kho thất bại!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }


    private void handleRowSelection() {
        int selectedRow = stockView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                String tenKhuVuc = (String) stockView.getTable().getValueAt(selectedRow, 1).toString(); // Cột 1 là tenkhuvuc
                stockView.updateProductList(tenKhuVuc);
                System.out.println(tenKhuVuc);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(stockView, "Lỗi khi cập nhật danh sách sản phẩm: " + ex.getMessage());
            }
        }
    }

    public void editKhuVuc(EditStockForm form){
        int selectedRow = stockView.getTable().getSelectedRow();
        try {
            int  productId = (int ) stockView.getTable().getValueAt(selectedRow, 0);

            String tenKhuVuc = form.getTxtName().getText().trim();
            String ghiChu = form.getTxtNote().getText().trim();
            String diachi = form.getTxtAddress().getText().trim();

            if (tenKhuVuc.isEmpty()||ghiChu.isEmpty()||diachi.isEmpty()) {
                JOptionPane.showMessageDialog(form, "Không được để trống thông tin !");
                return;
            }
            if (ghiChu.length() > 255) {
                JOptionPane.showMessageDialog(form, "Ghi chú không được vượt quá 255 ký tự!");
                return;
            }




            try {
                KhuVucKho kv = new KhuVucKho();
                kv.setTenkhuvuc(tenKhuVuc);
                kv.setGhichu(ghiChu);
                kv.setToado(diachi);


                int rows = khuVucKhoDAO.suaKhuVuc(kv, productId);
                if (rows > 0) {
                    JOptionPane.showMessageDialog(form, "Sửa thành công");
                    stockView.getMap().updateMapFromDatabase();

                } else {
                    JOptionPane.showMessageDialog(form, "Sửa ko thành công");
                }
                stockView.updateTable(khuVucKhoDAO.getAllkhuVuc());

                form.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(form, "Vui lòng nhập đúng định dạng số!");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(stockView, "Lỗi khi sửa sản phẩm: " + ex.getMessage());
        }
    }




}
