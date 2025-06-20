package client.controller;


import client.view.form.PhieuNhap.DetailsImportView;
import client.view.form.phieuXuat.DetailsExportView;
import client.view.shared.SidebarMenu;
import client.view.views.*;
import client.view.views.ThongKe.ThongKeView;
import shared.models.NhanVien;
import shared.models.TaiKhoan;

public class AppController {
    public TaiKhoan user;
    private SidebarMenu menuTaskbar;
    private MainView mainView;
    private ProductView productView;
    private CustomerView customerView;
    private EmployeeView employeeView;
    private StockView stockView;
    private ImportView importView;
    private ExportView exportView;
    private DetailsImportView details_ImportView;
    private DetailsExportView detailsExportView;
    private SupplierView supplierView;
    private AccountView accountView;
    private ThongKeView thongKeView;
    private DecentralizeView decentralizeView;
    private thuocTinh thuocTinh;
    private ImportController importController;
    private ExportController exportController;
    private NhanVien nhanVien;


    public AppController(NhanVien nhanVien) throws Exception {
        this.nhanVien = nhanVien;
        initViews();
        addViewsToMainPanel();
        importController = new ImportController(importView, details_ImportView,nhanVien,detailsExportView);
        exportController = new ExportController(exportView,detailsExportView,nhanVien,details_ImportView);

        initEventHandlers();
        mainView.setVisible(true);
    }

    public AppController(TaiKhoan user) throws Exception {
        this.user = user;
        initViews();
        addViewsToMainPanel();
        importController = new ImportController(importView, details_ImportView,nhanVien,detailsExportView);
        exportController = new ExportController(exportView,detailsExportView,nhanVien,details_ImportView);
        initEventHandlers();
        mainView.setVisible(true);
    }

    private void initViews() throws Exception {
        mainView = new MainView(nhanVien);
        productView = new ProductView();
        customerView = new CustomerView();
        employeeView = new EmployeeView();
        stockView = new StockView();
        importView = new ImportView();
        exportView = new ExportView();
        details_ImportView = new DetailsImportView(nhanVien);
        detailsExportView = new DetailsExportView(nhanVien);
        supplierView = new SupplierView();
        accountView = new AccountView();
        thongKeView = new ThongKeView();
        decentralizeView = new DecentralizeView();
        thuocTinh = new thuocTinh();
    }

    private void addViewsToMainPanel() {
        mainView.getMainPanel().add(mainView.getContentPanel(), "MainView");
        mainView.getMainPanel().add(productView.getContentPanel(), "ProductView");
        mainView.getMainPanel().add(customerView.getContentPanel(), "CustomerView");
        mainView.getMainPanel().add(employeeView.getContentPanel(), "EmployeeView");
        mainView.getMainPanel().add(stockView.getContentPanel(), "stockView");
        mainView.getMainPanel().add(importView.getContenPanel(), "importView");
        mainView.getMainPanel().add(exportView.getContenPanel(), "exportView");
        mainView.getMainPanel().add(details_ImportView.getContenPanel(), "details_ImportView");
        mainView.getMainPanel().add(detailsExportView.getContenPanel(), "detailsExportView");
        mainView.getMainPanel().add(supplierView.getContentPanel(), "SupplierView");
        mainView.getMainPanel().add(accountView.getContentPanel(), "AccountView");
        mainView.getMainPanel().add(thongKeView.getMainPanel(), "ThongKeView");
        mainView.getMainPanel().add(decentralizeView.getContentPanel(), "DecentralizeView");
        mainView.getMainPanel().add(thuocTinh.getContentPanel(), "thuocTinh");


    }

    private void initEventHandlers() {
        // Trang chủ
        mainView.getSidebarMenu().getBtnHome().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "MainView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnHome());
        });


        // Sản phẩm
        mainView.getSidebarMenu().getBtnProduct().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "ProductView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnProduct());

        });

        // Khách hàng
        mainView.getSidebarMenu().getBtnCustomer().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "CustomerView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnCustomer());
        });

        // Nhân viên
        mainView.getSidebarMenu().getBtnEmployee().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "EmployeeView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnEmployee());

        });

        // Nhà cung cấp
        mainView.getSidebarMenu().getBtnSupplier().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "SupplierView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnSupplier());
        });

        // Tài khoản
        mainView.getSidebarMenu().getBtnAccount().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "AccountView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnAccount());
        });

        // Thống kê
        mainView.getSidebarMenu().getBtnStatistic().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "ThongKeView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnStatistic());
        });

        // Phân quyền
        mainView.getSidebarMenu().getBtnDecentralization().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "DecentralizeView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnDecentralization());
        });

        // Khu vực kho
        mainView.getSidebarMenu().getBtnStock().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "stockView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnStock());
        });

        // Phiếu nhập
        mainView.getSidebarMenu().getBtnImport().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "importView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnImport());
        });

        // Phiếu xuất
        mainView.getSidebarMenu().getBtnExport().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "exportView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnExport());
        });

        //FORM_ADD_IMPORTVIEW
        importView.getTopPanel().getBtnAdd().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "details_ImportView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnImport());
        });
        exportView.getTopPanel().getBtnAdd().addActionListener(e -> {
            mainView.getCardLayout().show(mainView.getMainPanel(), "detailsExportView");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnExport());
        });
        mainView.getSidebarMenu().getBtnProductInfo().addActionListener(e ->{
            mainView.getCardLayout().show(mainView.getMainPanel(),"thuocTinh");
            mainView.getSidebarMenu().setActiveButton(mainView.getSidebarMenu().getBtnProductInfo());
        });

    }


}

