package client.view.views.ThongKe;

import client.helper.Formater;
import client.helper.JTableExporter;
import client.view.components.*;
import client.BUS.ThongKeBUS;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ThongKeNhaCungCap extends JPanel implements ActionListener, KeyListener, PropertyChangeListener {
    ThongKeBUS thongKeBUS;
    private PanelBorderRadius nhapxuat_left;
    private InputForm tensanpham;
    private InputDate start_date, end_date;
    private ButtonCustom export, reset;
    private PanelBorderRadius nhapxuat_center;
    private JScrollPane scrollTblTonKho;
    private DefaultTableModel model;
    HashMap<Integer, ArrayList<shared.models.ThongKe.ThongKeTonKho>> listSp;
    private JTable tblKH;
    ArrayList<shared.models.ThongKe.ThongKeNhaCungCap> list;

    public ThongKeNhaCungCap(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        listSp = thongKeBUS.getTonKho();
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        nhapxuat_left = new PanelBorderRadius();
        nhapxuat_left.setPreferredSize(new Dimension(300, 100));
        nhapxuat_left.setLayout(new BorderLayout());
        nhapxuat_left.setBorder(new EmptyBorder(0, 0, 0, 5));
        JPanel left_content = new JPanel(new GridLayout(4, 1));
        left_content.setPreferredSize(new Dimension(300, 360));
        nhapxuat_left.add(left_content, BorderLayout.NORTH);

        tensanpham = new InputForm("Tìm kiếm nhà cung cấp");
        tensanpham.getTxtForm().putClientProperty("JTextField.showClearButton", true);
        start_date = new InputDate("Từ ngày");
        end_date = new InputDate("Đến ngày");

        tensanpham.getTxtForm().addKeyListener(this);
        start_date.getDateChooser().addPropertyChangeListener(this);
        end_date.getDateChooser().addPropertyChangeListener(this);

        JPanel btn_layout = new JPanel(new BorderLayout());
        JPanel btninner = new JPanel(new GridLayout(1, 2));
        btninner.setOpaque(false);
        btn_layout.setPreferredSize(new Dimension(30, 36));
        btn_layout.setBorder(new EmptyBorder(20, 10, 0, 10));
        btn_layout.setBackground(Color.white);
        export = new ButtonCustom("Xuất Excel", "excel", 14);
        reset = new ButtonCustom("Làm mới", "danger", 14);

        export.addActionListener(this);
        reset.addActionListener(this);

        btninner.add(export);
        btninner.add(reset);
        btn_layout.add(btninner, BorderLayout.NORTH);

        left_content.add(tensanpham);
        left_content.add(start_date);
        left_content.add(end_date);
        left_content.add(btn_layout);

        nhapxuat_center = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(nhapxuat_center, BoxLayout.Y_AXIS);
        nhapxuat_center.setLayout(boxly);

        tblKH = new JTable();
        JTableHeader headerTable = tblKH.getTableHeader();
        headerTable.setPreferredSize(new Dimension(headerTable.getPreferredSize().width, 35)); // tăng chiều cao thanh header
        headerTable.setBackground(new Color(245, 245, 245)); // Màu nền header
        headerTable.setForeground(Color.BLACK);                      // Màu chữ
        headerTable.setFont(new Font("Arial", Font.BOLD, 13));       // Font chữ
        headerTable.setReorderingAllowed(false);  // Không cho kéo đổi vị trí cột
        headerTable.setResizingAllowed(false);    // Không cho resize
        headerTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tblKH.setRowHeight(40); // tăng chiều cao các hàng
        tblKH.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // chọn 1 hàng duy nhất
        tblKH.setSelectionBackground(Color.decode("#D3D3D3"));
        tblKH.setSelectionForeground(Color.black);
        scrollTblTonKho = new JScrollPane();
        model = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Số lượng nhập", "Tổng số tiền"};
        model.setColumnIdentifiers(header);
        tblKH.setModel(model);
        tblKH.setAutoCreateRowSorter(true);
        tblKH.setDefaultEditor(Object.class, null);
        scrollTblTonKho.setViewportView(tblKH);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKH.setDefaultRenderer(Object.class, centerRenderer);
        tblKH.setShowHorizontalLines(true);
        tblKH.setFocusable(false);
        tblKH.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblKH.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblKH.getColumnModel().getColumn(2).setPreferredWidth(200);

        TableSorter.configureTableColumnSorter(tblKH, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 3, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 4, TableSorter.VND_CURRENCY_COMPARATOR);

        nhapxuat_center.add(scrollTblTonKho);

        this.add(nhapxuat_left, BorderLayout.WEST);
        this.add(nhapxuat_center, BorderLayout.CENTER);
    }

    public boolean validateSelectDate() throws ParseException {
        java.util.Date time_start = start_date.getDate();
        java.util.Date time_end = end_date.getDate();

        java.util.Date current_date = new java.util.Date();
        if (time_start != null && time_start.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            start_date.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            end_date.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            end_date.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    public void Fillter() throws ParseException {
        if (validateSelectDate()) {
            String input = tensanpham.getText() != null ? tensanpham.getText() : "";
            java.util.Date time_start = start_date.getDate() != null ? start_date.getDate() : new java.util.Date(0);
            java.util.Date time_end = end_date.getDate() != null ? end_date.getDate() : new java.util.Date(System.currentTimeMillis());
            this.list = thongKeBUS.FilterNCC(input, new java.sql.Date(time_start.getTime()), new java.sql.Date(time_end.getTime()));
            loadDataTable(list);
        }
    }

    public void loadDataTable(ArrayList<shared.models.ThongKe.ThongKeNhaCungCap> result) {
        model.setRowCount(0);
        int k = 1;
        for (shared.models.ThongKe.ThongKeNhaCungCap i : result) {
            model.addRow(new Object[]{
                    k, i.getMancc(), i.getTenncc(), i.getSoluong(), Formater.FormatVND(i.getTongtien())
            });
            k++;
        }
    }

    public void resetForm() throws ParseException {
        tensanpham.setText("");
        start_date.getDateChooser().setCalendar(null);
        end_date.getDateChooser().setCalendar(null);
        Fillter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == export) {
            try {
                JTableExporter.exportJTableToExcel(tblKH);
            } catch (IOException ex) {
                Logger.getLogger(ThongKeNhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (source == reset) {
            try {
                resetForm();
            } catch (ParseException ex) {
                Logger.getLogger(ThongKeNhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ThongKeNhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ThongKeNhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
