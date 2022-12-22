/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

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
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.KhuyenMai;
import org.openide.util.Exceptions;
import pagination.EventPagination;
import pagination.Page;
import pagination.style.PaginationItemRenderStyle1;
import service.ICTSPService;
import service.IHDCTService;
import service.IHoaDonService;
import service.impl.CTSPImpl;
import service.impl.HDCTImpl;
import service.impl.HoaDonBanImpl;
import service.impl.KhuyenMaiImpl;
import utilities.Auth;
import utilities.DataGlobal;

/**
 *
 * @author ADMIN
 */
public class TraHang1 extends javax.swing.JPanel {

    /**
     * Creates new form TraHang1
     */
    DefaultTableModel modelHD = new DefaultTableModel();
    DefaultTableModel modelSP = new DefaultTableModel();
    IHoaDonService hoaDonService = new HoaDonBanImpl();
    IHDCTService hDCTService = new HDCTImpl();
    ICTSPService cTSPService = new CTSPImpl();
    List<HoaDonBan> listHoaDon = new ArrayList<>();
    List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    List<HoaDonChiTiet> listTra = new ArrayList<>();
    int click = 0;
    Integer pageSize = 5;
    Integer totalProducts = 0;
    private Page paging = new Page();

    public TraHang1() {
        initComponents();
        modelHD = (DefaultTableModel) tblHoaDon.getModel();
        modelSP = (DefaultTableModel) tblSanPham.getModel();
        listHoaDon = hoaDonService.getListByTrangThai(2);
        loadPagination();
        if (lbTienHoanTra.getText().trim().isBlank()) {
            btnTraHang.setEnabled(false);
        }
        pagination.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination.setPagegination(1, paging.getTotalPage());
    }

