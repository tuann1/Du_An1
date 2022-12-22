package view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import model.ChatLieu;
import model.ChiTietSanPham;
import model.DanhMuc;
import model.Mau;
import model.NSX;
import model.SanPham;
import service.impl.CTSPImpl;
import service.impl.ChatLieuImpl;
import service.impl.DanhMucImpl;
import service.impl.MauSacImpl;
import service.impl.NSXImpl;
import service.impl.SanPhamImp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import pagination.EventPagination;
import pagination.Page;
import pagination.style.PaginationItemRenderStyle1;
import utilities.ExportExcel;
import utilities.ImportExcel;
import service.ICTSPService;
import service.ISanPhamService;

public class QuanLySP extends javax.swing.JPanel {

    private List<ChatLieu> cl = new ArrayList<>();
    private List<Mau> mau = new ArrayList<>();
    private List<NSX> nsx = new ArrayList<>();
    private List<DanhMuc> dm = new ArrayList<>();
    private List<SanPham> sp = new ArrayList<>();
    private List<ChiTietSanPham> ctsp = new ArrayList<>();
    private ICTSPService ctspSer = new CTSPImpl();
    DefaultComboBoxModel<SanPham> cbSP = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<DanhMuc> cbDM = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<ChatLieu> cbCL = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Mau> cbMau = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<NSX> cbNSX = new DefaultComboBoxModel<>();
    private List<ChiTietSanPham> listCTSP;
    private ISanPhamService spSer = new SanPhamImp();
    // DefaultComboBoxModel<SanPham> findDanhMucBoxModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<DanhMuc> findDanhMucBoxModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<ChatLieu> findChatLieuBoxModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Mau> findMauBoxModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<NSX> findNSXBoxModel = new DefaultComboBoxModel<>();
    DanhMuc danhMucF = null;
    ChatLieu chatLieuF = null;
    Mau mauF = null;
    NSX nsxF = null;
    private ICTSPService ctspService;

    int index = 0;

    Integer pageSize = 5;
    Integer totalProducts = 0;
    private Page paging = new Page();

    public QuanLySP() {
        initComponents();
        ctspService = new CTSPImpl();
        loadSanPham(sp);
        ctsp = ctspSer.getAll();
        listCTSP = ctspSer.getAll();
//        loadChiTietSanPham(listCTSP);
        loadCbSanPham();
        loadCbDanhMuc();
        loadCbChatLieu();
        loadCbMauSac();
        loadCbNSX();
        loadAllCBFind();
        loadPagination();
        pagination.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination.setPagegination(1, paging.getTotalPage());
    }

