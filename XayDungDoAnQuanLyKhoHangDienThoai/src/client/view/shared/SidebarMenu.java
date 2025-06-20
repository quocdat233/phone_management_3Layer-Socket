package client.view.shared;

import client.view.components.ButtonUtils;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SidebarMenu extends JPanel {
    private JButton btnHome, btnProduct,btnProductInfo, btnStock, btnCustomer, btnEmployee, btnAccount, btnStatistic, btnLogout,
            btnImport, btnExport, btnSupplier, btnDecentralization, btnMess;
    private JLabel lblTitle;

    private List<JButton> allButtons = new ArrayList<>();
    private final Color defaultColor = new Color(92, 181, 181);
    private final Color hoverColor = new Color(241, 248, 248);
    private final Color activeColor = new Color(241, 248, 248);
    private JButton activeButton;

    // Danh sách các spacer linh hoạt giữa các nút
    private final List<Box.Filler> spacers = new ArrayList<>();

    public SidebarMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        setBackground(defaultColor);

        URL url = ButtonUtils.class.getResource("/images/user.svg");
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
         lblTitle = new JLabel();


        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 20));
        lblTitle.setIcon(new FlatSVGIcon("images/account.svg", 41, 41));
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        // Khởi tạo các nút
        btnHome = createSidebarButton("  Trang chủ", "/images/iconHome.png");
        btnHome.setIcon(new FlatSVGIcon("images/house.svg", 31, 31));

        btnProduct = createSidebarButton("  Sản phẩm", "/images/iconProduct.png");
        btnProduct.setIcon(new FlatSVGIcon("images/sanpham.svg", 31, 31));

        btnStock = createSidebarButton("  Khu vực kho", "/images/iconStock.png");
        btnStock.setIcon(new FlatSVGIcon("images/mapppp.svg", 31, 31));

        btnProductInfo = createSidebarButton("  Thuộc tính", "/images/thuocTinh.svg");
        btnProductInfo.setIcon(new FlatSVGIcon("images/thuocTinh.svg", 31, 31));

        btnImport = createSidebarButton("  Phiếu nhập", "/images/iconImport.png");
        btnImport.setIcon(new FlatSVGIcon("images/phieunhap.svg", 31, 31));

        btnExport = createSidebarButton("  Phiếu xuất", "/images/iconExport.png");
        btnExport.setIcon(new FlatSVGIcon("images/exportt.svg", 31, 31));

        btnCustomer = createSidebarButton("  Khách hàng", "/images/iconCustomer.png");
        btnCustomer.setIcon(new FlatSVGIcon("images/khachhang.svg", 31, 31));

        btnMess = createSidebarButton("  Tin nhắn", "/images/message.png");
        btnMess.setIcon(new FlatSVGIcon("images/messs.svg", 33, 33));

        btnSupplier = createSidebarButton("  Nhà cung cấp", "/images/iconSupplier.png");
        btnSupplier.setIcon(new FlatSVGIcon("images/nhacungcapp.svg", 31, 31));

        btnEmployee = createSidebarButton("  Nhân viên", "/images/iconEmployee.png");
        btnEmployee.setIcon(new FlatSVGIcon("images/nahnvienn.svg", 31, 31));

        btnAccount = createSidebarButton("  Tài khoản", "/images/iconAccount.png");
        btnAccount.setIcon(new FlatSVGIcon("images/accc.svg", 31, 31));

        btnStatistic = createSidebarButton("  Thống kê", "/images/iconStatistic.png");
        btnStatistic.setIcon(new FlatSVGIcon("images/thonmgke.svg", 31, 31));

        btnDecentralization = createSidebarButton("  Phân quyền", "/images/iconDecentralization.png");
        btnDecentralization.setIcon(new FlatSVGIcon("images/accuracyy.svg", 31, 31));

        btnLogout = createSidebarButton("  Đăng xuất", "/images/iconLogout.png");
        btnLogout.setIcon(new FlatSVGIcon("images/log_out.svg", 33, 33));
        btnLogout.setIconTextGap(-2);

        // Thêm nút vào danh sách
        allButtons.add(btnHome);
        allButtons.add(btnProduct);
        allButtons.add(btnStock);
        allButtons.add(btnProductInfo);
        allButtons.add(btnImport);
        allButtons.add(btnExport);
        allButtons.add(btnCustomer);
        allButtons.add(btnMess);
        allButtons.add(btnSupplier);
        allButtons.add(btnEmployee);
        allButtons.add(btnAccount);
        allButtons.add(btnStatistic);
        allButtons.add(btnDecentralization);
        allButtons.add(btnLogout);

        // Add vào giao diện với spacer linh hoạt
        add(lblTitle);
        add(Box.createVerticalStrut(20));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);

        add(separator);
        add(Box.createVerticalStrut(10));

        add(btnHome);
        addFlexibleSpacer();

        add(btnProduct);
        addFlexibleSpacer();

        add(btnProductInfo);
        addFlexibleSpacer();

        add(btnStock);
        addFlexibleSpacer();

        add(btnImport);
        addFlexibleSpacer();

        add(btnExport);
        addFlexibleSpacer();

        add(btnCustomer);
        addFlexibleSpacer();

        add(btnMess);
        addFlexibleSpacer();

        add(btnSupplier);
        addFlexibleSpacer();

        add(btnEmployee);
        addFlexibleSpacer();

        add(btnAccount);
        addFlexibleSpacer();

        add(btnStatistic);
        addFlexibleSpacer();

        add(btnDecentralization);
        add(Box.createVerticalStrut(60));  // giữ khoảng cách lớn cuối trước Logout

        add(btnLogout);

        // Lắng nghe sự kiện resize để cập nhật khoảng cách
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSpacing(getWidth());
            }
        });
    }

    // Tạo nút sidebar
    private JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorderPainted(false);
        button.setBackground(defaultColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ButtonUtils.setIconButton(button, iconPath, 27, 27);

        button.setMaximumSize(new Dimension(200, 49));
        button.setPreferredSize(new Dimension(200, 45));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setMargin(new Insets(0, 15, 0, 0));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != activeButton)
                    button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != activeButton)
                    button.setBackground(defaultColor);
            }
        });

        return button;
    }

    // Thêm spacer linh hoạt giữa các nút
    private void addFlexibleSpacer() {
        // min, pref, max height của spacer (chiều rộng không quan trọng vì layout BoxLayout Y_AXIS)
        Dimension min = new Dimension(0, 5);
        Dimension pref = new Dimension(0, 13);
        Dimension max = new Dimension(0, 20);
        Box.Filler spacer = new Box.Filler(min, pref, max);
        spacers.add(spacer);
        add(spacer);
    }

    public void updateSpacing(int width) {
        int spacing;
        if (width < 150) {
            spacing = 0;   // khoảng cách nhỏ khi sidebar hẹp
        } else if (width < 200) {
            spacing = 0;  // khoảng cách vừa phải
        } else {
            spacing = 13;  // khoảng cách mặc định
        }

        for (Box.Filler spacer : spacers) {
            spacer.changeShape(
                    new Dimension(0, spacing),
                    new Dimension(0, spacing),
                    new Dimension(0, spacing)
            );
        }

        revalidate();
        repaint();
    }

    // Các phương thức set/reset màu nút active
    public void setActiveButton(JButton button) {
        resetAllButtonColors();
        button.setBackground(activeColor);
        activeButton = button;
    }

    public void resetAllButtonColors() {
        for (JButton btn : allButtons) {
            btn.setBackground(defaultColor);
        }
    }

    // Getter methods
    public JButton getBtnHome() { return btnHome; }
    public JButton getBtnProduct() { return btnProduct; }
    public JButton getBtnStock() { return btnStock; }
    public JButton getBtnCustomer() { return btnCustomer; }
    public JButton getBtnEmployee() { return btnEmployee; }
    public JButton getBtnAccount() { return btnAccount; }
    public JButton getBtnStatistic() { return btnStatistic; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnImport() { return btnImport; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnSupplier() { return btnSupplier; }
    public JButton getBtnDecentralization() { return btnDecentralization; }
    public JButton getBtnMess() { return btnMess; }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(JLabel lblTitle) {
        this.lblTitle = lblTitle;
    }

    public JButton getBtnProductInfo() {
        return btnProductInfo;
    }


}
