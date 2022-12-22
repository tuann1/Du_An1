/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import model.NSX;
import repository.NSXRepository;
import java.util.List;
import service.INSXService;


/**
 *
 * @author HP
 */
public class NSXImpl implements INSXService{
    
    private NSXRepository nsxRepo = new NSXRepository();

    @Override
    public List<NSX> getAll() {
        return nsxRepo.getAll();
    }

    @Override
    public String add(NSX nsx) {
        if(nsxRepo.add(nsx)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(NSX nsx, Integer id) {
        if(nsxRepo.update(nsx, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if(nsxRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public List<NSX> getAllSp() {
        return nsxRepo.getAllSp();
    }

    @Override
    public NSX getByMa(String ma) {
        return nsxRepo.getByMa(ma);
    }
    
}
