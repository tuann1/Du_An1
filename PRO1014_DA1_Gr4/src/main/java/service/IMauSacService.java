/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import model.Mau;
import java.util.List;

/**
 *
 * @author HP
 */
public interface IMauSacService {
    public List<Mau> getAll();
    String add(Mau ms);
    String update(Mau ms, Integer id);
    String updateTrangThai(Integer id);
    List<Mau> getAllSp();
    Mau getByMa(String ma);
}
