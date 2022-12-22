/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import repository.KhachHangRepository;
import service.IKhachHangService;

/**
 *
 * @author ADMIN
 */
public class KhachHangImpl implements IKhachHangService {

    private KhachHangRepository khRepo = new KhachHangRepository();

    public KhachHangImpl() {
    }

    @Override
    public List<KhachHang> getAll() {
        return khRepo.getAll(1);
    }

    @Override
    public String add(KhachHang kh) {
        if (khRepo.add(kh)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(KhachHang kh, Integer id) {
        if (khRepo.update(kh, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if (khRepo.updateTrangThai(id)) {
            return "Thành công";
        } else {
            return "Thất bại";
        }
    }

    @Override
    public List<KhachHang> getAllByTrangThai(int trangThai) {
        return khRepo.getAllByTrangThai(trangThai);
    }

    @Override
    public KhachHang getKhachHangByMa(String ma) {
        return khRepo.getKhachHangByMa(ma);
    }

    @Override
    public Long getSumCustomer() {
        return khRepo.getSumCustomer();
    }

    @Override
    public List<KhachHang> getAllKhachHang() {
        return khRepo.getAllKhachHang();
    }

    @Override
    public List<KhachHang> pageListKhachHang(int position, int pageSize, String tenKh) {
        return khRepo.pageListKhachHang(position, pageSize, tenKh);
    }

    @Override
    public List<KhachHang> filterKhachHang(String tenKh) {
        return khRepo.filterKhachHang(tenKh);
    }

}
