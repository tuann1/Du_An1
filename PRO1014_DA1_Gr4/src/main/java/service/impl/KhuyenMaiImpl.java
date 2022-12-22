/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.KhuyenMai;
import repository.KhuyenMaiRepository;
import service.IKhuyenMaiService;

/**
 *
 * @author T450s
 */
public class KhuyenMaiImpl implements IKhuyenMaiService {

    private KhuyenMaiRepository kmr = new KhuyenMaiRepository();

    @Override
    public ArrayList<KhuyenMai> getList() {
        return kmr.getAll();
    }

    @Override
    public void add(KhuyenMai km) {
        kmr.insert(km);
    }

    @Override
    public void remove(int id) {
        kmr.dele(id);
    }

    @Override
    public void update(KhuyenMai km, int id) {
        kmr.update(km, id);
    }

    @Override
    public List<KhuyenMai> getKhuyenMaiMap(double minHoDon) {
        return kmr.getAllKhuyenMaiMap(minHoDon);
    }

    @Override
    public KhuyenMai getKhuyenMaiByMa(String maKM) {
        return kmr.getKhuyenMaiByMa(maKM);
    }

    @Override
    public ArrayList<KhuyenMai> getAllByTrangT(int tt) {
        return kmr.getAllByTrangT(tt);
    }

    @Override
    public ArrayList<KhuyenMai> searchByDate(String ngTao, String ngHet, int trangThai) {
        return kmr.searchByDate(ngTao, ngHet, trangThai);
    }

    @Override
    public List<KhuyenMai> pageListKhuyenMai(int position, int pageSize, String ngTao, String ngHet, int tt) {
        return kmr.pageListKhuyenMai(position, pageSize, ngTao, ngHet, tt);
    }

    @Override
    public List<KhuyenMai> filterProductKhuyenMai(String ngTao, String ngHet, int tt) {
        return kmr.filterProductKhuyenMai(ngTao, ngHet, tt);
    }

    @Override
    public void checkEndDate() {
        kmr.checkEndDate();
    }

}
