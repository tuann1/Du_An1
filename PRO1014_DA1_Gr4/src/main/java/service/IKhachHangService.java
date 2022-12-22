/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.KhachHang;

/**
 *
 * @author ADMIN
 */
public interface IKhachHangService {

    public List<KhachHang> getAll();

    public List<KhachHang> getAllKhachHang();

    String add(KhachHang kh);

    String update(KhachHang kh, Integer id);

    String updateTrangThai(Integer id);

    List<KhachHang> getAllByTrangThai(int trangThai);

    KhachHang getKhachHangByMa(String ma);

    Long getSumCustomer();

    List<KhachHang> pageListKhachHang(int position, int pageSize, String tenKh);

    List<KhachHang> filterKhachHang(String tenKh);
}
