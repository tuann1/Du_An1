/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.KhuyenMai;

/**
 *
 * @author T450s
 */
public interface IKhuyenMaiService {

    ArrayList<KhuyenMai> getList();

    void add(KhuyenMai km);

    void remove(int id);

    void update(KhuyenMai km, int id);

    List<KhuyenMai> getKhuyenMaiMap(double minHoDon);

    KhuyenMai getKhuyenMaiByMa(String maKM);

    ArrayList<KhuyenMai> getAllByTrangT(int tt);
    
    ArrayList<KhuyenMai> searchByDate(String ngTao, String ngHet,int trangThai);
    
    List<KhuyenMai> pageListKhuyenMai(int position, int pageSize, String ngTao, String ngHet, int tt);
    
    List<KhuyenMai> filterProductKhuyenMai(String ngTao, String ngHet, int tt);
    
    void checkEndDate();
}
