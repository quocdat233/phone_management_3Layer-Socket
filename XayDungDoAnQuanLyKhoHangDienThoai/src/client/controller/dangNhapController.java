package client.controller;

import client.helper.BCrypt;
import client.VKULogin;
import client.view.shared.Toast;
import com.formdev.flatlaf.FlatIntelliJLaf;
import network.SocketManager;
import server.DAO.TaiKhoanDAO;
import server.DAO.dangNhapDAO;
import shared.models.TaiKhoan;
import shared.models.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dangNhapController {
    private VKULogin loginView;

    public dangNhapController(VKULogin loginView) {
        this.loginView = loginView;
        initController();
    }

    private void initController() {
        loginView.getPnlDangNhap().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    pnlLogInMousePressed(e);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(VKULogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void checkLogin() {
        String tenDangNhap = loginView.getTxtTaiKhoan().getText().trim();
        String matKhau = String.valueOf(loginView.getTxtMatKhau().getPassword()).trim();

        if (tenDangNhap.equals("") || matKhau.equals("")) {
            JOptionPane.showMessageDialog(loginView, "Vui lòng nhập thông tin đầy đủ", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        } else {
            TaiKhoan tk = TaiKhoanDAO.getInstance().selectByUser(tenDangNhap);
            if (tk == null) {
                JOptionPane.showMessageDialog(loginView, "Tài khoản của bạn không tồn tại trên hệ thống", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            } else {
                if (tk.getTrangthai() == 0) {
                    JOptionPane.showMessageDialog(loginView, "Tài khoản của bạn đang bị khóa", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (BCrypt.checkpw(matKhau, tk.getMatkhau())) {
                        try {
                            NhanVien nv = dangNhapDAO.layThongTinNhanVien(tk.getManv());
                            ImageIcon successIcon = new ImageIcon(getClass().getResource("/images/success.png"));
                            new Toast(loginView, "Thành công", "Đăng nhập thành công, \nChào " + nv.getHoten() + "!", 1500, successIcon);
                            Timer timer = new Timer(300, e -> {
                                try {
                                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                                    new AppController(nv);
                                    loginView.dispose();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();
                        } catch (Exception ex) {
                            Logger.getLogger(VKULogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Mật khẩu không khớp", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        }
    }

    private void pnlLogInMousePressed(java.awt.event.MouseEvent evt) throws UnsupportedLookAndFeelException {
        checkLogin();
    }
}

