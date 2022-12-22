/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import org.bridj.ann.Template;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "HoaDon")
public class HoaDonBan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdNV")
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "hoaDonBan", fetch = FetchType.EAGER)
    private List<HoaDonChiTiet> listHdCt;

    @ManyToOne
    @JoinColumn(name = "IdKH")
    private KhachHang khachHang;

    @Column(name = "MaHD")
    private String maHDB;

    @ManyToOne
    @JoinColumn(name = "IdKM")
    private KhuyenMai khuyenMai;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTao")
    private Date ngayTao;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayThanhToan")
    private Date ngayThanhToan;

    @Column(name = "NguoiNhan")
    private String nguoiNhan;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "TrangThai")
    private Integer trangThai;

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<HoaDonChiTiet> getListHdCt() {
        return listHdCt;
    }

    public void setListHdCt(List<HoaDonChiTiet> listHdCt) {
        this.listHdCt = listHdCt;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public String getMaHDB() {
        return maHDB;
    }

    public void setMaHDB(String maHDB) {
        this.maHDB = maHDB;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String trangthai() {
        String trangThai = null;
        if (this.trangThai == 0) {
            return trangThai = "Đã Hủy";
        } else if (this.trangThai == 1) {
            return trangThai = "Chưa Thanh Toán";
        } else if (this.trangThai == 2) {
            return trangThai = "Đã Thanh Toán";
        } else if (this.trangThai == 3) {
            return trangThai = "Đã Trả Hàng";
        }
        return trangThai;

    }

    @Override
    public String toString() {
        return "HoaDonBan{" + "id=" + id + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + ", maHDB=" + maHDB + ", khuyenMai=" + khuyenMai + ", ngayTao=" + ngayTao + ", ngayThanhToan=" + ngayThanhToan + ", nguoiNhan=" + nguoiNhan + ", sdt=" + sdt + ", diaChi=" + diaChi + ", trangThai=" + trangThai + '\n';
    }

//    @Override
//    public String toString() {
//        return "HoaDonBan{" + "id=" + id + ", nhanVien=" + nhanVien + ", listHdCt=" + listHdCt + ", khachHang=" + khachHang + ", maHDB=" + maHDB + ", ngayTao=" + ngayTao + ", ngayThanhToan=" + ngayThanhToan + ", nguoiNhan=" + nguoiNhan + ", sdt=" + sdt + ", diaChi=" + diaChi + ", trangThai=" + trangThai + '}';
//    }
    public HoaDonBan() {
    }

//    public HoaDonBan(Integer id, NhanVien nhanVien, List<HoaDonChiTiet> listHdCt, KhachHang khachHang, String maHDB, String ngayTao, String ngayThanhToan, String nguoiNhan, String sdt, String diaChi, Integer trangThai) {
//        this.id = id;
//        this.nhanVien = nhanVien;
//        this.listHdCt = listHdCt;
//        this.khachHang = khachHang;
//        this.maHDB = maHDB;
//        this.ngayTao = ngayTao;
//        this.ngayThanhToan = ngayThanhToan;
//        this.nguoiNhan = nguoiNhan;
//        this.sdt = sdt;
//        this.diaChi = diaChi;
//        this.trangThai = trangThai;
//    }
    public HoaDonBan(Integer id, NhanVien nhanVien, KhachHang khachHang, String maHDB, KhuyenMai khuyenMai, Date ngayTao, Date ngayThanhToan, String nguoiNhan, String sdt, String diaChi, Integer trangThai) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.maHDB = maHDB;
        this.khuyenMai = khuyenMai;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.nguoiNhan = nguoiNhan;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

}
