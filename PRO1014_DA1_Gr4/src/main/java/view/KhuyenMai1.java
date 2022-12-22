/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import model.KhuyenMai;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Exceptions;
import pagination.EventPagination;
import pagination.Page;
import pagination.style.PaginationItemRenderStyle1;
import service.IKhuyenMaiService;
import service.impl.KhuyenMaiImpl;

/**
 *
 * @author T450s
 */
public class KhuyenMai1 extends javax.swing.JPanel {
    
    private IKhuyenMaiService kms = new KhuyenMaiImpl();
    
    private final LayNgayFrame lnf = new LayNgayFrame();
    private List<KhuyenMai> listKM;
    
    Integer pageSize = 5;
    Integer totalProducts = 0;
    private Page paging = new Page();
    List<KhuyenMai> pageKm = new ArrayList<>();

    /**
     * Creates new form KhuyenMai1
     */
    public KhuyenMai1() {
        initComponents();
        this.cbbTrangThai.setSelectedIndex(0);
        paginationKM.setPaginationItemRender(new PaginationItemRenderStyle1());
        paginationKM.setPagegination(1, paging.getTotalPage());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        loadPagination();
    }
    
    public void loadTableByArr(List<KhuyenMai> listKMloa) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblKhuyenMai.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (KhuyenMai km : listKMloa) {
            Object rowData[] = {
                km.getId(),
                km.getMakm(),
                km.getTenkm(),
                sdf.format(km.getNgayTao()),
                km.getPhantramgiam(),
                km.getMinhoadon(),
                sdf.format(km.getNgayhethan()),
                km.getGhichu(),
                km.getTrangthai() == 1 ? "Đang áp dụng" : "Ngừng áp dụng"
            };
            dtm.addRow(rowData);
        }
    }

