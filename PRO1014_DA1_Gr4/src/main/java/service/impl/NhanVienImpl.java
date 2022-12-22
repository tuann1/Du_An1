/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.List;
import model.NhanVien;
import repository.NhanVienRepository;
import service.INhanVienService;

/**
 *
 * @author ADMIN
 */
public class NhanVienImpl implements INhanVienService {

    private NhanVienRepository nvRepo = new NhanVienRepository();

    public NhanVienImpl() {
    }

    @Override
    public List<NhanVien> getAll() {
        return nvRepo.getAll();
    }

    @Override
    public NhanVien getNhanVien(String maNV) {
        return nvRepo.getNhanVien(maNV);
    }

    @Override
    public String add(NhanVien nv) {
        if (nvRepo.add(nv)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(NhanVien nv, Integer id) {
        if (nvRepo.update(nv, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if (nvRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public String updatePass(NhanVien nv) {
        if (nvRepo.updatePass(nv)) {
            return "Đổi mật khẩu thành công";
        } else {
            return "Thất bại";
        }
    }

    @Override
    public String forgotPass(String ma, String email) {
        if (nvRepo.forgotPass(ma, email)) {
            return "Mật khẩu mới đã được gửi đến mail của bạn";
        } else {
            return "Thất bại";
        }
    }

    @Override
    public List<NhanVien> getAllNhanVien() {
        return nvRepo.getAllNhanVien();
    }

    @Override
    public List<NhanVien> pageListNhanVien(int position, int pageSize, String tenNv) {
        return nvRepo.pageListNhanVien(position, pageSize, tenNv);
    }

    @Override
    public List<NhanVien> filterNhanVien(String ma) {
        return nvRepo.filterNhanVien(ma);
    }

}
