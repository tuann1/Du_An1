/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import model.DanhMuc;
import java.util.List;

/**
 *
 * @author HP
 */
public interface IDanhMucService {
    public List<DanhMuc> getAll();
    String add(DanhMuc dm);
    String update(DanhMuc dm, Integer id);
    String updateTrangThai(Integer id);
    List<DanhMuc> getAllSp();
    DanhMuc getByMa(String ma);
}
