/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdHD")
    private HoaDonBan hoaDonBan;

    @ManyToOne
    @JoinColumn(name = "IdCTSP")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia")
    private BigDecimal donGia;

    @Column(name = "TrangThai")
    private Integer trangThai;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HoaDonBan getHoaDonBan() {
        return hoaDonBan;
    }

    public void setHoaDonBan(HoaDonBan hoaDonBan) {
        this.hoaDonBan = hoaDonBan;
    }

    public ChiTietSanPham getChiTietSanPham() {
        return chiTietSanPham;
    }

    public void setChiTietSanPham(ChiTietSanPham chiTietSanPham) {
        this.chiTietSanPham = chiTietSanPham;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }
    
    

    @Override
    public String toString() {
        return "HoaDonChiTiet{" + "id=" + id + ", hoaDonBan=" + hoaDonBan + ", chiTietSanPham=" + chiTietSanPham + ", soLuong=" + soLuong + ", trangThai=" + trangThai + '}';
    }

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(Integer id, HoaDonBan hoaDonBan, ChiTietSanPham chiTietSanPham, Integer soLuong, BigDecimal donGia, Integer trangThai) {
        this.id = id;
        this.hoaDonBan = hoaDonBan;
        this.chiTietSanPham = chiTietSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

  

    public Object[] toDoanhSo() {
        return new Object[]{chiTietSanPham.getMa(), chiTietSanPham.getSanPham().getTenSP(), soLuong};
    }

}
