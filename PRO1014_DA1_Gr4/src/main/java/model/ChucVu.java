/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "ChucVu")
public class ChucVu implements  Serializable{ 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id ;
    
    @Column(name = "MaCV")
    private String maCV; 
    
    @Column(name = "TenCV")
    private  String tenCv ; 
    
    @Column(name = "TrangThai")
    private Integer trangThai ;
    
    @OneToMany(mappedBy = "chucVu",fetch = FetchType.LAZY)
    private List<NhanVien> listNv ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCv() {
        return tenCv;
    }

    public void setTenCv(String tenCv) {
        this.tenCv = tenCv;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public List<NhanVien> getListNv() {
        return listNv;
    }

    public void setListNv(List<NhanVien> listNv) {
        this.listNv = listNv;
    }

    @Override
    public String toString() {
        return  tenCv;
    }

    public ChucVu() {
    }

    public ChucVu(Integer id, String maCV, String tenCv, Integer trangThai, List<NhanVien> listNv) {
        this.id = id;
        this.maCV = maCV;
        this.tenCv = tenCv;
        this.trangThai = trangThai;
        this.listNv = listNv;
    }
    
    
    
}
