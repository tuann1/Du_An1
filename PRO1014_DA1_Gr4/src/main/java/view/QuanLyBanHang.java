package view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.BadElementException;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.ChiTietSanPham;
import model.DanhMuc;

import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.KhuyenMai;
import model.Mau;
import model.NSX;
import model.NhanVien;
import service.IHoaDonService;
import service.impl.CTSPImpl;
import service.impl.HoaDonBanImpl;
import service.impl.KhuyenMaiImpl;
import service.impl.NhanVienImpl;
import utilities.Auth;
import utilities.DataGlobal;
import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.openide.util.Exceptions;
import pagination.EventPagination;
import pagination.Page;
import pagination.style.PaginationItemRenderStyle1;
import service.impl.HDCTImpl;
import service.ICTSPService;
import service.IHDCTService;
import utilities.Clock;
import utilities.RunText;

public class QuanLyBanHang extends javax.swing.JPanel implements Runnable, ThreadFactory {

    DefaultTableModel hoaDonModel = new DefaultTableModel();
    DefaultTableModel chiTietSpModel = new DefaultTableModel();
    DefaultTableModel gioHangModel = new DefaultTableModel();
    ICTSPService cTSPService = new CTSPImpl();
    List<ChiTietSanPham> listCtSp = new ArrayList<>();
    List<HoaDonBan> listHoaDonBan = new ArrayList<>();
    List<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<>();
    IHoaDonService hoaDonBanService = new HoaDonBanImpl();
    DefaultComboBoxModel<DanhMuc> cbDM = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<ChatLieu> cbCL = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Mau> cbMau = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<NSX> cbNSX = new DefaultComboBoxModel<>();
    IHDCTService hDCTService = new HDCTImpl();
    int soLuong = 0;
    //  RunText rt = new RunText(Name);

    List<HoaDonChiTiet> list;
    Integer pageSize = 5;
    Integer totalProducts = 0;
    private Page paging = new Page();

    public QuanLyBanHang() {
        initComponents();
        chiTietSpModel = (DefaultTableModel) tblSanPham.getModel();
        hoaDonModel = (DefaultTableModel) tblHoaDon.getModel();
        gioHangModel = (DefaultTableModel) tblGioHang.getModel();
        listCtSp = cTSPService.getAll();
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        loadTableHoaDon(listHoaDonBan);
        // loadTableCTSP(listCtSp);
        loadPagination();
        pagination.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination.setPagegination(1, paging.getTotalPage());
        btnThanhToan.setEnabled(false);
        Clock cl = new Clock(lbnClock);
        cl.start();

    }

    public void loadPagination() {
        String search = txtSearch.getText();

        totalProducts = cTSPService.filterProductBanHang(search).size();

        int total = (int) Math.ceil(totalProducts / pageSize) + 1;
        paging.setTotalPage(total);
        pagination.setPagegination(1, paging.getTotalPage());

        if (paging.getTotalPage() < paging.getCurrent()) {
            pagination.setPagegination(paging.getTotalPage(), paging.getTotalPage());
            loadTable(cTSPService.pageListBanHang(paging.getTotalPage(), pageSize, search));
        } else {
            pagination.setPagegination(paging.getCurrent(), paging.getTotalPage());
            loadTable(cTSPService.pageListBanHang(paging.getCurrent(), pageSize, search));
        }

        pagination.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadTable(cTSPService.pageListBanHang(page, pageSize, search));
                paging.setCurrent(page);
            }
        });
    }

    public void loadTable(List<ChiTietSanPham> ctsp) {
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0);

        for (ChiTietSanPham x : ctsp) {
            Object[] rowData = {
                x.getMa(), x.getSanPham().getTenSP(),
                x.getDanhMuc().getTenDM(), x.getChatLieu().getTenCL(),
                x.getMauSac().getTenMau(), x.getNhaSanXuat().getTenNSX(),
                x.getSoLuongTon(), x.getGiaBan(),
                x.getMoTa()
            };
            dtm.addRow(rowData);
        }
    }

//    private void loadTableCTSP(List<ChiTietSanPham> list) {
//        chiTietSpModel.setNumRows(0);
//        for (ChiTietSanPham ctsp : list) {
//            chiTietSpModel.addRow(new Object[]{
//                ctsp.getMa(),
//                ctsp.getSanPham().getTenSP(),
//                ctsp.getNhaSanXuat().getTenNSX(),
//                ctsp.getDanhMuc().getTenDM(),
//                ctsp.getChatLieu().getTenCL(),
//                ctsp.getMauSac().getTenMau(),
//                ctsp.getSoLuongTon(),
//                ctsp.getGiaBan(),
//                ctsp.getMoTa()
//            });
//        }
//    }
    private void loadTableHoaDon(List<HoaDonBan> list) {
        hoaDonModel.setNumRows(0);
        for (HoaDonBan hd : list) {
            hoaDonModel.addRow(new Object[]{
                hd.getMaHDB(), hd.getNhanVien().getTenNV(), hd.getKhachHang().getTenKH(), hd.getNgayTao(), hd.getNgayThanhToan()
            });
        }
    }

