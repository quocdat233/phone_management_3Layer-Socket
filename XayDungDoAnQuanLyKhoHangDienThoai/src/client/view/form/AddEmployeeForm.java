package client.view.form;

import javax.swing.*;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.toedter.calendar.JDateChooser;


import java.awt.*;


public class AddEmployeeForm extends JDialog {
    private JPanel panelTop, panelBottom;
    private JPanel panelContainer, panelContent;
    private JButton btnAdd, btnCancel;
    private JTextField txtEmployeeName;
    private JTextField txtEmployeePhone;
    private JTextField txtEmployeeEmail;
    private JPanel panelHeader;
    private JPanel panelFooter;
    private JPanel panelGender;
    private JPanel panelDate;
    private JRadioButton RBtnMale;
    private JRadioButton RBtnFemale;
    private JPanel panelGrid;
    private JPanel panelNhom;
    private JDateChooser dateChooser;

    public AddEmployeeForm() {
        setTitle("Thêm nhân viên");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());


        // Panel Top
        panelTop = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("THÊM NHÂN VIÊN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTop.setBackground(Color.decode("#187AC3"));
        panelTop.add(lblTitle, BorderLayout.CENTER);

        // Panel Container
        panelContainer = new JPanel(new BorderLayout());

        // Panel Container/Panel Header
        panelHeader = new JPanel(new BorderLayout());

        // Panel Container/Panel Header/Panel Content
        panelContent = new JPanel(new GridLayout(6, 1, 17, 0));
        panelContent.setBorder(BorderFactory.createEmptyBorder(0, 20, 25, 20));

        panelContent.add(new JLabel("Họ và tên"));
        txtEmployeeName = new JTextField();
        panelContent.add(txtEmployeeName);

        panelContent.add(new JLabel("Email"));
        txtEmployeeEmail = new JTextField();
        panelContent.add(txtEmployeeEmail);

        panelContent.add(new JLabel("Số điện thoại"));
        txtEmployeePhone = new JTextField();
        panelContent.add(txtEmployeePhone);

        // Panel Container/Panel Footer
        panelFooter = new JPanel(new BorderLayout());
        panelFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel Container/Panel Footer/Panel Grid
        panelGrid = new JPanel(new GridLayout(2, 1, 0, 30));
        panelGrid.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Panel Container/Panel Footer/Panel Grid/Panel Gender
        panelGender = new JPanel();
        panelGender.setLayout(new BoxLayout(panelGender, BoxLayout.Y_AXIS));

        JLabel lblGender = new JLabel("Giới tính");
        lblGender.setAlignmentX(Component.LEFT_ALIGNMENT); // canh trái

        panelNhom = new JPanel();
        panelNhom.setLayout(new BoxLayout(panelNhom, BoxLayout.X_AXIS));
        panelNhom.setAlignmentX(Component.LEFT_ALIGNMENT); // canh trái

        RBtnMale = new JRadioButton("Nam");
        RBtnFemale = new JRadioButton("Nữ");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(RBtnMale);
        genderGroup.add(RBtnFemale);
        panelNhom.add(RBtnMale);
        panelNhom.add(Box.createHorizontalStrut(140));
        panelNhom.add(RBtnFemale);

        // Add vào gender panel
        panelGender.add(lblGender);
//        panelGender.add(Box.createVerticalStrut(3));
        panelGender.add(panelNhom);

        // Panel Container/Panel Footer/Panel Grid/Panel Date
        panelDate = new JPanel();
        panelDate.setLayout(new BoxLayout(panelDate, BoxLayout.Y_AXIS));

        JLabel lblDate = new JLabel("Ngày sinh");
        lblDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateChooser = new JDateChooser();
        dateChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
//        dateChooser.setPreferredSize(new Dimension(200, 30));
        dateChooser.setDateFormatString("dd/MM/yyyy"); // định dạng hiển thị

        panelDate.add(lblDate);
        panelDate.add(Box.createVerticalStrut(4));
        panelDate.add(dateChooser);



        // Buttons
        panelBottom = new JPanel();
        panelBottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        btnAdd = new JButton("Thêm nhân viên");
        btnAdd.setBorderPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 13));
        btnAdd.setPreferredSize(new Dimension(150, 40));
        btnAdd.setBackground(new Color(51, 142, 193));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBorderPainted(false);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setBackground(new Color(197, 79, 85));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBottom.add(btnAdd);
        panelBottom.add(btnCancel);


        // Thêm các thành phần
        panelHeader.add(panelContent, BorderLayout.CENTER);
        panelGrid.add(panelGender);
        panelGrid.add(panelDate);
        panelFooter.add(panelGrid, BorderLayout.NORTH);
        panelContainer.add(panelHeader, BorderLayout.CENTER);
        panelContainer.add(panelFooter, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);
        add(panelContainer, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JTextField getTxtEmployeeName() {
        return txtEmployeeName;
    }

    public JTextField getTxtEmployeePhone() {
        return txtEmployeePhone;
    }

    public JTextField getTxtEmployeeEmail() {
        return txtEmployeeEmail;
    }

    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public JRadioButton getRBtnMale() {
        return RBtnMale;
    }

    public JRadioButton getRBtnFemale() {
        return RBtnFemale;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            new AddEmployeeForm().setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}