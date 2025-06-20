package client.view.views;

import client.view.shared.BaseView;
import client.view.shared.TopPanel_Two;
import com.toedter.calendar.JDateChooser;
import server.DAO.phieuXuatdao;
import shared.models.phieuXuat;


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ExportView extends BaseView {
    private TopPanel_Two topPanel;
    private JTable table;
    private JPanel leftPanel;
    private JPanel container, footer;

    public ExportView() {
        super();
        topPanel = new TopPanel_Two();

        container = new JPanel(new BorderLayout());
        container.setBackground(new Color(230, 230, 230));
        container.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        container.add(topPanel, BorderLayout.NORTH);

        // Bảng
        String[] columns = {"STT", "Mã phiếu xuất", "Khách hàng", "Nhân viên nhập", "Thời gian", "Tổng tiền"};
        Object[][] data = {};

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return java.sql.Timestamp.class;
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        // Sort theo thời gian giảm dần
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(4, SortOrder.DESCENDING)));

        // Renderer STT tự động theo row hiển thị
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setText(String.valueOf(row + 1));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBackground(new Color(245, 245, 245));
        header.setFont(new Font("Arial", Font.BOLD, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(230, 230, 230));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        initLeftPanel();

        footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        footer.setBackground(Color.decode("#E6E6E6"));
        footer.add(leftPanel, BorderLayout.WEST);
        footer.add(tablePanel, BorderLayout.CENTER);

        container.add(footer, BorderLayout.CENTER);

        getMainPanel().add(container, BorderLayout.CENTER);
        table.setBackground(Color.WHITE);
        updateTable(phieuXuatdao.layDanhSachPhieuXuat());
    }

    private void initLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setLayout(new GridLayout(16, 1, 40, 5));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftPanel.add(createLabel("Khách hàng"));
        JComboBox<String> supplierComboBox = new JComboBox<>(new String[]{"Tất cả", "Khách hàng A", "Khách hàng B"});
        supplierComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 27));
        leftPanel.add(supplierComboBox);

        leftPanel.add(createLabel("Nhân viên xuất"));
        JComboBox<String> staffComboBox = new JComboBox<>(new String[]{"Tất cả", "Nhân viên A", "Nhân viên B"});
        staffComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 27));
        leftPanel.add(staffComboBox);

        leftPanel.add(createLabel("Từ ngày"));
        JDateChooser fromDateChooser = new JDateChooser();
        fromDateChooser.setDate(new Date());
        fromDateChooser.setDateFormatString("dd/MM/yyyy");
        fromDateChooser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 27));
        leftPanel.add(fromDateChooser);

        leftPanel.add(createLabel("Đến ngày"));
        JDateChooser toDateChooser = new JDateChooser();
        toDateChooser.setDate(new Date());
        toDateChooser.setDateFormatString("dd/MM/yyyy");
        toDateChooser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 27));
        leftPanel.add(toDateChooser);

        leftPanel.add(createLabel("Từ số tiền (VND)"));
        JTextField fromAmountField = new JTextField();
        fromAmountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        leftPanel.add(fromAmountField);

        leftPanel.add(createLabel("Đến số tiền (VND)"));
        JTextField toAmountField = new JTextField();
        toAmountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        leftPanel.add(toAmountField);
    }

    public void updateTable(List<?> list) {
        if (list.isEmpty()) return;
        if (!(list.get(0) instanceof phieuXuat)) return;

        List<phieuXuat> phieuXuatList = (List<phieuXuat>) list;
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        model.setRowCount(0);

        for (phieuXuat sp : phieuXuatList) {
            model.addRow(new Object[]{
                    null, // STT tự động render
                    sp.getMaphieuXuat(),
                    sp.getTenKhachHang(),
                    sp.getTennhanvien(),
                    sp.getThoigiantao(),
                    sp.getTongTien()
            });
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return label;
    }

    public JPanel getContenPanel() {
        return mainPanel;
    }

    public TopPanel_Two getTopPanel() {
        return topPanel;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    public JPanel getContainer() {
        return container;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }

    public JPanel getFooter() {
        return footer;
    }

    public void setFooter(JPanel footer) {
        this.footer = footer;
    }
}