//    private void loadTableGioHang(List<GioHangChiTiet> list) {
//        gioHangModel.setNumRows(0);
//        double giaBan = 0;
//        double donGia = 0;
//        double tongTien = 0;
//
//        for (GioHangChiTiet ghct : list) {
//            giaBan = (ghct.getChiTietSanPham().getGiaBan()).doubleValue();
//            donGia = giaBan * ghct.getSoLuong();
//            tongTien = tongTien + donGia;
//            gioHangModel.addRow(new Object[]{
//                ghct.getChiTietSanPham().getMa(), ghct.getChiTietSanPham().getSanPham().getTenSP(), ghct.getSoLuong(), ghct.getChiTietSanPham().getGiaBan(),
//                donGia
//            }
//            );
//        }
//        lbnTongTien.setText(String.valueOf(tongTien));
//        lbnTongTien.setForeground(Color.red);
//    }
    void loadGioHangByChiTietHoaDon(List<HoaDonChiTiet> list) {
        gioHangModel.setNumRows(0);
        double giaBan = 0;
        double donGia = 0;
        double tongTien = 0;

        for (HoaDonChiTiet hdct : list) {
            giaBan = (hdct.getChiTietSanPham().getGiaBan()).doubleValue();
            donGia = giaBan * hdct.getSoLuong();
            tongTien = tongTien + donGia;
            gioHangModel.addRow(new Object[]{
                hdct.getChiTietSanPham().getMa(), hdct.getChiTietSanPham().getSanPham().getTenSP(), hdct.getSoLuong(), hdct.getChiTietSanPham().getGiaBan(),
                donGia
            }
            );
        }
        lbnTongTien.setText(String.valueOf(tongTien));
        lbnTongTien.setForeground(Color.red);
    }

    void searchByName(String name) {
        DefaultTableModel tb = (DefaultTableModel) tblSanPham.getModel();
        tb.setRowCount(0);

        listCtSp = cTSPService.searchByName(name);
        if (listCtSp.size() == 0) {
            return;
        }
        for (ChiTietSanPham ctsp : listCtSp) {
            if (ctsp.getSanPham().getTenSP().toLowerCase().contains(txtSearch.getText().trim().toLowerCase())) {
                tb.addRow(new Object[]{
                    ctsp.getMa(),
                    ctsp.getSanPham().getTenSP(),
                    ctsp.getNhaSanXuat().getTenNSX(),
                    ctsp.getDanhMuc().getTenDM(),
                    ctsp.getChatLieu().getTenCL(),
                    ctsp.getMauSac().getTenMau(),
                    ctsp.getSoLuongTon(),
                    ctsp.getGiaBan(),
                    ctsp.getMoTa()
                });
            }
        }
    }

    public void clear() {
        lbnMaKh.setText("");
        lbnTenKh.setText("");
        lbnMaHD.setText("");
        lbnNgayTao.setText("");
        lbnKhuyenMai.setText("");
        lbnTienThua.setText("0");
        lbnTongTien.setText("0");
        txtTienKhachDua.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnThemVaoGioHang = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        pagination = new pagination.Pagination();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        panelWebcam = new javax.swing.JPanel();
        btnCameraOff = new javax.swing.JButton();
        btnCameraOn = new javax.swing.JButton();
        cbxTrangThaiHoaDon = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        btnXoaSp = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbnMaKh = new javax.swing.JLabel();
        lbnTenKh = new javax.swing.JLabel();
        btnThayDoi = new javax.swing.JButton();
        btnChon = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbnMaHD = new javax.swing.JLabel();
        lbnNgayTao = new javax.swing.JLabel();
        lbnTongTien = new javax.swing.JLabel();
        lbnTienThua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnTaoHoaDonw = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbnKhuyenMai = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        lbnClock = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1470, 781));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jPanel7ComponentAdded(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel8.text_1")); // NOI18N

        txtSearch.setText(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.txtSearch.text_1")); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnThemVaoGioHang.setBackground(new java.awt.Color(204, 204, 204));
        btnThemVaoGioHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnThemVaoGioHang, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnThemVaoGioHang.text_1")); // NOI18N
        btnThemVaoGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoGioHangActionPerformed(evt);
            }
        });

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Nsx", "Danh Mục", "Chất liệu", "Màu", "Số Lượng Tồn", "Đơn Giá", "Mô Tả"
            }
        ));
        tblSanPham.setRowHeight(30);
        jScrollPane2.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title0")); // NOI18N
            tblSanPham.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title1")); // NOI18N
            tblSanPham.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title2")); // NOI18N
            tblSanPham.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title3")); // NOI18N
            tblSanPham.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title4")); // NOI18N
            tblSanPham.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title5")); // NOI18N
            tblSanPham.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title6")); // NOI18N
            tblSanPham.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title7")); // NOI18N
            tblSanPham.getColumnModel().getColumn(8).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblSanPham.columnModel.title8")); // NOI18N
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemVaoGioHang)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(381, 381, 381)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(417, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnThemVaoGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel7.text_1")); // NOI18N

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Tên Nhân Viên", "Tên Khách Hàng", "Ngày Tạo", "Ngày Thanh Toán"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblHoaDon.columnModel.title0")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblHoaDon.columnModel.title1")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblHoaDon.columnModel.title2")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblHoaDon.columnModel.title3")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.tblHoaDon.columnModel.title4")); // NOI18N
        }

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel14.text_1")); // NOI18N

        panelWebcam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCameraOff.setBackground(new java.awt.Color(204, 204, 204));
        btnCameraOff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCameraOff, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnCameraOff.text")); // NOI18N
        btnCameraOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCameraOffActionPerformed(evt);
            }
        });

        btnCameraOn.setBackground(new java.awt.Color(204, 204, 204));
        btnCameraOn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCameraOn, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnCameraOn.text")); // NOI18N
        btnCameraOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCameraOnActionPerformed(evt);
            }
        });

        cbxTrangThaiHoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hóa đơn chờ  ", "Hóa đơn đã thanh toán ", "Hóa đơn hủy " }));
        cbxTrangThaiHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTrangThaiHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(cbxTrangThaiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCameraOn)))
                                .addGap(12, 12, 12)
                                .addComponent(btnCameraOff)))
                        .addGap(18, 18, 18)
                        .addComponent(panelWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(cbxTrangThaiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(panelWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCameraOff, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCameraOn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblGioHang.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên Sản phẩm", "Số lượng ", "Đơn giá ", "Thành tiền"
            }
        ));
        jScrollPane4.setViewportView(tblGioHang);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel16.text_1")); // NOI18N

        btnXoaSp.setBackground(new java.awt.Color(204, 204, 204));
        btnXoaSp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnXoaSp, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnXoaSp.text_1")); // NOI18N
        btnXoaSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSpActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(204, 204, 204));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton9, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jButton9.text_1")); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnXoaSp)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnXoaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel1.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel2.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnMaKh, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnMaKh.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnTenKh, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnTenKh.text_1")); // NOI18N

        btnThayDoi.setBackground(new java.awt.Color(204, 204, 204));
        btnThayDoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnThayDoi, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnThayDoi.text_1")); // NOI18N
        btnThayDoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThayDoiActionPerformed(evt);
            }
        });

        btnChon.setBackground(new java.awt.Color(204, 204, 204));
        btnChon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnChon, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnChon.text_1")); // NOI18N
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbnMaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbnTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThayDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbnMaKh)
                    .addComponent(btnChon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbnTenKh)
                    .addComponent(btnThayDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.MatteBorder(null));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel3.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel4.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel5.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel6.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel13.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnMaHD, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnMaHD.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnNgayTao, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnNgayTao.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnTongTien, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnTongTien.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbnTienThua, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnTienThua.text_1")); // NOI18N

        txtTienKhachDua.setText(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.txtTienKhachDua.text_1")); // NOI18N
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jButton1.text_1")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jButton4.text_1")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(204, 204, 204));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnThanhToan, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnThanhToan.text_1")); // NOI18N
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnTaoHoaDonw.setBackground(new java.awt.Color(204, 204, 204));
        btnTaoHoaDonw.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnTaoHoaDonw, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnTaoHoaDonw.text_1")); // NOI18N
        btnTaoHoaDonw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonwActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jButton2.text_1")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(lbnKhuyenMai, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnKhuyenMai.text_1")); // NOI18N

        btnOk.setBackground(new java.awt.Color(204, 204, 204));
        btnOk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnOk, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.btnOk.text_1")); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel17.text")); // NOI18N

        lbnClock.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbnClock.setForeground(new java.awt.Color(255, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(lbnClock, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.lbnClock.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel10.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel11.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jLabel12.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addComponent(jLabel6))
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbnTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lbnTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addComponent(lbnNgayTao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbnMaHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDonw)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbnKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbnClock, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbnMaHD))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbnNgayTao))
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbnTongTien)
                    .addComponent(jLabel10))
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbnTienThua)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(lbnClock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTaoHoaDonw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbnKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(QuanLyBanHang.class, "QuanLyBanHang.jPanel2.TabConstraints.tabTitle_1"), jPanel2); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1570, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1563, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        if (lbnTongTien.getText().equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(this, "Vui lòng thử lại");
            return;
        }
        if (DataGlobal.getKhuyenMai() == null) {
            return;
        }
        if (txtTienKhachDua.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa");
            return;
        }
        lbnKhuyenMai.setText(DataGlobal.getKhuyenMai().getTenkm());
        double tongTien = Double.valueOf(lbnTongTien.getText());
        double phamTramGiam = DataGlobal.getKhuyenMai().getPhantramgiam();
        double tongTienSauKM = tongTien - (tongTien * phamTramGiam) / 100;
        lbnTongTien.setText(String.valueOf(tongTienSauKM));
        lbnTienThua.setText(String.valueOf(Double.valueOf(txtTienKhachDua.getText()) - tongTienSauKM));
        btnOk.setEnabled(false);
    }//GEN-LAST:event_btnOkActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double totalHoaDon = Double.valueOf(lbnTongTien.getText());

        List<KhuyenMai> list = new ArrayList<>();
        list = new KhuyenMaiImpl().getKhuyenMaiMap(totalHoaDon);
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this, "Tổng hóa đơn của bạn chưa đạt giá trị tối thiểu ");
            return;
        }

        DataGlobal.setTotalHoaDon(totalHoaDon);
        new DsKhuyenMaiOk().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTaoHoaDonwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonwActionPerformed

        HoaDonBan hdb = new HoaDonBan();
        NhanVien nhanVien = new NhanVien();
        nhanVien = Auth.getNv();
        if (nhanVien == null) {
            nhanVien = new NhanVienImpl().getNhanVien("NV00");
        }
        KhachHang khachHang = new KhachHang();
        khachHang = Auth.getKh();
        if (khachHang == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
            return;
        }
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        // JOptionPane.showMessageDialog(this, listHoaDonBan);
        for (HoaDonBan hoaDonBan : listHoaDonBan) {
            if (!khachHang.getMaKH().equalsIgnoreCase("KH00")) {
                if (hoaDonBan.getKhachHang().getMaKH().equalsIgnoreCase(khachHang.getMaKH())) {
                    JOptionPane.showMessageDialog(this, "Khách hàng " + khachHang.getTenKH() + "  có 1 hóa đơn chưa thanh toán ! \n Vui lòng thanh toán hóa đơn !");
                    return;
                }
            }
        }

        // ===============================
        // tạo hóa đơn
        String maHD = null;
        listHoaDonBan = hoaDonBanService.getListHoaDonBan();
        int so = 0;
        int hd = 1;
        if (listHoaDonBan.size() == 0) {
            maHD = "HD" + hd;
            hd++;
        } else {
            HoaDonBan hoaDonBan = listHoaDonBan.get(0);
            so = (hoaDonBan.getId());
            maHD = "HD" + so;
        }
        hdb.setNhanVien(nhanVien);
        hdb.setKhachHang(khachHang);
        hdb.setMaHDB(maHD);
        hdb.setKhuyenMai(null);
        hdb.setNgayTao(new Date());
        hdb.setNgayThanhToan(null);
        hdb.setNguoiNhan(khachHang.getTenKH());
        hdb.setSdt(khachHang.getSdt());
        hdb.setDiaChi(khachHang.getDiaChi());
        hdb.setTrangThai(1);

        try {
            String result = hoaDonBanService.insert(hdb);
            JOptionPane.showMessageDialog(this, result);

        } catch (Exception e) {
            System.out.println("Lỗi");
        }
        // Insert sản phẩm ở giỏ hàng vào hóa đơn chi tiết - > trạng thái chưa thanh toán
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();

        //        for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
        //            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        //            hoaDonChiTiet.setHoaDonBan(hdb);
        //            hoaDonChiTiet.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
        //            hoaDonChiTiet.setSoLuong(gioHangChiTiet.getSoLuong());
        //            hoaDonChiTiet.setTrangThai(1); // set trạng thái chưa thanh toán
        //            listHDCT.add(hoaDonChiTiet);
        //        }
        for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            hoaDonBanService.addHoaDonChiTiet(hoaDonChiTiet); // thêm vào hóa đơn chi tiết
        }

        // Load lại table
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        loadTableHoaDon(listHoaDonBan);
        lbnMaHD.setText(maHD);
        lbnMaHD.setForeground(Color.red);
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date ngayTao = hdb.getNgayTao();
        sdf.applyPattern("yyyy-MM-dd");
        sdf.format(ngayTao);
        lbnNgayTao.setText(String.valueOf(ngayTao));
        lbnNgayTao.setForeground(Color.red);
        // listGioHangChiTiet.clear();
    }//GEN-LAST:event_btnTaoHoaDonwActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (lbnTongTien.getText().equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(this, "Vui lòng thử lại");
            return;
        }
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 0) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        } else if (cbxTrangThaiHoaDon.getSelectedIndex() == 1) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(2);
        } else {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(0);
        }
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán");
            return;
        }
        HoaDonBan hdb = listHoaDonBan.get(row);
        listHoaDonChiTiet = hDCTService.getById(hdb.getId());
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            String maCTSP = hdct.getChiTietSanPham().getMa();
            int soLuongMua = hdct.getSoLuong();
            if (soLuongMua > cTSPService.getSoLuongSpByMaCTSP(maCTSP)) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ ! \n "
                        + "Vui lòng chọn sản phẩm khác !");
                return;
            }

        }

        hoaDonBanService.updateTrangThaiHoaDonChiTiet(hdb.getId(), 2); // update trạng thái hóa đơn chi tiết - > đã thanh toán

        List<HoaDonChiTiet> listHDCT = hDCTService.getById(hdb.getId());
        for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            cTSPService.updateSoLuongCTSP(hoaDonChiTiet.getChiTietSanPham().getMa(), hoaDonChiTiet.getSoLuong()); // cập nhật số lượng tồn của chi tiết sản phẩm
        }

        Date ngayThanhToan = new Date();
        hdb.setNgayThanhToan(ngayThanhToan);
        KhuyenMai km = DataGlobal.getKhuyenMai();
        if (km == null) {
            km = new KhuyenMaiImpl().getKhuyenMaiByMa("KM00");
        }
        hdb.setKhuyenMai(km);
        hdb.setTrangThai(2);
        hoaDonBanService.update(hdb, hdb.getId()); // Update khuyến mãi + Trạng thái hóa đơn
        DataGlobal.setIdHoaDon(hdb.getId()); // luu id hóa đơn vừa thanh toán vào biến
        int oup = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn ?");
        if (oup == JOptionPane.YES_OPTION) {
            try {
                outputPDF();
                JOptionPane.showMessageDialog(this, "In hóa đơn thành công : " + hdb.getMaHDB());
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (BadElementException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công : " + hdb.getMaHDB());

        loadTableHoaDon(hoaDonBanService.getListByTrangThai(1)); // load lại hóa đơn
        loadGioHangByChiTietHoaDon(new ArrayList<>());
        loadPagination();
btnOk.setEnabled(true);
        clear();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        btnOk.setEnabled(true);
        clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn ");
            return;
        }
        if (Auth.getKh() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng !");
            return;
        }
        HoaDonBan hoaDonBan = listHoaDonBan.get(row);
        hoaDonBanService.updateTrangThaiHoaDon(hoaDonBan.getId(), 0); // cập nhật trạng thái hóa đơn
        hoaDonBanService.updateTrangThaiHoaDonChiTiet(hoaDonBan.getId(), 0); // cập nhật trạng thái hóa đơn chi tiết
        //        GioHang gioHang = gioHangService.getGioHangByKH(Auth.getKh().getId());
        //        gioHangService.updateTrangThaiGHCT(gioHang.getId(), 0); // cập nhật trạng thái giỏ hàng chi tiết
        JOptionPane.showMessageDialog(this, "Thành công " + hoaDonBan.getMaHDB());
        loadTableHoaDon(hoaDonBanService.getListByTrangThai(1));
        loadGioHangByChiTietHoaDon(new ArrayList<>());
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:
        String tienKhachDua = txtTienKhachDua.getText();

        if (txtTienKhachDua.getText().trim().isBlank()) {
            lbnTienThua.setText("");
            return;
        }
        try {
            double tienKH = Double.valueOf(tienKhachDua);
            double tongTien = Double.valueOf(lbnTongTien.getText());
            double tienThua = tienKH - tongTien;
            lbnTienThua.setText(String.valueOf(tienThua));
            if (lbnTienThua.getText().trim().isEmpty()) {
                btnThanhToan.setEnabled(false);
            } else {
                btnThanhToan.setEnabled(true);
            }
            if (tienThua < 0) {
                btnThanhToan.setEnabled(false);
            } else {
                btnThanhToan.setEnabled(true);
            }
        } catch (Exception e) {
            System.out.println("Lỗi");
            return;
            // e.printStackTrace();
        }
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        DsKH dkh = new DsKH();
        dkh.setVisible(true);
    }//GEN-LAST:event_btnChonActionPerformed

    private void btnThayDoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThayDoiActionPerformed
        // TODO add your handling code here:
        KhachHang kh = Auth.getKh();
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng !");
            return;
        }
        System.out.println(kh);
        lbnMaKh.setText(kh.getMaKH());
        lbnMaKh.setForeground(Color.red);
        lbnTenKh.setText(kh.getTenKH());
        lbnTenKh.setForeground(Color.red);

        //        GioHang gioHang = gioHangService.getGioHangByKH(kh.getId());
        //        listGioHangChiTiet = gioHangService.getGioHangChiTiet(gioHang.getId());
        //        loadTableGioHang(listGioHangChiTiet);
        //        int row = tblHoaDon.getSelectedRow();
        //        if (row == -1) {
        //            return;
        //        }
        //        HoaDonBan hdb = listHoaDonBan.get(row);
        //        hdb.setKhachHang(kh);
        //
        //        hoaDonBanService.updateKH(hdb);
        //        System.out.println("Thành công ");
        //        loadTableHoaDon(hoaDonBanService.getListHoaDonBan());
    }//GEN-LAST:event_btnThayDoiActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int rowHD = tblHoaDon.getSelectedRow();
        if (rowHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn ");
            return;
        }
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        HoaDonBan hdb = listHoaDonBan.get(rowHD);
        //        int row = tblGioHang.getSelectedRow();
        //        if (row == -1) {
        //            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
        //            return;
        //        }
        listHoaDonChiTiet = hDCTService.getById(hdb.getId());
        //HoaDonChiTiet hdct = listHoaDonChiTiet.get(row);
        if (listHoaDonChiTiet.size() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống");
            return;
        }

        for (HoaDonChiTiet hdct1 : listHoaDonChiTiet) {
            hoaDonBanService.deleteHoaDonCT(hdct1.getChiTietSanPham().getId());
        }
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        loadGioHangByChiTietHoaDon(hDCTService.getById(hdb.getId()));
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnXoaSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSpActionPerformed
        // TODO add your handling code here:

        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int rowHD = tblHoaDon.getSelectedRow();
        if (rowHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn ");
            return;
        }
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        HoaDonBan hdb = listHoaDonBan.get(rowHD);
        int row = tblGioHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }
        listHoaDonChiTiet = hDCTService.getById(hdb.getId());
        HoaDonChiTiet hdct = listHoaDonChiTiet.get(row);
        String m = null;
        int sl = 0;
        try {
            m = JOptionPane.showInputDialog("Số sản phẩm muốn xóa :");
            sl = Integer.valueOf(m);
            // System.out.println(soLuong);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng số");
            return;
        } //get số lượng
        int soLuongMua = (int) tblGioHang.getValueAt(row, 2);
        if (sl > soLuongMua) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá");
            return;
        }
        if (soLuongMua == 1) {
            hoaDonBanService.deleteHoaDonCT(hdct.getChiTietSanPham().getId());
        }
        hoaDonBanService.updateSoLuongHDCT(hdct.getChiTietSanPham().getId(), soLuongMua - sl);
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        loadGioHangByChiTietHoaDon(hDCTService.getById(hdct.getHoaDonBan().getId()));
    }//GEN-LAST:event_btnXoaSpActionPerformed

    private void cbxTrangThaiHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTrangThaiHoaDonActionPerformed
        // TODO add your handling code here:
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 0) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        } else if (cbxTrangThaiHoaDon.getSelectedIndex() == 1) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(2);
        } else {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(0);
        }
        loadTableHoaDon(listHoaDonBan);
    }//GEN-LAST:event_cbxTrangThaiHoaDonActionPerformed

    private void btnCameraOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCameraOnActionPerformed
        initWebCam();
    }//GEN-LAST:event_btnCameraOnActionPerformed

    private void btnCameraOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCameraOffActionPerformed
        webcam.close();
    }//GEN-LAST:event_btnCameraOffActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // load list hóa đơn theo combobox
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 0) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        } else if (cbxTrangThaiHoaDon.getSelectedIndex() == 1) {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(2);
        } else {
            listHoaDonBan = hoaDonBanService.getListByTrangThai(0);
        }

        int row = tblHoaDon.getSelectedRow();
        HoaDonBan hoaDonBan = listHoaDonBan.get(row); // get ra 1 dối tượng hóa đơn -> getid
        KhachHang khachHang = hoaDonBan.getKhachHang();
        Auth.setKh(khachHang);

        listHoaDonChiTiet = hDCTService.getById(hoaDonBan.getId());
        loadGioHangByChiTietHoaDon(listHoaDonChiTiet);

        // lbnTongTien.setText("00");
        lbnMaHD.setText(hoaDonBan.getMaHDB());
        lbnNgayTao.setText(String.valueOf(hoaDonBan.getNgayTao()));
        lbnMaHD.setForeground(Color.red);
        lbnNgayTao.setForeground(Color.red);
        lbnMaKh.setText(hoaDonBan.getKhachHang().getMaKH());
        lbnTenKh.setText(hoaDonBan.getKhachHang().getTenKH());
        lbnMaKh.setForeground(Color.red);
        lbnTenKh.setForeground(Color.red);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void jPanel7ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel7ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7ComponentAdded

    private void btnThemVaoGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoGioHangActionPerformed
        addGH();
    }//GEN-LAST:event_btnThemVaoGioHangActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        loadPagination();
    }//GEN-LAST:event_txtSearchKeyReleased

    void addGH() {
        if (cbxTrangThaiHoaDon.getSelectedIndex() == 1 || cbxTrangThaiHoaDon.getSelectedIndex() == 2) {
            JOptionPane.showMessageDialog(this, "Hành động không được cho phép !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        listHoaDonBan = hoaDonBanService.getListByTrangThai(1);
        int rowHD = tblHoaDon.getSelectedRow(); // get ra 1 hóa đơn 
        if (rowHD == -1) {
            JOptionPane.showMessageDialog(this, "Chọn hóa đơn");
            return;
        }
        HoaDonBan hoaDonBan = listHoaDonBan.get(rowHD);
        //  HoaDonChiTiet hdct = hoaDonBanService.getHoaDonChiTietByIdHD(hoaDonBan.getId());
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thêm !", "ERORR", JOptionPane.ERROR_MESSAGE);
            return;
        }
//        ChiTietSanPham ctsp = new ChiTietSanPham();
//        ctsp = listCtSp.get(row);
        listCtSp = cTSPService.pageListBanHang(paging.getCurrent(), pageSize, txtSearch.getText());
        ChiTietSanPham ctsp = listCtSp.get(row);
        int soLuong, soLuongTon = 0;

        String m = JOptionPane.showInputDialog("Số sản phẩm muốn mua :");
        try {
            soLuong = Integer.valueOf(m);
            System.out.println(soLuong);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng số");
            return;
        } //get số lượng

        soLuongTon = (int) tblSanPham.getValueAt(row, 6);
        if (soLuong > soLuongTon || soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0 & Số lượng < Số lượng tồn ");
            return;
        } // check input

        listHoaDonChiTiet = hDCTService.getById(hoaDonBan.getId());

        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            if (hdct.getChiTietSanPham().getMa().equalsIgnoreCase(ctsp.getMa())) {
                hdct.setSoLuong(hdct.getSoLuong() + soLuong);
            }
            if (hdct.getSoLuong() > soLuongTon) {
                JOptionPane.showMessageDialog(this, "Vượt quá số lượng");
                return;
            }
        }
        //================================================================================            
        if (listHoaDonChiTiet.size() == 0) {
            try {
                // HoaDonBan hoaDonBan = listHoaDonBan.get(rowHD);
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDonBan(hoaDonBan);
                hoaDonChiTiet.setChiTietSanPham(ctsp);
                hoaDonChiTiet.setSoLuong(soLuong);
                hoaDonChiTiet.setDonGia(ctsp.getGiaBan());
                hoaDonChiTiet.setTrangThai(1);
                hoaDonBanService.addHoaDonChiTiet(hoaDonChiTiet);
                JOptionPane.showMessageDialog(this, "Thành công !");

            } catch (Exception e) {
                System.out.println("Lỗi");
                return;
            }
        }

        boolean isValid = true;
        boolean update = true;
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            if (hdct.getChiTietSanPham().getMa().equalsIgnoreCase(ctsp.getMa())) {
                int sl = hdct.getSoLuong();
                //  sl = (hdct.getSoLuong() + soLuong);             
                hoaDonBanService.updateSoLuongHDCT(hdct.getChiTietSanPham().getId(), sl);
                update = false;
                if (sl > (int) tblSanPham.getValueAt(row, 6)) {
                    JOptionPane.showMessageDialog(this, "Vượt quá số lượng !", "ERORR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else {
                isValid = false;
            }
        }
        if (isValid == false && update == true) {
            try {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDonBan(hoaDonBan);
                hoaDonChiTiet.setChiTietSanPham(ctsp);
                hoaDonChiTiet.setSoLuong(soLuong);
                hoaDonChiTiet.setDonGia(ctsp.getGiaBan());
                hoaDonChiTiet.setTrangThai(1);
                hoaDonBanService.addHoaDonChiTiet(hoaDonChiTiet);
                //listHDCT.add(hoaDonChiTiet);
                JOptionPane.showMessageDialog(this, "Thành công !");
            } catch (Exception e) {
                System.out.println("Lỗi");
                return;
            }
        }

        loadGioHangByChiTietHoaDon(hDCTService.getById(hoaDonBan.getId()));

    }

    private Webcam webcam = null;
    private WebcamPanel webPanel = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    void initWebCam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        webPanel = new WebcamPanel(webcam);
        webPanel.setPreferredSize(size);
        webPanel.setFPSDisplayed(true);
        webPanel.setMirrored(true);

        panelWebcam.add(webPanel, new AbsoluteConstraints(0, 0, 300, 300));

        executor.execute(this);

    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
            Result res = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                res = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
            }

            if (res != null) {
                try {
                    String ma = res.getText().replace("QRCODE", "");
                    for (int i = 0; i < tblSanPham.getRowCount(); i++) {
                        if (tblSanPham.getValueAt(i, 0).equals(ma)) {
                            tblSanPham.setRowSelectionInterval(i, i);
                            JOptionPane.showMessageDialog(this, "Quét thành công: " + ma);
                            addGH();
                        }
                    }
                } catch (Exception e) {

                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r, "My Thread");
        th.setDaemon(true);
        return th;
    }

    public void outputPDF() throws IOException, BadElementException {
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
            return;
        }

        KhachHang khachHang = Auth.getKh();
        String pathh = (lbnMaHD.getText());
        System.out.println(pathh);
        String path = "D:\\HoaDonThanhToan\\" + pathh + ".pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float col = 280f;
        float columnWidth[] = {col, col};
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidth);
        String file = "src/main/resources/icon/vodien.png";
        ImageData date = ImageDataFactory.create(file);
        Image image = new Image(date);

        table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Shop tui xach Authentication").setFontSize(30f).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add("Ha Noi \n SĐT: 0922505266 - 0389718892")
                .setTextAlignment(TextAlignment.RIGHT).setMarginTop(30f).setMarginBottom(30f).setBorder(Border.NO_BORDER).setMarginRight(10f)
        );

        float colWidth[] = {80, 250, 80, 150};

        Table customerInfor = new Table(colWidth);
        customerInfor.addCell(new Cell(0, 4).add("Hoa don thanh toan").setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        customerInfor.addCell(new Cell(0, 4).add("Thong tin").setBold().setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Khach hang: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add((tblHoaDon.getValueAt(row, 2).toString())).setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("SDT : ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(khachHang.getSdt()).setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Ma hoa don: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(tblHoaDon.getValueAt(row, 0) + "").setBorder(Border.NO_BORDER));

        customerInfor.addCell(new Cell().add("Nhan vien: ").setBorder(Border.NO_BORDER)); //
        customerInfor.addCell(new Cell().add((tblHoaDon.getValueAt(row, 1).toString())).setBorder(Border.NO_BORDER)); //

        float iteamInforColWidth[] = {140, 140, 140, 140};
        Table itemInforTable = new Table(iteamInforColWidth);
        itemInforTable.addCell(new Cell().add("San pham"));
        itemInforTable.addCell(new Cell().add("So luong"));
        itemInforTable.addCell(new Cell().add("Don gia").setTextAlignment(TextAlignment.RIGHT));
        itemInforTable.addCell(new Cell().add("Thanh tien").setTextAlignment(TextAlignment.RIGHT));

        int total = 0;
        int quantitySum = 0;
        // Xuất pdf

        KhuyenMai km = DataGlobal.getKhuyenMai();
        if (km == null) {
            km = new KhuyenMaiImpl().getKhuyenMaiByMa("KM00");
        }
//
//        listHoaDonBan = hoaDonBanService.getListByTrangThai(2);
//        HoaDonBan hoaDonBan = listHoaDonBan.get(row);
        int idHD = DataGlobal.getIdHoaDon();
        List<HoaDonChiTiet> hdct = new HDCTImpl().getById(idHD);
        for (HoaDonChiTiet x : hdct) {
            String nameProduct = x.getChiTietSanPham().getSanPham().getTenSP();
            int quantity = (int) x.getSoLuong();
            double price = x.getChiTietSanPham().getGiaBan().doubleValue();
            itemInforTable.addCell(new Cell().add((nameProduct)));
            itemInforTable.addCell(new Cell().add(quantity + ""));
            itemInforTable.addCell(new Cell().add(nf.format(price) + " VND").setTextAlignment(TextAlignment.RIGHT));
            itemInforTable.addCell(new Cell().add(nf.format(price * quantity) + " VND").setTextAlignment(TextAlignment.RIGHT));

            total += ((price * quantity));
            quantitySum += quantity;
        }
        total = total * ((km.getPhantramgiam()) / 100);

        itemInforTable.addCell(
                new Cell().add("Tong So Luong").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add(quantitySum + "").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add("Chiet khau ").setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add((km.getPhantramgiam()) + "%").setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));

        itemInforTable.addCell(
                new Cell().add("Tong Tien").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add((lbnTongTien.getText()) + " VND").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));

        float colWidthNote[] = {560};
        com.itextpdf.layout.element.Table customerInforNote = new com.itextpdf.layout.element.Table(colWidthNote);

        customerInforNote.addCell(
                new Cell().add("Tien khach dua: " + ((txtTienKhachDua.getText())) + " VND").
                        setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBold().setFontSize(20).setFontColor(new DeviceRgb(0, 0, 0)));
        customerInforNote.addCell(
                new Cell().add("Tien Tra Lai: " + ((lbnTienThua.getText())) + " VND").
                        setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBold().setFontSize(20).setFontColor(new DeviceRgb(0, 0, 0)));
        customerInforNote.addCell(
                new Cell().add("Luu y: Quy khach vui long kiem tra hang truoc khi roi khoi shop \n Giu hoa don khi tra hang trong vong 2 ngay").
                        setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setItalic());
        customerInforNote.addCell(
                new Cell().add("Xin cam on quy khach !!!").
                        setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setItalic());
        document.add(table);

        document.add(
                new Paragraph("\n"));
        document.add(customerInfor);

        document.add(
                new Paragraph("\n"));
        document.add(itemInforTable);

        document.add(
                new Paragraph("\n"));
        document.add(customerInforNote);

        document.close();
    }

    Locale lc = new Locale("nv", "VN");
    NumberFormat nf = NumberFormat.getInstance(lc);


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCameraOff;
    private javax.swing.JButton btnCameraOn;
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnTaoHoaDonw;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThayDoi;
    private javax.swing.JButton btnThemVaoGioHang;
    private javax.swing.JButton btnXoaSp;
    private javax.swing.JComboBox<String> cbxTrangThaiHoaDon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbnClock;
    private javax.swing.JLabel lbnKhuyenMai;
    private javax.swing.JLabel lbnMaHD;
    private javax.swing.JLabel lbnMaKh;
    private javax.swing.JLabel lbnNgayTao;
    private javax.swing.JLabel lbnTenKh;
    private javax.swing.JLabel lbnTienThua;
    private javax.swing.JLabel lbnTongTien;
    private pagination.Pagination pagination;
    private javax.swing.JPanel panelWebcam;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables
}
