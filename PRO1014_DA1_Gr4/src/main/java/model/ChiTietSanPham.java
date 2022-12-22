/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "CHITIETSP")
public class ChiTietSanPham implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MaCTSP")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "IdSp")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IdNSX")
    private NSX nhaSanXuat;

    @ManyToOne
    @JoinColumn(name = "IdDM")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "IdCL")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "IdMau")
    private Mau mauSac;

//    @OneToMany(mappedBy = "chiTietSanPham", fetch = FetchType.EAGER)
//    private List<GioHangChiTiet> listGhct;
//    
    @OneToMany(mappedBy = "chiTietSanPham", fetch = FetchType.EAGER)
    private List<HoaDonChiTiet> hdct;

    public ChiTietSanPham(List<HoaDonChiTiet> listhChiTiet) {
        this.hdct = listhChiTiet;
    }

    public List<HoaDonChiTiet> getListhChiTiet() {
        return hdct;
    }

    public void setListhChiTiet(List<HoaDonChiTiet> listhChiTiet) {
        this.hdct = listhChiTiet;
    }

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "SoLuongTon")
    private int soLuongTon;

    @Column(name = "GiaNhap")
    private BigDecimal giaNhap;

    @Column(name = "GiaBan")
    private BigDecimal giaBan;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTao")
    private Date ngayTao;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgaySua")
    private Date ngaySua;

    @Column(name = "TrangThai")
    private Integer trangThai;
    
    @Column(name = "QRCode")
    private String qrCode;

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(Integer id, String ma, SanPham sanPham, NSX nhaSanXuat, DanhMuc danhMuc, ChatLieu chatLieu, Mau mauSac, String moTa, int soLuongTon, BigDecimal giaNhap, BigDecimal giaBan, Date ngayTao, Date ngaySua, Integer trangThai, String qrCode) {
        this.id = id;
        this.ma = ma;
        this.sanPham = sanPham;
        this.nhaSanXuat = nhaSanXuat;
        this.danhMuc = danhMuc;
        this.chatLieu = chatLieu;
        this.mauSac = mauSac;
        this.moTa = moTa;
        this.soLuongTon = soLuongTon;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.trangThai = trangThai;
        this.qrCode = qrCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public NSX getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NSX nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public ChatLieu getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieu chatLieu) {
        this.chatLieu = chatLieu;
    }

    public Mau getMauSac() {
        return mauSac;
    }

    public void setMauSac(Mau mauSac) {
        this.mauSac = mauSac;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Object[] toDataRow() {
        return new Object[]{id,ma,sanPham.getTenSP(),danhMuc.getTenDM(),chatLieu.getTenCL(),
            mauSac.getTenMau(),nhaSanXuat.getTenNSX(),soLuongTon,giaNhap,giaBan,moTa,ngayTao,ngaySua,trangThai==1?"Đang kinh doanh":"Nghỉ kinh doanh"};
    }

    @Override
    public String toString() {
        return "ChiTietSanPham{" + "id=" + id + ", ma=" + ma + ", sanPham=" + sanPham + ", nhaSanXuat=" + nhaSanXuat + ", danhMuc=" + danhMuc + ", chatLieu=" + chatLieu + ", mauSac=" + mauSac + ", hdct=" + hdct + ", moTa=" + moTa + ", soLuongTon=" + soLuongTon + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan + ", ngayTao=" + ngayTao + ", ngaySua=" + ngaySua + ", trangThai=" + trangThai + ", qrCode=" + qrCode + '}';
    }
    
    
}
