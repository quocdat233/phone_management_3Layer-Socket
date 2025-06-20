package client.view.views.ThongKe;

import client.view.shared.BaseView;
import client.BUS.ThongKeBUS;


import javax.swing.*;
import java.awt.*;

public final class ThongKeView extends BaseView {
    private JTabbedPane tabbedPane;
    JPanel tongquan, nhacungcap, khachhang, doanhthu;
    ThongKeTonKho nhapxuat;
    ThongKeBUS thongkeBUS = new ThongKeBUS();

    public ThongKeView() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(new Color(240, 247, 250));

        tongquan = new ThongKeTongQuan(thongkeBUS);
        nhapxuat = new ThongKeTonKho(thongkeBUS);
        doanhthu = new ThongKeDoanhThu(thongkeBUS);
        nhacungcap = new ThongKeNhaCungCap(thongkeBUS);
        khachhang = new ThongKeKhachHang(thongkeBUS);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.addTab("Tổng quan", tongquan);
        tabbedPane.addTab("Tồn kho", nhapxuat);
        tabbedPane.addTab("Doanh thu", doanhthu);
        tabbedPane.addTab("Nhà cung cấp", nhacungcap);
        tabbedPane.addTab("Khách hàng", khachhang);

        getMainPanel().add(tabbedPane);
    }
}
