/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import model.Mau;
import repository.MauRepository;
import java.util.List;
import service.IMauSacService;

/**
 *
 * @author HP
 */
public class MauSacImpl implements IMauSacService{
    
    private MauRepository msRepo = new MauRepository();

    @Override
    public List<Mau> getAll() {
        return msRepo.getAll();
    }

    @Override
    public String add(Mau ms) {
        if(msRepo.add(ms)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(Mau ms, Integer id) {
        if(msRepo.update(ms, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if(msRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public List<Mau> getAllSp() {
        return msRepo.getAllSp();
    }

    @Override
    public Mau getByMa(String ma) {
        return msRepo.getByMa(ma);
    }
    
}