    public void loadPagination() {
        String search = txtSearch.getText();
        String tenDM = cbxFindDanhMuc.getSelectedItem().toString();
        String tenCL = cbxFindChatLieu.getSelectedItem().toString();
        String tenMau = cbxFindMauSac.getSelectedItem().toString();
        String tenNSX = cbxFindNSX.getSelectedItem().toString();

        totalProducts = ctspService.filterProduct(search, tenDM, tenCL, tenMau, tenNSX).size();

        int total = (int) Math.ceil(totalProducts / pageSize) + 1;
        paging.setTotalPage(total);
        pagination.setPagegination(1, paging.getTotalPage());

        if (paging.getTotalPage() < paging.getCurrent()) {
            pagination.setPagegination(paging.getTotalPage(), paging.getTotalPage());
            loadTable(ctspService.pageList(paging.getTotalPage(), pageSize, search, tenDM, tenCL, tenMau, tenNSX));
        } else {
            pagination.setPagegination(paging.getCurrent(), paging.getTotalPage());
            loadTable(ctspService.pageList(paging.getCurrent(), pageSize, search, tenDM, tenCL, tenMau, tenNSX));
        }


        pagination.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadTable(ctspService.pageList(page, pageSize, search, tenDM, tenCL, tenMau, tenNSX));
                paging.setCurrent(page);
            }
        });
    }

    public void loadTable(List<ChiTietSanPham> ctsp) {
        DefaultTableModel dtm = (DefaultTableModel) tbSanPham.getModel();
        dtm.setRowCount(0);

        for (ChiTietSanPham x : ctsp) {
            Object[] rowData = {
                x.getId(), x.getMa(), x.getSanPham().getTenSP(),
                x.getDanhMuc().getTenDM(), x.getChatLieu().getTenCL(),
                x.getMauSac().getTenMau(), x.getNhaSanXuat().getTenNSX(),
                x.getSoLuongTon(), x.getGiaNhap(), x.getGiaBan(),
                x.getMoTa(), x.getNgayTao(), x.getNgaySua(), x.getTrangThai() == 1 ? "Dang kinh doanh" : "Ngung kinh doanh"
            };
            dtm.addRow(rowData);
        }
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
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        btnExport = new javax.swing.JButton();
        cbbSanPham = new javax.swing.JComboBox<>();
        cbbDanhMuc = new javax.swing.JComboBox<>();
        cbbChatLieu = new javax.swing.JComboBox<>();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbNSX = new javax.swing.JComboBox<>();
        btnClear = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxFindDanhMuc = new javax.swing.JComboBox<>();
        cbxFindChatLieu = new javax.swing.JComboBox<>();
        cbxFindMauSac = new javax.swing.JComboBox<>();
        cbxFindNSX = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        pagination = new pagination.Pagination();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        cbbAll = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbThuocTinh = new javax.swing.JTable();

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jPanel17.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel48, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel48.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel49, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel49.text")); // NOI18N

        txtMaSP.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel50, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel50.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel51, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel51.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel52, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel52.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel54, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel54.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel55, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel55.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel56, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel56.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel57, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel57.text")); // NOI18N

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane8.setViewportView(txtMoTa);

        btnAdd.setBackground(new java.awt.Color(0, 153, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnAdd, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnAdd.text")); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 153, 204));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnUpdate, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnUpdate.text")); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 153, 204));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnDelete, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnDelete.text")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel58, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel58.text")); // NOI18N

        btnExport.setBackground(new java.awt.Color(0, 153, 204));
        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnExport, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnExport.text_1")); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 153, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnClear, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnClear.text")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49))
                .addGap(27, 27, 27)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(txtSoLuong)
                    .addComponent(txtGiaNhap)
                    .addComponent(txtGiaBan)
                    .addComponent(cbbSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(90, 90, 90)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55)
                            .addComponent(jLabel56))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbbDanhMuc, 0, 120, Short.MAX_VALUE)
                                .addComponent(cbbChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(18, 18, 18)
                        .addComponent(cbbNSX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 49, Short.MAX_VALUE))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(jLabel54)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel51)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel58)
                                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57)
                            .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 46, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jPanel7.border.title"))); // NOI18N

        txtSearch.setBorder(null);
        txtSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchCaretUpdate(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel34, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel34.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel5.text")); // NOI18N

        cbxFindDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFindDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFindDanhMucActionPerformed(evt);
            }
        });

        cbxFindChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFindChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFindChatLieuActionPerformed(evt);
            }
        });

        cbxFindMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFindMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFindMauSacActionPerformed(evt);
            }
        });

        cbxFindNSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFindNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFindNSXActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(60, 60, 60))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxFindDanhMuc, 0, 103, Short.MAX_VALUE)
                            .addComponent(cbxFindChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxFindMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxFindNSX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxFindDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxFindChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxFindMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxFindNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));

        tbSanPham.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã", "Tên sản phẩm", "Danh mục", "Chất liệu", "Màu sắc", "Nhà sản xuất", "Số lượng", "Giá nhập", "Giá bán", "Mô tả", "Ngày tạo", "Ngày sửa", "Trạng thái"
            }
        ));
        tbSanPham.setRowHeight(30);
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbSanPham);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(563, 563, 563)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(95, 95, 95)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(650, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab(org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jPanel16.TabConstraints.tabTitle"), jPanel16); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jPanel11.border.title"))); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel35, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel35.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel36, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jLabel36.text")); // NOI18N

        txtMa.setEditable(false);

        cbbAll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sản phẩm", "Danh mục", "Nhà sản xuất", "Chất liệu", "Màu sắc" }));
        cbbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbAllActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 153, 204));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnThem, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnThem.text")); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 153, 204));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnSua, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnSua.text")); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 204));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(btnXoa, org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.btnXoa.text")); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMa)
                            .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(81, 81, 81))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(cbbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(415, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)))
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(13, 13, 13)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 153)));

        tbThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã", "Tên", "Ngày tạo", "Ngày sửa", "Trạng thái"
            }
        ));
        tbThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbThuocTinh);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 185, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane5.addTab(org.openide.util.NbBundle.getMessage(QuanLySP.class, "QuanLySP.jPanel13.TabConstraints.tabTitle"), jPanel13); // NOI18N

        jScrollPane1.setViewportView(jTabbedPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1278, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String ma = txtMaSP.getText().trim();
        boolean check = checkMaCTSP(ma);
        if (check == false) {
            return;
        }

        if (validateSanPham()) {
            String result = new CTSPImpl().add(getData());
            JOptionPane.showMessageDialog(this, result);
            loadPagination();
            clearCTSP();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tbSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thông tin trong bảng");
            return;
        }
        Integer id = (Integer) tbSanPham.getValueAt(row, 0);

        if (validateSanPham()) {
            String result = new CTSPImpl().update(getDataCTSP(), id);
            JOptionPane.showMessageDialog(this, result);
            loadPagination();
            clearCTSP();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tbSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thông tin trong bảng");
            return;
        }
        Integer id = (Integer) tbSanPham.getValueAt(row, 0);

        String result = new CTSPImpl().updateTrangThai(id);
        JOptionPane.showMessageDialog(this, result);
        loadPagination();
        clearCTSP();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Export Excel");
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                int xn = JOptionPane.showConfirmDialog(this, "Xác nhận xuất file ?");
                if (xn == JOptionPane.YES_OPTION) {
                    ExportExcel.writeExcel(ctspSer.getAll(), fileToSave.getAbsolutePath() + filter.getDescription());
                    JOptionPane.showMessageDialog(this, "Export thành công");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Export thất bại");
            }
            System.out.println("Save as file: " + fileToSave.getAbsolutePath() + filter.getDescription());
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        loadPagination();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        String search = txtSearch.getText();
        String tenDM = cbxFindDanhMuc.getSelectedItem().toString();
        String tenCL = cbxFindChatLieu.getSelectedItem().toString();
        String tenMau = cbxFindMauSac.getSelectedItem().toString();
        String tenNSX = cbxFindNSX.getSelectedItem().toString();

        int row = tbSanPham.getSelectedRow();
        listCTSP = ctspSer.pageList(paging.getCurrent(), pageSize, search, tenDM, tenCL, tenMau, tenNSX);
        ChiTietSanPham ct = listCTSP.get(row);
        txtMaSP.setText(ct.getMa());
        cbSP.setSelectedItem(ct.getSanPham());
        cbDM.setSelectedItem(ct.getDanhMuc());
        cbCL.setSelectedItem(ct.getChatLieu());
        cbMau.setSelectedItem(ct.getMauSac());
        cbNSX.setSelectedItem(ct.getNhaSanXuat());
        txtSoLuong.setText(String.valueOf(ct.getSoLuongTon()));
        txtGiaNhap.setText(String.valueOf(ct.getGiaNhap()));
        txtGiaBan.setText(String.valueOf(ct.getGiaBan()));
        txtMoTa.setText(String.valueOf(ct.getMoTa()));
    }//GEN-LAST:event_tbSanPhamMouseClicked

    private void cbbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbAllActionPerformed
        String text = cbbAll.getSelectedItem().toString();
        if (text.equals("Sản phẩm")) {
            index = 0;
            clearData();
        } else if (text.equals("Danh mục")) {
            index = 1;
            clearData();
        } else if (text.equals("Nhà sản xuất")) {
            index = 2;
            clearData();
        } else if (text.equals("Chất liệu")) {
            index = 3;
            clearData();
        } else if (text.equals("Màu sắc")) {
            index = 4;
            clearData();
        }
        loadCBB();
    }//GEN-LAST:event_cbbAllActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String ma = txtMa.getText().trim();
        boolean check = checkMaSP(ma);
        if (check == false) {
            return;
        }
        if (index == 0) {
            if (validateThuocTinh()) {
                String result = new SanPhamImp().add(getSanPham());
                JOptionPane.showMessageDialog(this, result);
                loadSanPham(sp);
                loadAllCB();
                clearData();
            }
        } else if (index == 1) {
            if (validateThuocTinh()) {
                String result = new DanhMucImpl().add(getDanhMuc());
                JOptionPane.showMessageDialog(this, result);
                loadDanhMuc(dm);
                loadAllCB();
                clearData();
            }
        } else if (index == 2) {
            if (validateThuocTinh()) {
                String result = new NSXImpl().add(getNSX());
                JOptionPane.showMessageDialog(this, result);
                loadNSX(nsx);
                loadAllCB();
                clearData();
            }
        } else if (index == 3) {
            if (validateThuocTinh()) {
                String result = new ChatLieuImpl().add(getChatLieu());
                JOptionPane.showMessageDialog(this, result);
                loadChatLieu(cl);
                loadAllCB();
                clearData();
            }
        } else if (index == 4) {
            if (validateThuocTinh()) {
                String result = new MauSacImpl().add(getMauSac());
                JOptionPane.showMessageDialog(this, result);
                loadMauSac(mau);
                loadAllCB();
                clearData();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int row = tbThuocTinh.getSelectedRow();
        Integer id = (Integer) tbThuocTinh.getValueAt(row, 0);

        if (index == 0) {
            if (validateThuocTinh()) {
                String result = new SanPhamImp().update(getSP(), id);
                JOptionPane.showMessageDialog(this, result);
                loadSanPham(sp);
                loadAllCB();
                loadChiTietSanPham(ctsp);
                clearData();
            }
        } else if (index == 1) {
            if (validateThuocTinh()) {
                String result = new DanhMucImpl().update(getDM(), id);
                JOptionPane.showMessageDialog(this, result);
                loadDanhMuc(dm);
                loadAllCB();
                loadChiTietSanPham(ctsp);
                clearData();
            }
        } else if (index == 2) {
            if (validateThuocTinh()) {
                String result = new NSXImpl().update(getNhaSanXuat(), id);
                JOptionPane.showMessageDialog(this, result);
                loadNSX(nsx);
                loadAllCB();
                loadChiTietSanPham(ctsp);
                clearData();
            }
        } else if (index == 3) {
            if (validateThuocTinh()) {
                String result = new ChatLieuImpl().update(getCL(), id);
                JOptionPane.showMessageDialog(this, result);
                loadChatLieu(cl);
                loadAllCB();
                loadChiTietSanPham(ctsp);
                clearData();
            }
        } else if (index == 4) {
            if (validateThuocTinh()) {
                String result = new MauSacImpl().update(getMS(), id);
                JOptionPane.showMessageDialog(this, result);
                loadMauSac(mau);
                loadAllCB();
                loadChiTietSanPham(ctsp);
                clearData();
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tbThuocTinh.getSelectedRow();
        Integer id = (Integer) tbThuocTinh.getValueAt(row, 0);
        if (index == 0) {
            String result = new SanPhamImp().updateTrangThai(id);
            JOptionPane.showMessageDialog(this, result);
            loadSanPham(sp);
            loadAllCB();
            clearData();
        } else if (index == 1) {
            String result = new DanhMucImpl().updateTrangThai(id);
            JOptionPane.showMessageDialog(this, result);
            loadDanhMuc(dm);
            loadAllCB();
            clearData();
        } else if (index == 2) {
            String result = new NSXImpl().updateTrangThai(id);
            JOptionPane.showMessageDialog(this, result);
            loadNSX(nsx);
            loadAllCB();
            clearData();
        } else if (index == 3) {
            String result = new ChatLieuImpl().updateTrangThai(id);
            JOptionPane.showMessageDialog(this, result);
            loadChatLieu(cl);
            loadAllCB();
            clearData();
        } else if (index == 4) {
            String result = new MauSacImpl().updateTrangThai(id);
            JOptionPane.showMessageDialog(this, result);
            loadMauSac(mau);
            loadAllCB();
            clearData();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tbThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbThuocTinhMouseClicked
        int row = tbThuocTinh.getSelectedRow();
        txtMa.setText(tbThuocTinh.getValueAt(row, 1).toString());
        txtTen.setText(tbThuocTinh.getValueAt(row, 2).toString());
    }//GEN-LAST:event_tbThuocTinhMouseClicked

    private void cbxFindDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFindDanhMucActionPerformed
//        // DanhMuc danhMuc = null;
//        if (cbxFindDanhMuc.getSelectedIndex() == 0) {
//            danhMucF = null;
//
//        } else {
//            danhMucF = (DanhMuc) cbxFindDanhMuc.getSelectedItem();
//        }
//        List<ChiTietSanPham> list = ctspSer.getChiTietSanPhamByComBoBox(danhMucF, chatLieuF, mauF, nsxF);
//        List<ChiTietSanPham> listResult = new ArrayList<>();
//        for (ChiTietSanPham chiTietSanPham : list) {
//            ChiTietSanPham ctsp = ctspSer.getAllByID(chiTietSanPham.getId());
//            listResult.add(ctsp);
//        }
//
//        loadChiTietSanPham(listResult);
        loadPagination();
    }//GEN-LAST:event_cbxFindDanhMucActionPerformed

    private void cbxFindChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFindChatLieuActionPerformed
//        if (cbxFindChatLieu.getSelectedIndex() == 0) {
//            chatLieuF = null;
//
//        } else {
//            chatLieuF = (ChatLieu) cbxFindChatLieu.getSelectedItem();
//        }
//
//        List<ChiTietSanPham> list = ctspSer.getChiTietSanPhamByComBoBox(danhMucF, chatLieuF, mauF, nsxF);
//        List<ChiTietSanPham> listResult = new ArrayList<>();
//        for (ChiTietSanPham chiTietSanPham : list) {
//            ChiTietSanPham ctsp = ctspSer.getAllByID(chiTietSanPham.getId());
//            listResult.add(ctsp);
//        }
//
//        loadChiTietSanPham(listResult);
        loadPagination();
    }//GEN-LAST:event_cbxFindChatLieuActionPerformed

    private void cbxFindMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFindMauSacActionPerformed
//        if (cbxFindMauSac.getSelectedIndex() == 0) {
//            mauF = null;
//
//        } else {
//            mauF = (Mau) cbxFindMauSac.getSelectedItem();
//        }
//
//        List<ChiTietSanPham> list = ctspSer.getChiTietSanPhamByComBoBox(danhMucF, chatLieuF, mauF, nsxF);
//        List<ChiTietSanPham> listResult = new ArrayList<>();
//        for (ChiTietSanPham chiTietSanPham : list) {
//            ChiTietSanPham ctsp = ctspSer.getAllByID(chiTietSanPham.getId());
//            listResult.add(ctsp);
//        }
//
//        loadChiTietSanPham(listResult);

        loadPagination();
    }//GEN-LAST:event_cbxFindMauSacActionPerformed

    private void cbxFindNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFindNSXActionPerformed
//        if (cbxFindNSX.getSelectedIndex() == 0) {
//            nsxF = null;
//        } else {
//            nsxF = (NSX) cbxFindNSX.getSelectedItem();
//        }
//
//        List<ChiTietSanPham> list = ctspSer.getChiTietSanPhamByComBoBox(danhMucF, chatLieuF, mauF, nsxF);
//        List<ChiTietSanPham> listResult = new ArrayList<>();
//        for (ChiTietSanPham chiTietSanPham : list) {
//            ChiTietSanPham ctsp = ctspSer.getAllByID(chiTietSanPham.getId());
//            listResult.add(ctsp);
//        }
//        loadChiTietSanPham(listResult);

        loadPagination();
    }//GEN-LAST:event_cbxFindNSXActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtMaSP.setText("");
        txtSoLuong.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtMoTa.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchCaretUpdate
        searchByName();
    }//GEN-LAST:event_txtSearchCaretUpdate

    // Update QLSP
    void loadCbSanPham() {
        cbbSanPham.setModel((DefaultComboBoxModel) cbSP);
        cbSP.removeAllElements();
        for (SanPham sp : new SanPhamImp().getAll()) {
            cbSP.addElement(sp);
        }
    }

    void loadCbDanhMuc() {
        cbbDanhMuc.setModel((DefaultComboBoxModel) cbDM);
        cbDM.removeAllElements();
        for (DanhMuc dm : new DanhMucImpl().getAll()) {
            cbDM.addElement(dm);
        }
    }

    void loadCbChatLieu() {
        cbbChatLieu.setModel((DefaultComboBoxModel) cbCL);
        cbCL.removeAllElements();
        for (ChatLieu cl : new ChatLieuImpl().getAll()) {
            cbCL.addElement(cl);
        }
    }

    void loadCbMauSac() {
        cbbMauSac.setModel((DefaultComboBoxModel) cbMau);
        cbMau.removeAllElements();
        for (Mau mau : new MauSacImpl().getAll()) {
            cbMau.addElement(mau);
        }
    }

    void loadCbNSX() {
        cbbNSX.setModel((DefaultComboBoxModel) cbNSX);
        cbNSX.removeAllElements();
        for (NSX nsx : new NSXImpl().getAll()) {
            cbNSX.addElement(nsx);
        }
    }

    public void loadAllCB() {
        loadCbChatLieu();
        loadCbDanhMuc();
        loadCbMauSac();
        loadCbNSX();
        loadCbSanPham();
    }

    //==============================================================================
    void loadCbxFindDanhMuc() {
        cbxFindDanhMuc.setModel((DefaultComboBoxModel) findDanhMucBoxModel);
        findDanhMucBoxModel.removeAllElements();
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTenDM("All");
        findDanhMucBoxModel.addElement(danhMuc);
        for (DanhMuc dm : new DanhMucImpl().getAll()) {
            findDanhMucBoxModel.addElement(dm);
        }
    }

    void loadCbxFindChatLieu() {
        cbxFindChatLieu.setModel((DefaultComboBoxModel) findChatLieuBoxModel);
        findChatLieuBoxModel.removeAllElements();
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setTenCL("All");
        findChatLieuBoxModel.addElement(chatLieu);
        for (ChatLieu cl : new ChatLieuImpl().getAll()) {
            findChatLieuBoxModel.addElement(cl);
        }
    }

    void loadCbxFindMauSac() {
        cbxFindMauSac.setModel((DefaultComboBoxModel) findMauBoxModel);
        findMauBoxModel.removeAllElements();
        Mau mau1 = new Mau();
        mau1.setTenMau("All");
        findMauBoxModel.addElement(mau1);
        for (Mau mau : new MauSacImpl().getAll()) {
            findMauBoxModel.addElement(mau);
        }
    }

    void loadCbxFindNSX() {
        cbxFindNSX.setModel((DefaultComboBoxModel) findNSXBoxModel);
        findNSXBoxModel.removeAllElements();
        NSX nsx1 = new NSX();
        nsx1.setTenNSX("All");
        findNSXBoxModel.addElement(nsx1);
        for (NSX nsx : new NSXImpl().getAll()) {
            findNSXBoxModel.addElement(nsx);
        }
    }

    public void loadAllCBFind() {
        loadCbxFindChatLieu();
        loadCbxFindDanhMuc();
        loadCbxFindMauSac();
        loadCbxFindNSX();
    }