    public void loadPagination() {
        String search = searchOnKey.getText();

        totalProducts = hoaDonService.filterProductTraHang(search).size();

        int total = (int) Math.ceil(totalProducts / pageSize) + 1;
        paging.setTotalPage(total);
        pagination.setPagegination(1, paging.getTotalPage());

        if (paging.getTotalPage() < paging.getCurrent()) {
            pagination.setPagegination(paging.getTotalPage(), paging.getTotalPage());
            loadTable(hoaDonService.pageListTraHang(paging.getTotalPage(), pageSize, search));
        } else {
            pagination.setPagegination(paging.getCurrent(), paging.getTotalPage());
            loadTable(hoaDonService.pageListTraHang(paging.getCurrent(), pageSize, search));
        }

        pagination.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadTable(hoaDonService.pageListTraHang(page, pageSize, search));
                paging.setCurrent(page);
            }
        });
    }

    public void loadTable(List<HoaDonBan> ctsp) {
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon.getModel();
        dtm.setRowCount(0);

        for (HoaDonBan x : ctsp) {
            Object[] rowData = {
                x.getId(), x.getMaHDB(), x.getKhachHang().getTenKH(), x.getNhanVien().getTenNV(), x.getNgayTao(), x.getNgayThanhToan(), x.trangthai()
            };
            dtm.addRow(rowData);
        }
    }

    private void loadTableDanhSachSp(List<HoaDonChiTiet> listHdct) {

        modelSP.setRowCount(0);
        for (HoaDonChiTiet hd : listHdct) {
            modelSP.addRow(new Object[]{
                hd.getId(),
                hd.getChiTietSanPham().getSanPham().getMaSP(), hd.getChiTietSanPham().getSanPham().getTenSP(),
                hd.getSoLuong(), hd.getChiTietSanPham().getMauSac().getTenMau(), hd.getChiTietSanPham().getChatLieu().getTenCL(),
                hd.getDonGia()
            });
        }
    }

    private void loadTableDanhSachTra(List<HoaDonChiTiet> listHdct) {
        DefaultTableModel model = (DefaultTableModel) tblSpTra.getModel();
        model.setRowCount(0);
        for (HoaDonChiTiet hd : listHdct) {
            model.addRow(new Object[]{
                hd.getId(),
                hd.getChiTietSanPham().getSanPham().getTenSP(), hd.getSoLuong(),});
        }
    }

    private void clear() {
        lbKhachHang.setText("");
        lbMaHoaDon.setText("");
        txtGhiChu.setText("");
        lbTienHoanTra.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTra = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        searchOnKey = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        pagination = new pagination.Pagination();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSpTra = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbKhachHang = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbMaHoaDon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTienHoanTra = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnTraHang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jPanel3.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã sản phẩm", "Tên sản phẩm", "Số lượng mua", "Màu sắc", "Chất liệu", "Đơn giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setRowHeight(30);
        tblSanPham.setVerifyInputWhenFocusTarget(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        btnTra.setBackground(new java.awt.Color(0, 102, 0));
        btnTra.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnTra, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.btnTra.text")); // NOI18N
        btnTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTra, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTra)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jLabel1.text_1")); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jPanel1.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchOnKey.setText(org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.searchOnKey.text")); // NOI18N
        searchOnKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchOnKeyKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(searchOnKey, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(searchOnKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jPanel2.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã hoá đơn", "Tên KH", "Tên NV", "Ngày Tạo", "Ngày Thanh Toán", "Trạng Thái "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(30);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblSpTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Tên Sp", "Số Lượng Trả"
            }
        ));
        jScrollPane4.setViewportView(tblSpTra);

        jButton2.setBackground(new java.awt.Color(0, 102, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(35, 35, 35))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jLabel2.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbKhachHang, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.lbKhachHang.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jLabel3.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbMaHoaDon, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.lbMaHoaDon.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jLabel4.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.jLabel5.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbTienHoanTra, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.lbTienHoanTra.text_1")); // NOI18N

        txtGhiChu.setText(org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.txtGhiChu.text")); // NOI18N

        btnTraHang.setBackground(new java.awt.Color(0, 102, 102));
        btnTraHang.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        btnTraHang.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnTraHang, org.openide.util.NbBundle.getMessage(TraHang1.class, "TraHang1.btnTraHang.text_1")); // NOI18N
        btnTraHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(55, 55, 55)
                        .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lbTienHoanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(20, 20, 20)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(lbTienHoanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int row = tblSanPham.getSelectedRow();
        HoaDonChiTiet hdct = listHDCT.get(row);
        //JOptionPane.showMessageDialog(this, hdct.getSoLuong());
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraActionPerformed

        int rowHD = tblHoaDon.getSelectedRow();
        if (rowHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn ");
            return;
        }
        listHoaDon = hoaDonService.getListByTrangThai(2);
        HoaDonBan hdb = listHoaDon.get(rowHD);
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần trả");
            return;
        }
        listHDCT = hDCTService.getById(hdb.getId());
        HoaDonChiTiet hdct = listHDCT.get(row);
        String m = null;
        int sl = 0;
        try {
            m = JOptionPane.showInputDialog("Số sản phẩm muốn trả :");
            sl = Integer.valueOf(m);
            // System.out.println(soLuong);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng số");
            return;
        } //get số lượng
        int soLuongMua = (int) tblSanPham.getValueAt(row, 3);
        if (sl > soLuongMua) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá");
            return;
        }

        boolean isDup = true;

        for (HoaDonChiTiet hdct1 : listTra) {
            System.out.println(hdct1.getId() + "," + hdct.getId());
            if (hdct1.getChiTietSanPham().getMa().equalsIgnoreCase(hdct.getChiTietSanPham().getMa())) {
                // int sl = hdct1.getSoLuong();
                if (hdct1.getSoLuong() == hdct.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Vượt quá số lượng");
                    return;
                }

                hdct1.setSoLuong(sl + hdct1.getSoLuong());
                loadTableDanhSachTra(listTra);
                isDup = false;
            }
        }
        if (isDup == true) {
            hdct.setSoLuong(sl);
            listTra.add(hdct);
        }

        loadTableDanhSachTra(listTra);

        int sum = 0;
        double sumGia = 0;
        for (HoaDonChiTiet hoaDonChiTiet : listTra) {
            sum = sum + hoaDonChiTiet.getSoLuong();
            sumGia = sumGia + (hoaDonChiTiet.getSoLuong() * hoaDonChiTiet.getDonGia().doubleValue());
        }
        lbTienHoanTra.setText(String.valueOf(sumGia));
        btnTraHang.setEnabled(true);
    }//GEN-LAST:event_btnTraActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        listTra = new ArrayList<>();
        loadTableDanhSachTra(listTra);


    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTraHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraHangActionPerformed

        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
            return;
        }
        List<HoaDonBan> list = hoaDonService.getListByTrangThai(3);
        if (list == null) {
            return;
        }
        HoaDonBan hoaDonBan = listHoaDon.get(row);
        HoaDonBan hoaDonBan1 = new HoaDonBan();
        // tạo hóa đơn trả 
        hoaDonBan1.setId(null);
        hoaDonBan1.setMaHDB("HDT" + (list.size() + 1));
        hoaDonBan1.setNhanVien(Auth.getNv());
        hoaDonBan1.setKhachHang(hoaDonBan.getKhachHang());
        hoaDonBan1.setNguoiNhan(hoaDonBan.getNguoiNhan());
        hoaDonBan1.setSdt(hoaDonBan.getSdt());
        hoaDonBan1.setDiaChi(hoaDonBan.getDiaChi());
        hoaDonBan1.setNgayTao(new Date());
        hoaDonBan1.setNgayThanhToan(new Date());
        hoaDonBan1.setKhuyenMai(null);
        hoaDonBan1.setTrangThai(3);
        hoaDonService.insert(hoaDonBan1);
        int soLuongMua = 0;
        for (HoaDonChiTiet hoaDonChiTiet : listTra) {
            soLuongMua = hDCTService.getSoluongByCTSPandMaHD(hoaDonChiTiet.getChiTietSanPham().getId(), hoaDonChiTiet.getHoaDonBan().getId());
            hoaDonChiTiet.setTrangThai(3);
            hoaDonChiTiet.setHoaDonBan(hoaDonBan1);
            hoaDonService.updateSoLuongHDCTbyIDHDCT(hoaDonChiTiet.getId(), soLuongMua - hoaDonChiTiet.getSoLuong()); // cập nhật hóa đơn ch tiết 
            cTSPService.updateSoLuongCTSPTraHang(hoaDonChiTiet.getChiTietSanPham().getMa(), hoaDonChiTiet.getSoLuong());
            System.out.println(hoaDonChiTiet.getId());
            hoaDonService.addHoaDonChiTiet(hoaDonChiTiet); // insert cái mới

            int idHdTra = (int) tblHoaDon.getValueAt(row, 0);
            if (hDCTService.getByIdByTrangThai(idHdTra, 2).size() == 0) {
                hoaDonService.updateTrangThaiHoaDon(idHdTra, 4);
            }

        }
        int oup = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn ?");
        if (oup == JOptionPane.YES_OPTION) {
            try {
                outputPDF();
                JOptionPane.showMessageDialog(this, "In hóa đơn thành công");
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (BadElementException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        JOptionPane.showMessageDialog(this, "Thành công");
        int id = (int) tblHoaDon.getValueAt(row, 0);
        listHDCT = hDCTService.getByIdByTrangThai(id, 2);
        loadTableDanhSachSp(listHDCT);
        clear();
        loadTableDanhSachTra(new ArrayList<>());
        // loadTableHoaDon(hoaDonService.getListByTrangThai(2));
        loadPagination();
    }//GEN-LAST:event_btnTraHangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = tblSpTra.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
            return;
        }
        listTra.remove(row);
        loadTableDanhSachTra(listTra);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int row = tblHoaDon.getSelectedRow();
        int id = (int) tblHoaDon.getValueAt(row, 0);
        String maHD = (String) tblHoaDon.getValueAt(row, 1);
        String tenKH = (String) tblHoaDon.getValueAt(row, 2);
        lbKhachHang.setText(tenKH);
        lbMaHoaDon.setText(maHD);
        listHDCT = hDCTService.getByIdByTrangThai(id, 2);
        if (listHDCT.size() == 0) {
            return;
        }
        loadTableDanhSachSp(listHDCT);
        listTra = new ArrayList<>();
        loadTableDanhSachTra(new ArrayList<>());
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void searchOnKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchOnKeyKeyReleased
        loadPagination();
    }//GEN-LAST:event_searchOnKeyKeyReleased

    public void outputPDF() throws IOException, BadElementException {
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
            return;
        }

        KhachHang khachHang = Auth.getKh();
        String pathh = (lbMaHoaDon.getText());
        System.out.println(pathh);
        String path = "D:\\HoaDonTraHang\\" + pathh + ".pdf";
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
        customerInfor.addCell(new Cell(0, 4).add("Hoa don tra hang").setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        customerInfor.addCell(new Cell(0, 4).add("Thong tin").setBold().setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Khach hang: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add((lbKhachHang.getText())).setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Ma hoa don: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(lbMaHoaDon.getText()).setBorder(Border.NO_BORDER));

        float iteamInforColWidth[] = {140, 140, 140, 140};
        Table itemInforTable = new Table(iteamInforColWidth);
        itemInforTable.addCell(new Cell().add("San pham"));
        itemInforTable.addCell(new Cell().add("So luong"));
        itemInforTable.addCell(new Cell().add("Don gia").setTextAlignment(TextAlignment.RIGHT));
        itemInforTable.addCell(new Cell().add("Thanh tien").setTextAlignment(TextAlignment.RIGHT));

        int total = 0;
        int quantitySum = 0;
        // Xuất pdf

//        listHoaDonBan = hoaDonBanService.getListByTrangThai(2);
//        HoaDonBan hoaDonBan = listHoaDonBan.get(row);
//        int idHD = DataGlobal.getIdHoaDon();
        List<HoaDonChiTiet> hdct = listTra;
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

        itemInforTable.addCell(
                new Cell().add("Tong So Luong").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add(quantitySum + "").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));

        itemInforTable.addCell(
                new Cell().add("Tong Tien").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(
                new Cell().add((total + " VND")).setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));

        float colWidthNote[] = {560};
        com.itextpdf.layout.element.Table customerInforNote = new com.itextpdf.layout.element.Table(colWidthNote);

        customerInforNote.addCell(
                new Cell().add("Tien Tra Lai: " + ((lbTienHoanTra.getText())) + " VND").
                        setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBold().setFontSize(20).setFontColor(new DeviceRgb(0, 0, 0)));
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
    private javax.swing.JButton btnTra;
    private javax.swing.JButton btnTraHang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbMaHoaDon;
    private javax.swing.JLabel lbTienHoanTra;
    private pagination.Pagination pagination;
    private javax.swing.JTextField searchOnKey;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSpTra;
    private javax.swing.JTextField txtGhiChu;
    // End of variables declaration//GEN-END:variables
}
