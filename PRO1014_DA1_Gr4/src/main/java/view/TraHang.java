/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import service.IHDCTService;
import service.IHoaDonService;
import service.impl.HDCTImpl;
import service.impl.HoaDonBanImpl;

/**
 *
 * @author Laptop
 */
public class TraHang extends javax.swing.JFrame {

    DefaultTableModel modelHD = new DefaultTableModel();
    DefaultTableModel modelSP = new DefaultTableModel();
    IHoaDonService hoaDonService = new HoaDonBanImpl();
    IHDCTService hDCTService = new HDCTImpl();
    List<HoaDonBan> listHoaDon = new ArrayList<>();
    List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    List<HoaDonChiTiet> listTra = new ArrayList<>();
    int click = 0;

    public TraHang() {
        initComponents();
        modelHD = (DefaultTableModel) tblHoaDon.getModel();
        modelSP = (DefaultTableModel) tblSanPham.getModel();
        listHoaDon = hoaDonService.getListByTrangThai(2);
        loadTableHoaDon(listHoaDon);

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

    private void loadTableHoaDon(List<HoaDonBan> list) {
        //   DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        modelHD.setRowCount(0);
        for (HoaDonBan a : list) {
            modelHD.addRow(new Object[]{a.getId(),
                a.getMaHDB(), a.getKhachHang().getTenKH(), a.getNhanVien().getTenNV(),
                a.getNgayTao(), a.getNgayThanhToan(), a.getKhuyenMai().getTenkm(), a.getTrangThai()
            });
        }
    }

    private void clear() {
        lbKhachHang.setText("");
        lbMaHoaDon.setText("");
        txtGhiChu.setText("");
        lbTienHoanTra.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        searchOnKey = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTra = new javax.swing.JButton();
        btnTraAll = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbKhachHang = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbMaHoaDon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        lbTienHoanTra = new javax.swing.JLabel();
        btnTraHang = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSpTra = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jLabel1.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchOnKey.setText(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.searchOnKey.text")); // NOI18N

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

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
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title6")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title0")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title1")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title2")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title3")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title4")); // NOI18N
            tblHoaDon.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblHoaDon.columnModel.title5")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

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
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title6")); // NOI18N
            tblSanPham.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title0")); // NOI18N
            tblSanPham.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title1")); // NOI18N
            tblSanPham.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title2")); // NOI18N
            tblSanPham.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title3")); // NOI18N
            tblSanPham.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title4")); // NOI18N
            tblSanPham.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSanPham.columnModel.title5")); // NOI18N
        }

        org.openide.awt.Mnemonics.setLocalizedText(btnTra, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.btnTra.text")); // NOI18N
        btnTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnTraAll, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.btnTraAll.text")); // NOI18N
        btnTraAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTra)
                .addGap(30, 30, 30)
                .addComponent(btnTraAll)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTra)
                    .addComponent(btnTraAll))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbKhachHang, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.lbKhachHang.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbMaHoaDon, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.lbMaHoaDon.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jLabel4.text")); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbTienHoanTra, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.lbTienHoanTra.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnTraHang, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.btnTraHang.text")); // NOI18N
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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lbTienHoanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTraHang)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lbMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4)))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTienHoanTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnTraHang)
                .addContainerGap())
        );

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
        if (tblSpTra.getColumnModel().getColumnCount() > 0) {
            tblSpTra.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSpTra.columnModel.title0")); // NOI18N
            tblSpTra.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSpTra.columnModel.title1")); // NOI18N
            tblSpTra.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.tblSpTra.columnModel.title2")); // NOI18N
        }

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(TraHang.class, "TraHang.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    }//GEN-LAST:event_tblHoaDonMouseClicked

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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }
        listHDCT = hDCTService.getById(hdb.getId());
        HoaDonChiTiet hdct = listHDCT.get(row);
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
        int soLuongMua = (int) tblSanPham.getValueAt(row, 3);
        if (sl > soLuongMua) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá");
            return;
        }
//        if (soLuongMua == sl) {
//            hoaDonService.updateTrangThaiHoaDonChiTietbyIDHDCT(hdct.getId(), 3);
//        } else {
//            hoaDonService.updateSoLuongHDCT(hdct.getChiTietSanPham().getId(), soLuongMua - sl);
//        }
//
//        JOptionPane.showMessageDialog(this, "Xóa thành công");
//        loadTableDanhSachSp(hDCTService.getById(hdct.getHoaDonBan().getId()));
        boolean isDup = true;

        for (HoaDonChiTiet hdct1 : listTra) {
            if (hdct1.getId() == hdct.getId()) {
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
    }//GEN-LAST:event_btnTraActionPerformed

    private void btnTraHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraHangActionPerformed

        //int id = (int) tblSanPham.getValueAt(rowSP, 0);
        int soLuongMua = 0;
        for (HoaDonChiTiet hoaDonChiTiet : listTra) {
            soLuongMua = hDCTService.getSoluongByCTSPandMaHD(hoaDonChiTiet.getChiTietSanPham().getId(), hoaDonChiTiet.getHoaDonBan().getId());

            hoaDonChiTiet.setTrangThai(3);
            hoaDonService.updateSoLuongHDCTbyIDHDCT(hoaDonChiTiet.getId(), soLuongMua - hoaDonChiTiet.getSoLuong());
            System.out.println(hoaDonChiTiet.getId());
            hoaDonService.addHoaDonChiTiet(hoaDonChiTiet); // insert cái mới 
            if (hDCTService.getByIdByTrangThai(hoaDonChiTiet.getHoaDonBan().getId(), 2).size() == 0) {
                hoaDonService.updateTrangThaiHoaDon(hoaDonChiTiet.getHoaDonBan().getId(), 3);
            }

        }
        JOptionPane.showMessageDialog(this, "Thành công");
        int row = tblHoaDon.getSelectedRow();
        int id = (int) tblHoaDon.getValueAt(row, 0);
        listHDCT = hDCTService.getByIdByTrangThai(id, 2);
        
        loadTableDanhSachSp(listHDCT);
        clear();
        loadTableDanhSachTra(new ArrayList<>());
        loadTableHoaDon(hoaDonService.getListByTrangThai(2));
    }//GEN-LAST:event_btnTraHangActionPerformed

    private void btnTraAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraAllActionPerformed
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        int id = (int) tblHoaDon.getValueAt(row, 0);
        listHDCT = hDCTService.getByIdByTrangThai(id, 2);
        for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            listTra.add(hoaDonChiTiet);
        }
        loadTableDanhSachTra(listTra);
        int sum = 0;
        double sumGia = 0;
        for (HoaDonChiTiet hoaDonChiTiet : listTra) {
            sum = sum + hoaDonChiTiet.getSoLuong();
            sumGia = sumGia + (hoaDonChiTiet.getSoLuong() * hoaDonChiTiet.getDonGia().doubleValue());
        }
        lbTienHoanTra.setText(String.valueOf(sumGia));
    }//GEN-LAST:event_btnTraAllActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = tblSpTra.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
            return;
        }
        listTra.remove(row);
        loadTableDanhSachTra(listTra);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        listTra = new ArrayList<>();
        loadTableDanhSachTra(listTra);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TraHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TraHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TraHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TraHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TraHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTra;
    private javax.swing.JButton btnTraAll;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbMaHoaDon;
    private javax.swing.JLabel lbTienHoanTra;
    private javax.swing.JTextField searchOnKey;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSpTra;
    private javax.swing.JTextArea txtGhiChu;
    // End of variables declaration//GEN-END:variables
}