//===============================================================================
    void loadChiTietSanPham(List<ChiTietSanPham> list) {
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
//        list = ctspSer.getAll();
        for (ChiTietSanPham ctsp : list) {
            model.addRow(ctsp.toDataRow());
        }
    }

    void loadSanPham(List<SanPham> list) {
        DefaultTableModel model = (DefaultTableModel) tbThuocTinh.getModel();
        model.setRowCount(0);
        list = new SanPhamImp().getAll();
        for (SanPham sp : list) {
            model.addRow(sp.toDataRow());
        }
    }

    void loadChatLieu(List<ChatLieu> list) {
        DefaultTableModel model = (DefaultTableModel) tbThuocTinh.getModel();
        model.setRowCount(0);
        list = new ChatLieuImpl().getAll();
        for (ChatLieu x : list) {
            model.addRow(x.toDataRow());
        }
    }

    void loadMauSac(List<Mau> list) {
        DefaultTableModel model = (DefaultTableModel) tbThuocTinh.getModel();
        model.setRowCount(0);
        list = new MauSacImpl().getAll();
        for (Mau x : list) {
            model.addRow(x.toDataRow());
        }
    }

    void loadDanhMuc(List<DanhMuc> list) {
        DefaultTableModel model = (DefaultTableModel) tbThuocTinh.getModel();
        model.setRowCount(0);
        list = new DanhMucImpl().getAll();
        for (DanhMuc x : list) {
            model.addRow(x.toDataRow());
        }
    }

    void loadNSX(List<NSX> list) {
        DefaultTableModel model = (DefaultTableModel) tbThuocTinh.getModel();
        model.setRowCount(0);
        list = new NSXImpl().getAll();
        for (NSX x : list) {
            model.addRow(x.toDataRow());
        }
    }

    ChiTietSanPham getData() {
        ChiTietSanPham ct = new ChiTietSanPham();
        String maM = "";
        if (txtMaSP.getText().isBlank() == true) {
            maM = "CTSP" + (new CTSPImpl().getAll().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        ct.setMa(maM);
        String text = "QRCODE" + maM.toUpperCase();
        ct.setMoTa(txtMoTa.getText());
        ct.setSoLuongTon(Integer.parseInt(txtSoLuong.getText()));
        ct.setGiaNhap(BigDecimal.valueOf(Double.valueOf(txtGiaNhap.getText())));
        ct.setGiaBan(BigDecimal.valueOf(Double.valueOf(txtGiaBan.getText())));
        ct.setNgayTao(new Date());
        ct.setNgaySua(new Date());
        ct.setTrangThai(1);
        SanPham sp = (SanPham) cbbSanPham.getSelectedItem();
        ct.setSanPham(sp);
        DanhMuc dm = (DanhMuc) cbbDanhMuc.getSelectedItem();
        ct.setDanhMuc(dm);
        ChatLieu cl = (ChatLieu) cbbChatLieu.getSelectedItem();
        ct.setChatLieu(cl);
        Mau mau = (Mau) cbbMauSac.getSelectedItem();
        ct.setMauSac(mau);
        NSX nsx = (NSX) cbbNSX.getSelectedItem();
        ct.setNhaSanXuat(nsx);
        ct.setQrCode(maM.toUpperCase() + ".png");
        try {
            String path = "D:\\" + maM.toUpperCase() + ".png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> map = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix bit = new MultiFormatWriter().encode(new String(text.getBytes(charset), charset), BarcodeFormat.QR_CODE, 500, 500, map);
            MatrixToImageWriter.writeToFile(bit, path.substring(path.lastIndexOf('.') + 1), new File(path));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ct;

    }

    ChiTietSanPham getDataCTSP() {
        ChiTietSanPham chiTiet = new ChiTietSanPham();
        chiTiet.setMa(txtMaSP.getText());
        chiTiet.setMoTa(txtMoTa.getText());
        chiTiet.setSoLuongTon(Integer.parseInt(txtSoLuong.getText()));
        chiTiet.setGiaNhap(BigDecimal.valueOf(Double.valueOf(txtGiaNhap.getText())));
        chiTiet.setGiaBan(BigDecimal.valueOf(Double.valueOf(txtGiaBan.getText())));
        chiTiet.setNgaySua(new Date());

        SanPham sp = (SanPham) cbbSanPham.getSelectedItem();
        chiTiet.setSanPham(sp);
        DanhMuc dm = (DanhMuc) cbbDanhMuc.getSelectedItem();
        chiTiet.setDanhMuc(dm);
        ChatLieu cl = (ChatLieu) cbbChatLieu.getSelectedItem();
        chiTiet.setChatLieu(cl);
        Mau mau = (Mau) cbbMauSac.getSelectedItem();
        chiTiet.setMauSac(mau);
        NSX nsx = (NSX) cbbNSX.getSelectedItem();
        chiTiet.setNhaSanXuat(nsx);
        return chiTiet;

    }

    SanPham getSanPham() {
        SanPham sp = new SanPham();
//        String maSP;
//        for (int i = 0; i < new SanPhamImp().getAll().size() + 1; i++) {
//            maSP = "SP" + i;
//            if(spSer.getByMa(maSP) == null) {
//                sp.setMaSP(maSP);
//                break;
//            } else {
//                continue;
//            }
//        }
        String maM = "";
        if (txtMa.getText().isBlank() == true) {
            maM = "SP" + (new SanPhamImp().getAllSp().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        sp.setMaSP(maM);
//        sp.setMaSP(txtMa.getText());
        sp.setTenSP(txtTen.getText());
        sp.setNgayTao(new Date());
        sp.setNgaySua(new Date());
        sp.setTrangThai(1);
        return sp;
    }

    SanPham getSP() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMa.getText());
        sp.setTenSP(txtTen.getText());
        sp.setNgaySua(new Date());

        return sp;
    }

    ChatLieu getChatLieu() {
        ChatLieu cl = new ChatLieu();
        String maM = "";
        if (txtMa.getText().isBlank() == true) {
            maM = "CL" + (new ChatLieuImpl().getAllSp().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        cl.setMaCL(maM);
//        cl.setMaCL(txtMa.getText());
        cl.setTenCL(txtTen.getText());
        cl.setNgayTao(new Date());
        cl.setNgaySua(new Date());
        cl.setTrangThai(1);
        return cl;
    }

    ChatLieu getCL() {
        ChatLieu cl = new ChatLieu();
        cl.setMaCL(txtMa.getText());
        cl.setTenCL(txtTen.getText());
        cl.setNgaySua(new Date());
        return cl;
    }

    Mau getMauSac() {
        Mau ms = new Mau();
        String maM = "";
        if (txtMa.getText().isBlank() == true) {
            maM = "M" + (new MauSacImpl().getAllSp().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        ms.setMaMau(maM);
//      ms.setMaMau(txtMa.getText());
        ms.setTenMau(txtTen.getText());
        ms.setNgayTao(new Date());
        ms.setNgaySua(new Date());
        ms.setTrangThai(1);
        return ms;
    }

    Mau getMS() {
        Mau ms = new Mau();
        ms.setMaMau(txtMa.getText());
        ms.setTenMau(txtTen.getText());
        ms.setNgaySua(new Date());

        return ms;
    }

    DanhMuc getDanhMuc() {
        DanhMuc dm = new DanhMuc();
        String maM = "";
        if (txtMa.getText().isBlank() == true) {
            maM = "DM" + (new DanhMucImpl().getAllSp().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        dm.setMaDM(maM);
//        dm.setMaDM(txtMa.getText());
        dm.setTenDM(txtTen.getText());
        dm.setNgayTao(new Date());
        dm.setNgaySua(new Date());
        dm.setTrangThai(1);
        return dm;
    }

    DanhMuc getDM() {
        DanhMuc dm = new DanhMuc();
        dm.setMaDM(txtMa.getText());
        dm.setTenDM(txtTen.getText());
        dm.setNgaySua(new Date());

        return dm;
    }

    NSX getNSX() {
        NSX nsx = new NSX();
//        nsx.setMaNSX(txtMa.getText());
        String maM = "";
        if (txtMa.getText().isBlank() == true) {
            maM = "NSX" + (new NSXImpl().getAllSp().size() + 1);
        } else {
            maM = txtMa.getText();
        }
        nsx.setMaNSX(maM);
        nsx.setTenNSX(txtTen.getText());
        nsx.setNgayTao(new Date());
        nsx.setNgaySua(new Date());
        nsx.setTrangThai(1);
        return nsx;
    }

    NSX getNhaSanXuat() {
        NSX nsx = new NSX();
        nsx.setMaNSX(txtMa.getText());
        nsx.setTenNSX(txtTen.getText());
        nsx.setNgaySua(new Date());

        return nsx;
    }

    void loadCBB() {
        if (index == 0) {
            loadSanPham(sp);
        } else if (index == 1) {
            loadDanhMuc(dm);
        } else if (index == 2) {
            loadNSX(nsx);
        } else if (index == 3) {
            loadChatLieu(cl);
        } else if (index == 4) {
            loadMauSac(mau);
        }
    }

    void searchByName() {
        String search = txtSearch.getText();
        String tenDM = cbxFindDanhMuc.getSelectedItem().toString();
        String tenCL = cbxFindChatLieu.getSelectedItem().toString();
        String tenMau = cbxFindMauSac.getSelectedItem().toString();
        String tenNSX = cbxFindNSX.getSelectedItem().toString();

        listCTSP = ctspSer.pageList(paging.getCurrent(), pageSize, search, tenDM, tenCL, tenMau, tenNSX);

        DefaultTableModel tb = (DefaultTableModel) tbSanPham.getModel();
        tb.setRowCount(0);

        List<ChiTietSanPham> ct = ctspSer.pageList(paging.getCurrent(), pageSize, search, tenDM, tenCL, tenMau, tenNSX);
        for (ChiTietSanPham x : ct) {
            if (x.getSanPham().getTenSP().toLowerCase().contains(txtSearch.getText().trim().toLowerCase())) {
                tb.addRow(x.toDataRow());
            }
        }
    }

    public boolean validateSanPham() {
        String soLuong = txtSoLuong.getText();
        String giaNhap = txtGiaNhap.getText();
        String giaBan = txtGiaBan.getText();

        if (soLuong.isBlank() || giaNhap.isBlank() || giaBan.isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được để trống");
            return false;
        }

        try {
            Integer.parseInt(soLuong);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên");
            return false;
        }

//        try {
//            if (Integer.parseInt(soLuong) > 99) {
//                JOptionPane.showMessageDialog(this, "Số lượng phải dưới 100");
//                return false;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Số lượng phải dưới 100");
//            return false;
//        }
        if (Integer.valueOf(soLuong) < 1) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
            return false;
        }

        Pattern partten = Pattern.compile("^[0-9]+$");
        Matcher matcher1 = partten.matcher(giaBan);
        Matcher matcher2 = partten.matcher(giaNhap);

        if (!matcher1.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số");
            return false;
        }

        if (!matcher2.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số");
            return false;
        }

        if (Double.parseDouble(giaBan) < Double.parseDouble(giaNhap)) {
            JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn giá nhập");
            return false;
        }

        return true;
    }

    public boolean validateThuocTinh() {
        if (txtTen.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được để trống");
            return false;
        }

        return true;
    }

    public boolean checkMaCTSP(String ma) {
        ChiTietSanPham ctsp = new CTSPImpl().getByMa(ma);
        if (ctsp != null) {
            JOptionPane.showMessageDialog(this, "Mã CTSP đã tồn tại");
            return false;
        }
        return true;
    }

    public boolean checkMaSP(String ma) {
        if (index == 0) {
            SanPham sp = new SanPhamImp().getByMa(ma);
            if (sp != null) {
                JOptionPane.showMessageDialog(this, "Mã bạn nhập đã tồn tại");
                return false;
            }
        } else if (index == 1) {
            DanhMuc sp = new DanhMucImpl().getByMa(ma);
            if (sp != null) {
                JOptionPane.showMessageDialog(this, "Mã bạn nhập đã tồn tại");
                return false;
            }
        } else if (index == 2) {
            NSX sp = new NSXImpl().getByMa(ma);
            if (sp != null) {
                JOptionPane.showMessageDialog(this, "Mã bạn nhập đã tồn tại");
                return false;
            }
        } else if (index == 3) {
            ChatLieu sp = new ChatLieuImpl().getByMa(ma);
            if (sp != null) {
                JOptionPane.showMessageDialog(this, "Mã bạn nhập đã tồn tại");
                return false;
            }
        } else if (index == 4) {
            Mau sp = new MauSacImpl().getByMa(ma);
            if (sp != null) {
                JOptionPane.showMessageDialog(this, "Mã bạn nhập đã tồn tại");
                return false;
            }
        }
        return true;
    }

    void clearData() {
        txtMa.setText("");
        txtTen.setText("");
    }

    void clearCTSP() {
        txtMaSP.setText("");
        txtMoTa.setText("");
        txtSoLuong.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbAll;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbNSX;
    private javax.swing.JComboBox<String> cbbSanPham;
    private javax.swing.JComboBox<String> cbxFindChatLieu;
    private javax.swing.JComboBox<String> cbxFindDanhMuc;
    private javax.swing.JComboBox<String> cbxFindMauSac;
    private javax.swing.JComboBox<String> cbxFindNSX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane5;
    private pagination.Pagination pagination;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tbThuocTinh;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
