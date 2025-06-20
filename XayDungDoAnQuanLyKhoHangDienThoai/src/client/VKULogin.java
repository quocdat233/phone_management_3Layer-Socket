package client;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import client.controller.ForgotPassWord;
import client.controller.dangNhapController;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class VKULogin extends JFrame {
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JPanel pnlDangNhap;
    private JComboBox<String> comboGiaoDien;
    private JLabel lblDangNhap;

    public VKULogin() {
        setTitle("VKU-LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        setResizable(false);

        // === Hình ảnh bên trái ===
        JPanel pnlAnh = new JPanel();
        pnlAnh.setBorder(new EmptyBorder(3, 10, 5, 5));
        pnlAnh.setPreferredSize(new Dimension(500, 500));
        JLabel lblAnh = new JLabel();
        lblAnh.setIcon(new FlatSVGIcon("images/accuracyy.svg")); // Thay bằng SVG phù hợp
        pnlAnh.add(lblAnh);
        add(pnlAnh, BorderLayout.WEST);

        // === Panel form đăng nhập ===
        JPanel pnlMain = new JPanel();
        pnlMain.setBorder(new EmptyBorder(20, 0, 0, 0));
        pnlMain.setPreferredSize(new Dimension(500, 500));
        pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        // ==== GIAO DIỆN GÓC PHẢI TRÊN ====
        String[] themes = {"Flat Light", "Flat Dark", "IntelliJ", "Darcula", "VKU Theme"};
        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlTopRight.setOpaque(false);
        pnlTopRight.setPreferredSize(new Dimension(400, 40));
        JLabel lblIconTheme = new JLabel(new ImageIcon("src/view/assets/lightbulb.png"));
        comboGiaoDien = new JComboBox<>(themes);
        comboGiaoDien.setPreferredSize(new Dimension(150, 30));
        comboGiaoDien.addActionListener(e -> thayDoiGiaoDien());
        pnlTopRight.add(lblIconTheme);
        pnlTopRight.add(comboGiaoDien);
        pnlMain.add(pnlTopRight); // Thêm đầu tiên

        // ==== Tiêu đề ====
        JLabel lblTieuDe = new JLabel("VKU - LOGIN");
        lblTieuDe.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 32));
        pnlMain.add(lblTieuDe);

        // ==== Form nhập liệu ====
        JPanel panelForm = new JPanel();
        panelForm.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelForm.setPreferredSize(new Dimension(400, 190));
        panelForm.setLayout(new GridLayout(5, 1, 0, 0));

        JLabel lblTaiKhoan = new JLabel("Tài khoản");
        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setText("hgbaodevv");
        txtTaiKhoan.setForeground(Color.BLACK);
        txtTaiKhoan.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        txtTaiKhoan.putClientProperty("JTextField.placeholderText", "Tài khoản");
        panelForm.add(lblTaiKhoan);
        panelForm.add(txtTaiKhoan);

        JLabel lblPass = new JLabel("Mật khẩu");
        txtMatKhau = new JPasswordField();
        txtMatKhau.setText("123456");
        txtMatKhau.setForeground(Color.BLACK);
        txtMatKhau.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        txtMatKhau.putClientProperty("JTextField.placeholderText", "Mật khẩu");

        panelForm.add(lblPass);
        panelForm.add(txtMatKhau);

        pnlMain.add(panelForm);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));


        // ==== Nút đăng nhập ====
        lblDangNhap = new JLabel("ĐĂNG NHẬP");
        lblDangNhap.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lblDangNhap.setForeground(Color.WHITE);
        lblDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlDangNhap = new JPanel();
        pnlDangNhap.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlDangNhap.setBackground(Color.BLACK);
        pnlDangNhap.setPreferredSize(new Dimension(380, 45));
        pnlDangNhap.setLayout(new FlowLayout(1, 0, 15));
        pnlDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlDangNhap.add(lblDangNhap);
        pnlMain.add(pnlDangNhap);

        // ==== Quên mật khẩu ====

        JLabel lblQuenMatKhau = new JLabel("Quên mật khẩu", JLabel.RIGHT);
        lblQuenMatKhau.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        lblQuenMatKhau.setPreferredSize(new Dimension(380, 50));
        lblQuenMatKhau.setForeground(Color.decode("#C50023"));
        lblQuenMatKhau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPassWord().showEmailInputForm();
            }
        });
        lblQuenMatKhau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblQuenMatKhau.setForeground(Color.decode("#0000FF"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblQuenMatKhau.setForeground(Color.decode("#C50023"));
            }
        });


        pnlMain.add(lblQuenMatKhau);

        // ==== Thêm vào frame ====
        add(pnlMain, BorderLayout.EAST);


    }

    private void thayDoiGiaoDien() {
        String luaChon = (String) comboGiaoDien.getSelectedItem();
        try {
            switch (luaChon) {
                case "Flat Light" -> UIManager.setLookAndFeel(new FlatLightLaf());
                case "Flat Dark" -> UIManager.setLookAndFeel(new FlatDarkLaf());
                case "IntelliJ" -> UIManager.setLookAndFeel(new FlatIntelliJLaf());
                case "Darcula" -> UIManager.setLookAndFeel(new FlatDarculaLaf());
                case "VKU Theme" -> {
                    FlatLaf.registerCustomDefaultsSource("theme");
                    UIManager.setLookAndFeel(new FlatLightLaf());
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);

        SwingUtilities.invokeLater(() -> {
            VKULogin loginView = new VKULogin(); // ✅ Tạo trước đối tượng VKULogin
            new dangNhapController(loginView);   // ✅ Truyền loginView vào controller
            loginView.setVisible(true);          // ✅ Hiển thị sau khi controller gán listener
        });
    }


    public JTextField getTxtTaiKhoan() {
        return txtTaiKhoan;
    }

    public void setTxtTaiKhoan(JTextField txtTaiKhoan) {
        this.txtTaiKhoan = txtTaiKhoan;
    }

    public JPasswordField getTxtMatKhau() {
        return txtMatKhau;
    }

    public void setTxtMatKhau(JPasswordField txtMatKhau) {
        this.txtMatKhau = txtMatKhau;
    }

    public JPanel getPnlDangNhap() {
        return pnlDangNhap;
    }

    public void setPnlDangNhap(JPanel pnlDangNhap) {
        this.pnlDangNhap = pnlDangNhap;
    }

    public JComboBox<String> getComboGiaoDien() {
        return comboGiaoDien;
    }

    public JLabel getLblDangNhap() {
        return lblDangNhap;
    }

    public void setLblDangNhap(JLabel lblDangNhap) {
        this.lblDangNhap = lblDangNhap;
    }

    public void setComboGiaoDien(JComboBox<String> comboGiaoDien) {
        this.comboGiaoDien = comboGiaoDien;
    }
}