//    public void loadTableByArr2(List<KhuyenMai> listKMloa) {
//        DefaultTableModel dtm = (DefaultTableModel) this.tblKhuyenMai.getModel();
//        dtm.setRowCount(0);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        for (KhuyenMai km : listKMloa) {
//            Object rowData[] = {
//                km.getId(),
//                km.getMakm(),
//                km.getTenkm(),
//                sdf.format(km.getNgayTao()),
//                km.getPhantramgiam(),
//                km.getMinhoadon(),
//                sdf.format(km.getNgayhethan()),
//                km.getGhichu(),
//                km.getTrangthai() == 1 ? "Đang áp dụng" : "Ngừng áp dụng"
//            };
//            dtm.addRow(rowData);
//        }
//    }
    public KhuyenMai getForm() {
        
        boolean isValid = true;
        String tenKm = this.txtTenKm.getText().trim();
        if (tenKm.length() == 0) {
            //JOptionPane.showMessageDialog(this, " Ban de trong ten khuyen mai");
            lbnMesTenKM.setText("Bạn để trống tên khuyến mãi");
            lbnMesTenKM.setForeground(Color.red);
            txtTenKm.requestFocus();
            isValid = false;
        } else {
            lbnMesTenKM.setText("");
        }
        String moTa = this.txtGhiChu.getText().trim();
        if (moTa.length() == 0) {
            lblMesMoTa.setText("Bạn bỏ trống mô tả ");
            lblMesMoTa.setForeground(Color.red);
            isValid = false;
        } else {
            lblMesMoTa.setText("");
        }
        String ngayTao = this.txtNgayTao.getDate().toString().trim();
        Date ngtao = (Date) this.txtNgayTao.getDate();
        
        String dieuKien = this.txtDieuKien.getText().trim();
        int DkapDung = 0;
        String phanGiam = this.txtPhanTramGiam.getText().trim();
        int phtramGiam = 0;
        if (dieuKien.length() == 0) {
            lblMesDK.setText("Bạn để trống điều kiện");
            lblMesDK.setForeground(Color.red);
            txtDieuKien.requestFocus();
            isValid = false;
        }
        if (phanGiam.length() == 0) {
            lblMesPTram.setText("Bạn để trống phần trăm giảm");
            lblMesPTram.setForeground(Color.red);
            isValid = false;
        } else {
            try {
                //phtramGiam = Integer.valueOf(phanGiam);
                DkapDung = Integer.valueOf(dieuKien);
            } catch (NumberFormatException e) {
                
                lblMesDK.setText("Sai định dạng số");
                lblMesDK.setForeground(Color.red);
                txtDieuKien.requestFocus();
                isValid = false;
                return null;
            }
            
            if (DkapDung <= 0) {
                lblMesDK.setText("Điều kiện nhỏ hơn 0");
                lblMesDK.setForeground(Color.red);
                txtDieuKien.requestFocus();
                isValid = false;
                return null;
            } else {
                lblMesDK.setText("");
            }
            
            try {
                phtramGiam = Integer.valueOf(phanGiam);
                
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                lblMesPTram.setText("Sai định dạng số");
                lblMesPTram.setForeground(Color.red);
                txtPhanTramGiam.requestFocus();
                isValid = false;
                
            }
            
            if (phtramGiam <= 0) {
                lblMesPTram.setText("Phần trăm số nhỏ hơn 0");
                lblMesPTram.setForeground(Color.red);
                txtPhanTramGiam.requestFocus();
                isValid = false;
                
            } else if (phtramGiam >= 100) {
                lblMesPTram.setText("Phần trăm số không thể quá 100%");
                lblMesPTram.setForeground(Color.red);
                txtPhanTramGiam.requestFocus();
                isValid = false;
            } else {
                lblMesPTram.setText("");
            }
            
        }
        
        int tthai = 1;
        String maKm = "";     
        maKm = "KM00" + (kms.getList().size() + 1);
        Date ngHet = (Date) this.txtNgayHetHan.getDate();
        
        int ssanh = ngHet.compareTo(ngtao);
        if (ssanh <= 0) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập ngày hết hạn lớn hơn ngày sửa");
            return null;
        }
        
        if (isValid == false) {
            return null;
        } else {
            KhuyenMai km = new KhuyenMai(0, maKm, tenKm, ngtao, phtramGiam, DkapDung, ngHet, moTa, tthai);
            System.out.println(ngtao + "" + ngHet);
            return km;
        }
        
    }
    
    public void clearForm() {
        
        txtMaKm.setText("");
        txtTenKm.setText("");
        txtNgayTao.setDate(new Date());
        txtPhanTramGiam.setText("");
        txtDieuKien.setText("");
        txtNgayHetHan.setDate(new Date());
        txtGhiChu.setText("");
        tblKhuyenMai.clearSelection();
        this.cbbTrangThai.setSelectedIndex(0);
        this.lblMesMaKm.setText("");
    }
    
    public int checkTonTai() {
        String ma = this.txtMaKm.getText().trim();
        KhuyenMai km = this.kms.getKhuyenMaiByMa(ma);
        if (km == null) {
            return 0;
        }
        return 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaKm = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenKm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDieuKien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPhanTramGiam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        lbnMesTenKM = new javax.swing.JLabel();
        lblMesDK = new javax.swing.JLabel();
        lblMesPTram = new javax.swing.JLabel();
        lblMesMoTa = new javax.swing.JLabel();
        lblMesMaKm = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lblMesNgayHet = new javax.swing.JLabel();
        txtNgayTao = new com.toedter.calendar.JDateChooser();
        txtNgayHetHan = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        paginationKM = new pagination.Pagination();
        btnKtra = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel2.text")); // NOI18N

        txtMaKm.setEditable(false);
        txtMaKm.setForeground(new java.awt.Color(255, 204, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel3.text")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel1.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel4.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel5.text")); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtMaKm, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTenKm))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(txtPhanTramGiam)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbnMesTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMesMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(33, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblMesDK, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMesPTram, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblMesMaKm, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblMesMaKm, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lbnMesTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMesDK, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lblMesMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMesPTram, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel6.text")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel7.text")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel8.text")); // NOI18N

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn khuyến mãi", "Hết khuyến mãi" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel9.text")); // NOI18N

        txtNgayTao.setDateFormatString(org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.txtNgayTao.dateFormatString")); // NOI18N

        txtNgayHetHan.setDateFormatString(org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.txtNgayHetHan.dateFormatString")); // NOI18N

        btnTimKiem.setBackground(new java.awt.Color(204, 204, 204));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnTimKiem, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.btnTimKiem.text")); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(130, 130, 130)
                                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMesNgayHet, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                    .addComponent(txtNgayHetHan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 400, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(lblMesNgayHet, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Mã Km", "Tên Km", "Ngày Tạo", "Phần trăm giảm", "Điều kiện", "Ngày hết", "Mô tả", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhuyenMai);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.jLabel10.text")); // NOI18N

        btnClear.setBackground(new java.awt.Color(204, 204, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnClear, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.btnClear.text")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnKtra.setBackground(new java.awt.Color(204, 204, 204));
        btnKtra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnKtra, org.openide.util.NbBundle.getMessage(KhuyenMai1.class, "KhuyenMai1.btnKtra.text")); // NOI18N
        btnKtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKtraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap(1078, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(428, 428, 428)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jButton3)
                        .addGap(0, 155, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(btnKtra, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(520, 520, 520)
                .addComponent(paginationKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKtra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(73, 73, 73)
                .addComponent(paginationKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        
        loadPagination();

    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        KhuyenMai km = this.getForm();
        if (km == null) {
            // JOptionPane.showMessageDialog(this, "Moi ban check lai form");
            return;
        }
//        int x = this.checkTonTai();
//        if (x == 1) {
//            this.lblMesMaKm.setText("Mã khuyến mãi đã tồn tại");
//            this.lblMesMaKm.setForeground(Color.red);
//            return;
//        }
        kms.add(km);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        loadPagination();
        clearForm();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = this.tblKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Mời bạn chọn lại dòng");
            return;
        }
        String id = this.tblKhuyenMai.getValueAt(row, 0).toString();
        
        KhuyenMai km = this.getForm();
        if (km == null) {
            JOptionPane.showMessageDialog(this, "Mời bạn check lại form");
            return;
        }
        int idm = Integer.valueOf(id);
        int chooser = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa");
        if (chooser == 0) {
            kms.update(km, idm);
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            loadPagination();
            clearForm();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = this.tblKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Mời bạn chọn lại dòng");
            return;
        }
        String id = this.tblKhuyenMai.getValueAt(row, 0).toString();
        int idm = Integer.valueOf(id);
        
        int chooser = JOptionPane.showConfirmDialog(this, "Bạn có muốn ngừng áp dụng");
        if (chooser == 0) {
            this.kms.remove(idm);
            JOptionPane.showMessageDialog(this, "Ngừng áp dụng thanh cong");
            loadPagination();
            clearForm();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        clearMes();
        int row = this.tblKhuyenMai.getSelectedRow();
        if (row == -1) {
            return;
        }
//        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date ngTao = txtNgayTao.getDate();
//        Date ngHet = txtNgayHetHan.getDate();
//        int htai = date.getDate();
//        String c = null;
//        String d = null;
//
////        if (!sdf.format(ngTao).equals(sdf.format(ngHet))) {
////            c = sdf.format(ngTao);
////            d = sdf.format(ngHet);
////        }
//
//        int vt = cbbTrangThai.getSelectedIndex();
//        int tt1 = 0;
//        if (vt == 0) {
//            tt1 = 1;
//        } else {
//            tt1 = 0;
//        }
//
//        pageKm = kms.pageListKhuyenMai(paging.getCurrent(), pageSize, c, d, tt1);
//
//        KhuyenMai a = pageKm.get(row);
//        txtMaKm.setText(a.getMakm());
//        txtTenKm.setText(a.getTenkm());
//        txtNgayTao.setDate(a.getNgayTao());
//        txtNgayHetHan.setDate(a.getNgayhethan());
//        txtPhanTramGiam.setText(a.getPhantramgiam() + "");
//        txtDieuKien.setText(a.getMinhoadon() + "");
//        txtGhiChu.setText(a.getGhichu());

        txtMaKm.setText(this.tblKhuyenMai.getValueAt(row, 1).toString());
        txtTenKm.setText(this.tblKhuyenMai.getValueAt(row, 2).toString());
        String date = this.tblKhuyenMai.getValueAt(row, 3).toString();
        Date c1 = null;
        try {
            c1= sdf.parse(date);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        txtNgayTao.setDate(c1);
        txtPhanTramGiam.setText(this.tblKhuyenMai.getValueAt(row, 4).toString());
        txtDieuKien.setText(this.tblKhuyenMai.getValueAt(row, 5).toString());
        String date2 = this.tblKhuyenMai.getValueAt(row, 6).toString();
        Date c2 = null;
        try {
            c2= sdf.parse(date2);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        txtNgayHetHan.setDate(c2);
        txtGhiChu.setText(this.tblKhuyenMai.getValueAt(row, 7).toString());
//        String tt = this.tblKhuyenMai.getValueAt(row, 8).toString();
//        int trangt = Integer.valueOf(tt);
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        clearMes();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
//
//        Date ngTao = this.txtNgayTao.getDate();
//        Date ngHet = this.txtNgayHetHan.getDate();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String x = sdf.format(ngTao);
//        String y = sdf.format(ngHet);
//
//        ArrayList<KhuyenMai> lm = new ArrayList<>();
//        lm = kms.searchByDate(x, y, tt);
//        System.out.println(lm.size());
//        loadTableByArr(lm);
////        System.out.println(kms.searchByDate(x, y, tt));
//        clearForm();

        loadPagination();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnKtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKtraActionPerformed
        int sztruoc = this.kms.getAllByTrangT(0).size();
        kms.checkEndDate();
        int szsau = this.kms.getList().size()-kms.getAllByTrangT(1).size();
        JOptionPane.showMessageDialog(this,"Kiểm tra thành công, số khuyến mãi đã ngừng áp dụng:"+(szsau-sztruoc)+"");
        loadTableByArr(kms.getAllByTrangT(1));
    }//GEN-LAST:event_btnKtraActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        clearMes();
    }//GEN-LAST:event_jPanel1MouseClicked
    
    public void loadPagination() {
        Date ngTao = this.txtNgayTao.getDate();
        Date ngHet = this.txtNgayHetHan.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String a = "";
        String b = "";
        if (sdf.format(ngTao).equals(sdf.format(ngHet))) {
            a = null;
            b = null;
        } else {
            a = sdf.format(ngTao);
            b = sdf.format(ngHet);
        }
        
        String x = a;
        String y = b;
        
        int vt = cbbTrangThai.getSelectedIndex();
        int tt = 0;
        if (vt == 0) {
            tt = 1;
        } else {
            tt = 0;
        }
        int tt1 = tt;
        totalProducts = this.kms.filterProductKhuyenMai(x, y, tt1).size();
        int total = (int) Math.ceil(totalProducts / pageSize) + 1;
        paging.setTotalPage(total);
        paginationKM.setPagegination(1, paging.getTotalPage());
        
        if (paging.getTotalPage() < paging.getCurrent()) {
            paginationKM.setPagegination(paging.getTotalPage(), paging.getTotalPage());
            loadTableByArr(kms.pageListKhuyenMai(paging.getTotalPage(), pageSize, x, y, tt1));
        } else {
            paginationKM.setPagegination(paging.getCurrent(), paging.getTotalPage());
            loadTableByArr(kms.pageListKhuyenMai(paging.getCurrent(), pageSize, x, y, tt1));
        }
        
        paginationKM.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadTableByArr(kms.pageListKhuyenMai(page, pageSize, x, y, tt1));
                paging.setCurrent(page);
            }
        });
        
    }

    public void clearMes(){
        lbnMesTenKM.setText("");
        lblMesDK.setText("");
        lblMesMoTa.setText("");
        lblMesNgayHet.setText("");
        lblMesPTram.setText("");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKtra;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblMesDK;
    private javax.swing.JLabel lblMesMaKm;
    private javax.swing.JLabel lblMesMoTa;
    private javax.swing.JLabel lblMesNgayHet;
    private javax.swing.JLabel lblMesPTram;
    private javax.swing.JLabel lbnMesTenKM;
    private pagination.Pagination paginationKM;
    private javax.swing.JTable tblKhuyenMai;
    private javax.swing.JTextField txtDieuKien;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaKm;
    private com.toedter.calendar.JDateChooser txtNgayHetHan;
    private com.toedter.calendar.JDateChooser txtNgayTao;
    private javax.swing.JTextField txtPhanTramGiam;
    private javax.swing.JTextField txtTenKm;
    // End of variables declaration//GEN-END:variables
}
