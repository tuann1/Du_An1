/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.KhachHang;
import model.NhanVien;

/**
 *
 * @author ADMIN
 */
public interface INhanVienService {

    public List<NhanVien> getAll();

    NhanVien getNhanVien(String maNV);

    String add(NhanVien nv);

    String update(NhanVien nv, Integer id);

    String updateTrangThai(Integer id);

    String updatePass(NhanVien nv);

    String forgotPass(String ma, String email);

    List<NhanVien> getAllNhanVien();

    List<NhanVien> pageListNhanVien(int position, int pageSize, String tenNv);

    List<NhanVien> filterNhanVien(String ma);

}
