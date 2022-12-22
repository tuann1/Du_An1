/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import model.SanPham;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ISanPhamService {
   List<SanPham> getAll();
   String add(SanPham sp);
   String update(SanPham sp, Integer id);
   String updateTrangThai(Integer id);
   SanPham getByMa(String ma);
   List<SanPham> getAllSp();
}
