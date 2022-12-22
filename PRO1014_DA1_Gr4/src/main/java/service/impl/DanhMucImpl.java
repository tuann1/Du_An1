/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import model.DanhMuc;
import repository.DanhMucRepository;
import java.util.List;
import service.IDanhMucService;

/**
 *
 * @author HP
 */
public class DanhMucImpl implements IDanhMucService{
    
    private DanhMucRepository dmRepo = new DanhMucRepository();

    @Override
    public List<DanhMuc> getAll() {
        return dmRepo.getAll();
    }

    @Override
    public String add(DanhMuc dm) {
        if(dmRepo.add(dm)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(DanhMuc dm, Integer id) {
        if(dmRepo.update(dm, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if(dmRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public List<DanhMuc> getAllSp() {
        return dmRepo.getAllSp();
    }

    @Override
    public DanhMuc getByMa(String ma) {
        return dmRepo.getByMa(ma);
    }
    
}
