/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import model.NSX;
import java.util.List;

/**
 *
 * @author HP
 */
public interface INSXService {

    public List<NSX> getAll();

    String add(NSX nsx);

    String update(NSX nsx, Integer id);

    String updateTrangThai(Integer id);
    List<NSX> getAllSp();
    NSX getByMa(String ma);
}
