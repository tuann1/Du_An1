/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "KhuyenMai")
public class KhuyenMai implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "makm")
    private String makm;

    @Column(name = "tenkm")
    private String tenkm;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "phantramgiam")
    private int phantramgiam;

    @Column(name = "minhoadon")
    private int minhoadon;

    @Column(name = "ngayhethan")
    private Date ngayhethan;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private int trangthai;
    
    @OneToMany(mappedBy= "khuyenMai",fetch = FetchType.LAZY)
    private List<HoaDonBan> listHd;

    public KhuyenMai() {
    }

    public KhuyenMai(int id, String makm, String tenkm, Date ngayTao, int phantramgiam, int minhoadon, Date ngayhethan, String ghichu, int trangthai) {
        this.id = id;
        this.makm = makm;
        this.tenkm = tenkm;
        this.ngayTao = ngayTao;
        this.phantramgiam = phantramgiam;
        this.minhoadon = minhoadon;
        this.ngayhethan = ngayhethan;
        this.ghichu = ghichu;
        this.trangthai = trangthai;
    }

    public KhuyenMai(int id, String makm, String tenkm, Date ngayTao, int phantramgiam, int minhoadon, Date ngayhethan, String ghichu, int trangthai, List<HoaDonBan> listHd) {
        this.id = id;
        this.makm = makm;
        this.tenkm = tenkm;
        this.ngayTao = ngayTao;
        this.phantramgiam = phantramgiam;
        this.minhoadon = minhoadon;
        this.ngayhethan = ngayhethan;
        this.ghichu = ghichu;
        this.trangthai = trangthai;
        this.listHd = listHd;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakm() {
        return makm;
    }

    public void setMakm(String makm) {
        this.makm = makm;
    }

    public String getTenkm() {
        return tenkm;
    }

    public void setTenkm(String tenkm) {
        this.tenkm = tenkm;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getPhantramgiam() {
        return phantramgiam;
    }

    public void setPhantramgiam(int phantramgiam) {
        this.phantramgiam = phantramgiam;
    }

    public int getMinhoadon() {
        return minhoadon;
    }

    public void setMinhoadon(int minhoadon) {
        this.minhoadon = minhoadon;
    }

    public Date getNgayhethan() {
        return ngayhethan;
    }

    public void setNgayhethan(Date ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public List<HoaDonBan> getListHd() {
        return listHd;
    }

    public void setListHd(List<HoaDonBan> listHd) {
        this.listHd = listHd;
    }

    @Override
    public String toString() {
        return  tenkm +"-"+phantramgiam+"%";
    }

        
    
}
